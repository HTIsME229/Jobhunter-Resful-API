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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.GetAccoutDTO;
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
        String AccessToken = this.securityUtil.CreateAccessToken(userLogin.getEmail(), userLoginDTO);
        restLoginDto.setAccess_token(AccessToken);
        String refreshToken = this.securityUtil.CreateRefreshToken(Email, restLoginDto);
        this.userService.UpdateUserToken(refreshToken, Email);
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).path("/").maxAge(refreshTokenExpiration).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(restLoginDto);


    }

    @ApiMessage("Get Account Success")
    @GetMapping("/auth/account")
    public ResponseEntity<GetAccoutDTO> getAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = this.userService.getUserByEmail(email);
        UserLoginDTO userLoginDTO = new UserLoginDTO(currentUser.getEmail(), currentUser.getName(), currentUser.getId());
        GetAccoutDTO res = new GetAccoutDTO();
        res.setUser(userLoginDTO);
        return ResponseEntity.ok(res);
    }

    @ApiMessage("Get new refresh token success")
    @GetMapping("/auth/refresh")
    public ResponseEntity<RestLoginDto> getRefreshToken(@CookieValue(name = "refresh_token") String refresh_token) {
        //check valid token
        Jwt tokenDecode = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = tokenDecode.getSubject();
        User currentUser = this.userService.handleFindByEmailAndRefreshToken(email, refresh_token);
        if (currentUser != null) {
            RestLoginDto restLoginDto = new RestLoginDto();
            UserLoginDTO userLoginDTO = new UserLoginDTO(currentUser.getEmail(), currentUser.getName(), currentUser.getId());
            String AccessToken = this.securityUtil.CreateAccessToken(currentUser.getEmail(), userLoginDTO);
            restLoginDto.setAccess_token(AccessToken);
            restLoginDto.setUser(userLoginDTO);
            String newRefreshToken = this.securityUtil.CreateRefreshToken(currentUser.getEmail(), restLoginDto);
            this.userService.UpdateUserToken(newRefreshToken, currentUser.getEmail());
            ResponseCookie responseCookie = ResponseCookie.from("refresh_token", newRefreshToken).httpOnly(true).path("/").maxAge(refreshTokenExpiration).build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(restLoginDto);
        }
        throw new RuntimeException("Token is not valid");


    }

    @ApiMessage("Logout  Success")
    @GetMapping("/logout")
    public ResponseEntity<Void> Logout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = this.userService.getUserByEmail(email);
        if (currentUser != null) {
            this.userService.handleRemoveRefreshToken(currentUser);
        }

        return ResponseEntity.ok().build();
    }

}
