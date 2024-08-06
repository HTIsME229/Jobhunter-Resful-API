package vn.hoidanit.jobhunter.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import vn.hoidanit.jobhunter.domain.res.RestResponse;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

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

        if (body instanceof Resource) {
            return body;
        }
        if (body instanceof String) {
            return body;
        }

        if (statusCode >= 400) {

            return body;
        } else {
            ApiMessage message = returnType.getMethod().getAnnotation(ApiMessage.class);
            res.setStatusCode(statusCode);
            res.setData(body);
            res.setMessage(message != null ? message.value() : "CAll API SUCCESS");
        }

        return res;
    }
}
