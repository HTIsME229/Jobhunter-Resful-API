package vn.hoidanit.jobhunter.domain.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReqUpdateSkill {
    @NotNull(message = "Id Is Required")

    private long id;
    @NotEmpty(message = "Name Is Required")
    private String name;

    public ReqUpdateSkill() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
