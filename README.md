# 📖STEADY📖 - 백엔드

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FEFUB-steady%2Fsteady-back&count_bg=%2344D0B3&title_bg=%23CEE1C1&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

## 🍈 백엔드 팀원
| [김민주](https://github.com/MINJU-KIMmm)                                                            | [김윤지](https://github.com/yoonjiy)                                                            | [변지은](https://github.com/mons-trev)                                                            | [최빈](https://github.com/chlqls)                                                            |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------ |
|<img src = "https://user-images.githubusercontent.com/80975932/181456398-351ceac7-5d40-4d0d-a462-38bcc9bdd5f2.png" width="100%"/>         | <img src = "https://user-images.githubusercontent.com/80975932/181456524-c552d522-c010-48b0-9c8a-c03cbcaebeb2.png" width="100%"/>         | <img src = "https://user-images.githubusercontent.com/80975932/181457826-d3060ff6-d85b-4d29-ab82-8f5904482364.png" width="100%"/>         | <img src = "https://user-images.githubusercontent.com/80975932/181457910-99f905fb-3e8d-45c3-9aa7-49355ddb7a6a.png" width="89%"/>         |
| [스터디] 스터디 생성, 삭제, 규칙 수정 기능</br> [신고] 신고하기, 신고 인증 취소, 신고 취소, 신고 조회 기능</br> [배포] Docker/EC2를 이용한 CI/CD 구축,</br> loadBalancer/route53/SSL인증서를 이용해 HTTPS 통신 구현 </br> [기타] 프로젝트 생성 | [유저] 회원 가입, 로그인, 탈퇴 기능 </br>[스터디] 스터디 가입, 탈퇴, 벌금 정산 기능 </br> [투두리스트] 투두리스트 조회, 완료 체크 기능</br> [DB] RDS 생성 | [유저] 아이디 찾기, 임시비밀번호 전송 기능</br>[스터디] 날짜별 스터디 인증글 조회 기능</br> [DB] ERD 작성 </br>[기타] readme 작성| [유저] 회원 가입, 로그인, 탈퇴, 조회 기능</br> [스터디] 인증 글쓰기(벌금로직 다르게), 공지사항 등록 조회 기능 </br> [AWS] s3|

-------------------
## 🍈 개요
'STEADY' 는 스터디 관리를 해주는 웹 서비스입니다. 회원가입을 한 누구나 스터디를 개설할 수 있으며 기한 관리, 스터디 인증글 작성, 벌금 정산, 랭킹 제도, 투두리스트, 신고 등의 기능을 통해 STEADY는 유저들이 원활한 스터디 활동을 하도록 돕습니다. 스터디 부원들에게는 동기부여를, 스터디 관리가 부담스러워 스터디를 선뜻 개설하지 못하는 스터디장에게는 부담을 대신해주는 서비스를 제공하고 있습니다. 

## 🍈 기술 스택

- DEVELOP &nbsp; 
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=round-square&logo=Spring&logoColor=white) 

- DB &nbsp; <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> 

- AWS &nbsp;
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon%20AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon LoadBalancer-E68B49?style=flat-square&logo=Amazon LoadBalancer&logoColor=white"/>


- ETC &nbsp; <img src="https://img.shields.io/badge/GitHub -181717?style=flat-square&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Doker -2496ED?style=flat-square&logo=Docker&logoColor=white"/> 
</br>

<p align = "center">
<img src="https://user-images.githubusercontent.com/80975932/182993432-35d32e52-c05f-48c8-8f47-6051c6549085.png"/>
</p>


</br>

## 🍈 라이브러리
1. lombok
2. spring web
3. spring data jpa
4. junit
5. spring boot test
6. jwt
7. spring mail

## 🍈 프로젝트 구조


### 설명
1. main/java/[프로젝트명]/config ▶️ Config
2. main/java/[프로젝트명]/controller ▶️ Controller
3. main/java/[프로젝트명]/domain ▶️ Entity, Repository
4. main/java/[프로젝트명]/dto ▶️ Dto
5. main/java/[프로젝트명]/service ▶️ Service
6. main/java/[프로젝트명]/SteadyBackApplication.java
7. main/resources/application.properties

### 폴더 

<pre>
<code>
└── 🗂 main
    ├── 🗂 java
    │   └── 🗂 com
    │       └── 🗂 steady
    │           └── 🗂 steadyback
    │               ├── 📑 SteadyBackApplication.java
    │               ├── 🗂 config
    │               │   ├── 📑 CustomAccessDeniedHandler.java
    │               │   ├── 📑 CustomAuthenticationEntryPoint.java
    │               │   ├── 📑 JwtAuthenticationFilter.java
    │               │   ├── 📑 JwtTokenProvider.java
    │               │   ├── 📑 RedisConfig.java    
    │               │   ├── 📑 S3Config.java
    │               │   └── 📑 WebSecurityConfig.java
    │               ├── 🗂 controller
    │               │   ├── 📑 NoticeController.java
    │               │   ├── 📑 ReportController.java
    │               │   ├── 📑 StudyController.java
    │               │   ├── 📑 StudyPostController.java
    │               │   ├── 📑 TodolistController.java          
    │               │   ├── 📑 UserController.java
    │               │   └── 📑 UserStudyController.java
    │               │  
    │               ├── 🗂 domain
    │               │   ├── 📑 Color.java
    │               │   ├── 📑 Notice.java
    │               │   ├── 📑 NoticeRepository.java
    │               │   ├── 📑 Report.java
    │               │   ├── 📑 ReportRepository.java
    │               │   ├── 📑 Study.java
    │               │   ├── 📑 StudyPost.java
    │               │   ├── 📑 StudyPostImage.java
    │               │   ├── 📑 StudyPostImageRepository.java
    │               │   ├── 📑 StudyPostRespository.java
    │               │   ├── 📑 StudyRepository.java
    │               │   ├── 📑 User.java
    │               │   ├── 📑 UserRespository.java
    │               │   ├── 📑 UserStudy.java
    │               │   ├── 📑 UserStudyID.java
    │               │   └── 📑 UserStudyResporitory.java
    │               ├── 🗂 dto
    │               │   ├── 📑 LoginRequestDto.java
    │               │   ├── 📑 LoginResponseDto.java
    │               │   ├── 📑 NoticeRequestDto.java
    │               │   ├── 📑 NoticeResponseDto.java
    │               │   ├── 📑 ReportRequestDto.java
    │               │   ├── 📑 ReportResponseDto.java
    │               │   ├── 📑 SignupRequestDto.java
    │               │   ├── 📑 StudyGetResponseDto.java
    │               │   ├── 📑 StudyPostGetResponseDto.java
    │               │   ├── 📑 StudyPostImageRequestDto.java
    │               │   ├── 📑 StudyPostImageResponseDto.java
    │               │   ├── 📑 StudyPostRequestDto.java
    │               │   ├── 📑 StudyPostResponseDto.java
    │               │   ├── 📑 StudyRequestDto.java
    │               │   ├── 📑 StudyResponseDto.java
    │               │   ├── 📑 TodolistResponseDto.java
    │               │   ├── 📑 TokenRequestDto.java    
    │               │   ├── 📑 UserDeleteResponseDto.java
    │               │   ├── 📑 UserFindPwRequestDto.java
    │               │   ├── 📑 UserFindResponseDto.java
    │               │   ├── 📑 UserRequestDto.java
    │               │   ├── 📑 UserResponseDto.java
    │               │   ├── 📑 UserStudyFineResponseDto.java
    │               │   ├── 📑 UserStudyGetResponseDto.java
    │               │   ├── 📑 UserStudyRankingResponseDto.java
    │               │   ├── 📑 UserStudyResponseDto.java
    │               │   ├── 📑 UserUpdateRequestDto.java
    │               │   └── 📑 UserUpdateResponseDto.java
    │               ├── 🗂 service
    │               │   ├── 📑 NoticeService.java
    │               │   ├── 📑 RedisService.java
    │               │   ├── 📑 ReportService.java
    │               │   ├── 📑 SchedulerService.java
    │               │   ├── 📑 StudyPostService.java
    │               │   ├── 📑 StudyService.java
    │               │   ├── 📑 TodolistService.java
    │               │   ├── 📑 UserService.java
    │               │   └── 📑 UserStudyService.java
    │               ├── 🗂 util.errorutil
    │               │   ├── 📑 CustomException.java
    │               │   ├── 📑 ErrorCode.java
    │               │   ├── 📑 ErrorResponse.java
    │               └── └── 📑 GlobalExceptionHandler.java
    └── 🗂 resources
        └── 📑 application.properties
</code>
</pre>


## 🍈 데이터베이스 설계도(E-R diagram)
<img src = "https://user-images.githubusercontent.com/80975932/181460465-5cb808a6-b78d-4acf-8c77-aa8af65fac17.PNG"/>

## 🍈 API 명세서
### [🔗 Link](https://www.notion.so/efub/API-996723e4ac2b454596b359066b061361)

## ci/cd 테스트

