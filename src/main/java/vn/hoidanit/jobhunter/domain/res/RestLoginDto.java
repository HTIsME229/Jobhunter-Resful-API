package vn.hoidanit.jobhunter.domain.res;

public class RestLoginDto {
    private String access_token;
    private UserLoginDTO user;

    public String getAccess_token() {
        return access_token;
    }

    public UserLoginDTO getUser() {
        return user;
    }

    public void setUser(UserLoginDTO user) {
        this.user = user;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
