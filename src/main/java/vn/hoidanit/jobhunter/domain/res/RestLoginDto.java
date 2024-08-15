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

    public static class UserInsideToken {
        private long id;
        private String name;
        private String email;

        public UserInsideToken(long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
