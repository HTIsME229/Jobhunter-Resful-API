package vn.hoidanit.jobhunter.domain.req;

import jakarta.validation.constraints.NotEmpty;

public class reqLoginDTO {
    @NotEmpty(message = "Username must be not empty")
    private String username;
    @NotEmpty(message = "Password must be not empty")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
