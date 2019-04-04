package cn.yali.redis.exception;

/**
 * Created by Sterling on 2019/4/4.
 */
public class RedisException extends Exception {
    private int errorCode = 0;
    private String msg = "";

    public RedisException() {
    }

    public RedisException(String message) {
        super(message);
        this.msg = message;
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    public RedisException(int code, String message) {
        super(message);
        this.errorCode = code;
        this.msg = message;
    }

    public RedisException(int code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
        this.msg = message;
    }

    public RedisException(int code, Throwable cause) {
        super(cause);
        this.errorCode = code;
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
