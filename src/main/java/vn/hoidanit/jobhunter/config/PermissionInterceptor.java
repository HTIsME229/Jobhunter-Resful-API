package vn.hoidanit.jobhunter.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vn.hoidanit.jobhunter.domain.Permissions;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.SecurityUtil;
import vn.hoidanit.jobhunter.utils.error.forbiddenException;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        if (email != null) {

            User user = userService.getUserByEmail(email);
            if (user != null) {
                List<Permissions> permissionsList = user.getRole().getPermissions();
                for (Permissions permissions : permissionsList) {
                    if (permissions.getApiPath().equals(path) && permissions.getMethod().equals(httpMethod)) {
                        return true;
                    }
                }
            }
        }

        throw new forbiddenException("You are not Allowed to access this resource");

    }

}
