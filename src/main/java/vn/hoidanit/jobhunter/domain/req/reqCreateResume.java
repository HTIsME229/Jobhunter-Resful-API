package vn.hoidanit.jobhunter.domain.req;

import vn.hoidanit.jobhunter.utils.Enum.Status;

public class reqCreateResume {
    private String email;
    private String url;
    private User_Id user;
    private Job_Id job;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Job_Id getJob() {
        return job;
    }

    public void setJob(Job_Id job) {
        this.job = job;
    }

    public User_Id getUser() {
        return user;
    }

    public void setUser(User_Id user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class User_Id {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public static class Job_Id {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
