package vn.hoidanit.jobhunter.domain.res;

public class RestResponse<T> {
    private int statusCode;
    private String error;
    private T data;
    private Object message;


    public void setMessage(Object message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


}
