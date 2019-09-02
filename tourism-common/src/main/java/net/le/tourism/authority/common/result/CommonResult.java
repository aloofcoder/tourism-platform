package net.le.tourism.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.le.tourism.common.exception.ErrorCode;
import net.le.tourism.common.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-05-18
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
public class CommonResult {

    /**
     * 成功时直接返回无数据
     */
    public CommonResult() {
        Head head = new Head();
        head.setRet(ErrorCode.sys_ok.getCode());
        head.setMsg(ErrorCode.sys_ok.getMsg());
        HttpServletRequest request = ServletUtils.getRequest();
        head.setCmd(request.getRequestURI());
        this.head = head;
    }

    /**
     * 成功时返回带数据的构造器
     * @param data
     */
    public CommonResult(Object data) {
        HttpServletRequest request = ServletUtils.getRequest();
        Head head = new Head(ErrorCode.sys_ok.getCode(),
                ErrorCode.sys_ok.getMsg(), request.getRequestURI());
        this.head = head;
        this.body = data;
    }

    /**
     * 自定义返回状态码无数据
     * @param errorCode
     */
    public CommonResult(ErrorCode errorCode) {
        HttpServletRequest request = ServletUtils.getRequest();
        Head head = new Head(errorCode.getCode(),
                errorCode.getMsg(), request.getRequestURI());
        this.head = head;
    }

    /**
     * 自定义返回状态码无数据
     * @param errorCode
     */
    public CommonResult(Integer errorCode, String message) {
        HttpServletRequest request = ServletUtils.getRequest();
        Head head = new Head(errorCode,
                message, request.getRequestURI());
        this.head = head;
    }

    /**
     * 自定义返回状态码和数据
     * @param errorCode
     * @param data
     */
    public CommonResult(ErrorCode errorCode, Object data) {
        HttpServletRequest request = ServletUtils.getRequest();
        Head head = new Head(errorCode.getCode(),
                errorCode.getMsg(), request.getRequestURI());
        this.head = head;
        this.body = data;
    }

    private Head head;

    private Object body;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Head {
        private Integer ret;
        private String msg;
        private String cmd;
    }
}

