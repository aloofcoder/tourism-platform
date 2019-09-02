package net.le.tourism.authority.common.exception;

import lombok.Getter;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-18
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Getter
public class AppServiceException extends RuntimeException {

    private Integer errorCode = 1;

    private String message;

    public AppServiceException() {
        super();
    }

    public AppServiceException(String message) {
        super(message);
        this.message = message;
    }

    public AppServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public AppServiceException(Throwable cause) {
        super(cause);
    }

    protected AppServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }

    public AppServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMsg();
    }
}
