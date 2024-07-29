package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.DTO.RestLoginDto;
import vn.hoidanit.jobhunter.domain.DTO.UserLoginDTO;
import vn.hoidanit.jobhunter.domain.DTO.loginDTO;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.SecurityUtil;
import vn.hoidanit.jobhunter.utils.error.InvalidLogin;

@RequestMapping("/api/v1")
@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<RestLoginDto> login(@Valid @RequestBody loginDTO loginDTO) throws InvalidLogin {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // create token
        String AccessToken = this.securityUtil.CreateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestLoginDto restLoginDto = new RestLoginDto();
        if (restLoginDto != null) {
            restLoginDto.setAccess_token(AccessToken);

            String Email = SecurityContextHolder.getContext().getAuthentication().getName();

            User userLogin = this.userService.getUserByEmail(Email);
            UserLoginDTO userLoginDTO = new UserLoginDTO(userLogin.getEmail(), userLogin.getName(), userLogin.getId());
            restLoginDto.setUser(userLoginDTO);
        }
        return ResponseEntity.ok().body(restLoginDto);


    }
}
