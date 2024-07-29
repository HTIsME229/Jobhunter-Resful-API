package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.RestLoginDto;
import vn.hoidanit.jobhunter.domain.DTO.UserLoginDTO;
import vn.hoidanit.jobhunter.domain.DTO.loginDTO;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.SecurityUtil;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;
import vn.hoidanit.jobhunter.utils.error.InvalidLogin;

@RequestMapping("/api/v1")
@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestLoginDto restLoginDto = new RestLoginDto();


        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
//            Create Refresh Token
        User userLogin = this.userService.getUserByEmail(Email);
        UserLoginDTO userLoginDTO = new UserLoginDTO(userLogin.getEmail(), userLogin.getName(), userLogin.getId());
        restLoginDto.setUser(userLoginDTO);
        String AccessToken = this.securityUtil.CreateAccessToken(authentication, userLoginDTO);
        restLoginDto.setAccess_token(AccessToken);
        String refreshToken = this.securityUtil.CreateRefreshToken(Email, restLoginDto);
        this.userService.UpdateUserToken(refreshToken, Email);
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).path("/").maxAge(refreshTokenExpiration).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(restLoginDto);


    }

    @ApiMessage("Get Account Success")
    @GetMapping("/auth/account")
    public ResponseEntity<UserLoginDTO> getAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = this.userService.getUserByEmail(email);
        UserLoginDTO userLoginDTO = new UserLoginDTO(currentUser.getEmail(), currentUser.getName(), currentUser.getId());

        return ResponseEntity.ok(userLoginDTO);
    }

//    @ApiMessage("Get Account Success")
//    @GetMapping("/auth/refresh")
//    public ResponseEntity<UserLoginDTO> getRefreshToken() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        User currentUser = this.userService.
//        UserLoginDTO userLoginDTO = new UserLoginDTO(currentUser.getEmail(), currentUser.getName(), currentUser.getId());
//
//        return ResponseEntity.ok(userLoginDTO);
//    }

}
