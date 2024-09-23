package com.spring.boot.config.exception;

import com.spring.boot.util.constant.ResultCode;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author Yu Zhengmin
 */
@RestControllerAdvice
public class Exception {

    private static final Log log = LogFactory.getLog(Exception.class);

    @ExceptionHandler(java.lang.Exception.class)
    public Result<String> exception(java.lang.Exception e) {
        log.error("Unified exception handling: {}", e, e.getMessage());
        return ResultUtil.fail(e.getMessage(), ResultCode.INVALID_OPERATION);
    }

}
