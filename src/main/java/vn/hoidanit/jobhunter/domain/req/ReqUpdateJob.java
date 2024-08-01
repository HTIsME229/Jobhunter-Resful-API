package vn.hoidanit.jobhunter.domain.req;

public class ReqUpdateJob extends ReqCreateJob {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
