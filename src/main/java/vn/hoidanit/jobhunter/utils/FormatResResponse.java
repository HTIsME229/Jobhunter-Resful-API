package vn.hoidanit.jobhunter.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import vn.hoidanit.jobhunter.domain.RestResponse;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class FormatResResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletResponse servletResponses = ((ServletServerHttpResponse) response).getServletResponse();

        int statusCode = servletResponses.getStatus();
        RestResponse res = new RestResponse();


        if (body instanceof String) {
            return body;
        }

        if (statusCode >= 400) {

            return body;
        } else {

            res.setStatusCode(statusCode);
            res.setData(body);
            res.setMessage("call Api Success");
        }

        return res;
    }
}
