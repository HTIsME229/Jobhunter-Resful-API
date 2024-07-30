package vn.hoidanit.jobhunter.domain.res;

import java.util.List;

public class RestPaginateDTO<T> {
    private Meta meta;

    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
