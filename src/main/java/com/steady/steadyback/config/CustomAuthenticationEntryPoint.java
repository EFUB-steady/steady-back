package com.steady.steadyback.config;

import com.steady.steadyback.util.errorutil.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        String exception = (String)request.getAttribute("exception");
        ErrorCode errorCode;
        String requestURI = request.getRequestURI();

        /**
         * 토큰 없는 경우
         */
        if(exception.equals(ErrorCode.NON_LOGIN.getDetail())) {
            errorCode = ErrorCode.NON_LOGIN;
            setResponse(response, errorCode, requestURI);
            return;
        }

        /**
         * 토큰이 만료됐거나 유효하지 않은 경우
         */
        if(exception.equals(ErrorCode.INVALID_TOKEN.getDetail())) {
            errorCode = ErrorCode.INVALID_TOKEN;
            setResponse(response, errorCode, requestURI);
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode, String redirectURI) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + errorCode.getDetail()
                + "\", \"status\" : \"" + errorCode.getHttpStatus()
                + "\", \"redirectURI\" : \"" + redirectURI + "\"\n}");
    }
}
