package com.steady.steadyback.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.*;

import com.steady.steadyback.util.errorutil.CustomException;
import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class StudyPostService {

    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final StudyPostRepository studyPostRepository;
    private final StudyPostImageRepository studyPostImageRepository;
    private final UserStudyRepository userStudyRepository;

    private final AmazonS3Client amazonS3Client;

    public StudyPostGetResponseDto findByStudyIdStudyPostId(Long studyPostId) {
        StudyPost studyPost= studyPostRepository.findById(studyPostId)
                .orElseThrow(()->new CustomException(ErrorCode.STUDY_POST_NOT_FOUND));

        return new StudyPostGetResponseDto(studyPost);
    }
    public List<StudyPostGetResponseDto> findStudyPostListByDateAndStudy(Long studyId, String date) {

        LocalDate Date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Study study= studyRepository.findById(studyId).orElseThrow(()->new CustomException(ErrorCode.STUDY_NOT_FOUND));

        List<StudyPost> list = studyPostRepository.findByStudyAndDate(study, Date);
        List<StudyPostGetResponseDto> total= new ArrayList<>();
        for (StudyPost studyPost:list){
            List<StudyPostImage> studyPostImage= studyPostImageRepository.findByStudyPost(studyPost);
            for(StudyPostImage studyPostImage1: studyPostImage) {
                total.add(new StudyPostGetResponseDto(studyPost, new StudyPostImageResponseDto(studyPostImage1)));
            }
        }

        if(list.isEmpty()) throw new CustomException(ErrorCode.STUDY_POST_LIST_NOT_FOUND);

        return total;
    }


    public List<StudyPostGetResponseDto> findStudyPostListByDateAndUser(Long userId, String date) {


        LocalDate Date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        User user= userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<StudyPost> list = studyPostRepository.findByUserAndDate(user, Date);
        List<StudyPostGetResponseDto> total= new ArrayList<>();
        for (StudyPost studyPost:list){
            List<StudyPostImage> studyPostImage= studyPostImageRepository.findByStudyPost(studyPost);
            for(StudyPostImage studyPostImage1: studyPostImage) {
                total.add(new StudyPostGetResponseDto(studyPost, new StudyPostImageResponseDto(studyPostImage1)));

            }
        }

        if(list.isEmpty()) throw new CustomException(ErrorCode.STUDY_POST_LIST_NOT_FOUND);

        return total;
    }



    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 ?????? ??????

    public StudyPostResponseDto createStudyPost(StudyPostRequestDto studyPostRequestDto, List<MultipartFile> multipartFiles) throws IOException {

        int imageCount = 0;
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty())
                imageCount++;
        }
        if(studyPostRequestDto.getLink().isEmpty() && imageCount == 0) {
            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);
        }
        if(imageCount > 2)
            throw new CustomException(ErrorCode.OVER_2_IMAGES);

        User user = studyPostRequestDto.getUser();
        userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyPostRequestDto.getStudy();
        studyRepository.findById(study.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));


        StudyPost studyPost = studyPostRepository.save(studyPostRequestDto.toEntity());

        UserStudy userStudy = userStudyRepository.findByUserAndStudy(user, study)
                .orElseThrow(() -> new CustomException(ErrorCode.INFO_NOT_FOUNT));

        //?????? ?????????
        LocalDateTime date = studyPost.getDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue(); //???:1, ???:2, ... , ???:7

        boolean check = true;
        String studyPostSort;

        switch(dayOfWeekNumber){
            case 1:
                check = study.getMon();
                break ;
            case 2:
                check = study.getTue();
                break ;
            case 3:
                check = study.getWed();
                break ;
            case 4:
                check = study.getThu();
                break ;
            case 5:
                check = study.getFri();
                break ;
            case 6:
                check = study.getSat();
                break ;
            case 7:
                check = study.getSun();
                break ;

        }

        //??? ??? ?????? ??????????????????
        if(check) {
            if (date.getHour() < study.getHour() || (date.getHour() == study.getHour() && date.getMinute() < study.getMinute())) {
                studyPostSort = "?????? ??????";
            }
            else{
                userStudy.addLateMoney();
                studyPostSort = "??????";
            }
        }
        else {
            if(userStudy.getNowFine() > 0) {
                userStudy.subtractMoney();
                userStudy.addLateMoney();
                studyPostSort = "?????? ?????? ";
            }
            else {
                studyPostSort = "?????? ?????? ";
            }
        }

        userStudyRepository.save(userStudy);

        List<String> uploadImageUrl = new ArrayList<>();

        if(imageCount > 0) {
            for (MultipartFile file : multipartFiles) {
                File uploadFile = convert(file)  // ?????? ????????? ??? ????????? ??????
                        .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

                String imageUrl = upload(uploadFile, "static");
                uploadImageUrl.add(imageUrl); //uploadImageUrl??? studyPostResponseDto??? ??????

                //StudyPostImage ???????????? ?????????
                StudyPostImageRequestDto studyPostImageRequestDto = new StudyPostImageRequestDto(studyPost, imageUrl);
                StudyPostImage studyPostImage = studyPostImageRepository.save(studyPostImageRequestDto.toEntity());
            }
        }
        StudyPostResponseDto studyPostResponseDto = new StudyPostResponseDto(studyPost.getId(), studyPost.getUser().getId(), studyPost.getStudy().getId(), studyPost.getLink(), uploadImageUrl, studyPost.getDate(), userStudy.getNowFine(), studyPostSort);

        return studyPostResponseDto;
    }


    // S3??? ?????? ???????????????
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3??? ????????? ?????? ??????
        String uploadImageUrl = putS3(uploadFile, fileName); // s3??? ?????????
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3??? ?????????
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // ????????? ????????? ????????? ?????????
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // ????????? ?????? ????????? ??????
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // ?????? ????????? ????????? ????????? File??? ????????? (????????? ?????????????????? ?????? ?????????)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream ???????????? ????????? ????????? ??????????????? ???????????? ??????
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

}
