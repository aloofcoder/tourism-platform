package net.le.tourism.authority.exception;

import lombok.extern.slf4j.Slf4j;
import net.le.tourism.authority.result.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * 异常统一处理类
 *
 * @author hanle
 * @version v1.0
 * @date 2019-05-18
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数绑定异常统一处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public CommonResult paramException(BindException e) {
        log.error("请求参数错误，{}！", e.getFieldError().getDefaultMessage());
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_param_error.getCode(),
                e.getFieldError().getDefaultMessage());
        return result;
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult paramException(MethodArgumentNotValidException e) {
        log.error("请求参数错误，{}！", e.getBindingResult().getFieldError().getDefaultMessage());
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_param_error.getCode(),
                e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }

    /**
     * 自定义异常统一处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AppServiceException.class)
    public CommonResult AppServiceException(AppServiceException e) {
        e.printStackTrace();
        log.error("代码执行出错，{}！", e.getMessage());
        return new CommonResult(e.getErrorCode(), e.getMessage());
    }

    /**
     * sql语法错误异常统一处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public CommonResult MySQLSyntaxException(SQLSyntaxErrorException e) {
        log.error("SQL语法错误！");
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_sql_syntax_error);
        return result;
    }

    /**
     * SQL错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public CommonResult sqlException(SQLException e) {
        log.error("执行sql语句错误！");
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_sql_error);
        return result;
    }

    /**
     * 上传文件过大
     */
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResult maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("执行sql语句错误！");
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_upload_file_to_max);
        return result;
    }
    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult defaultErrorHandler(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        CommonResult result = new CommonResult(ErrorCode.sys_default_error.getCode(), ErrorCode.sys_default_error.getMsg() + "," + e.getMessage());
        return result;
    }

}
