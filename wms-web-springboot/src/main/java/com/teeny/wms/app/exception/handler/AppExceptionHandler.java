package com.teeny.wms.app.exception.handler;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.app.model.ResponseEntity;
import com.teeny.wms.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AppExceptionHandler
 * @since 2017/10/19
 */
@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger sLogger = LoggerFactory.getLogger(AppExceptionHandler.class);

    private static final String MESSAGE_UNKNOWN_EXCEPTION = "服务器内部错误.";

    private static final int CODE_UNKNOWN_EXCEPTION = 0x1f4;
    private static final int CODE_INNER_EXCEPTION = 0x1;

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity handleInnerException(Exception ex) {
        int code = CODE_UNKNOWN_EXCEPTION;
        if (ex instanceof InnerException) {
            code = CODE_INNER_EXCEPTION;
        }
        String message = ex.getMessage();
        if (Validator.isEmpty(message)) {
            message = MESSAGE_UNKNOWN_EXCEPTION;
        }
        return ResponseEntity.create(code, message);
    }
}
