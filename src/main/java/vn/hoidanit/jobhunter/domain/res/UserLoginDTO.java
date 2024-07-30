package vn.hoidanit.jobhunter.domain.res;

public class UserLoginDTO {
    private String email;
    private String name;
    private long id;


    public UserLoginDTO(String email, String name, long id) {
        this.email = email;
        this.name = name;
        this.id = id;
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
