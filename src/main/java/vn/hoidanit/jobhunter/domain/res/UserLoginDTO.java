package vn.hoidanit.jobhunter.domain.res;

import vn.hoidanit.jobhunter.domain.Role;

public class UserLoginDTO {
    private String email;
    private String name;
    private long id;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserLoginDTO(String email, String name, long id, Role role) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public UserLoginDTO() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
