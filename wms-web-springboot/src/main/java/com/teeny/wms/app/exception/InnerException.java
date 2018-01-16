package com.teeny.wms.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InnerException
 * @since 2017/10/19
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InnerException extends RuntimeException {

    public InnerException(String message) {
        super(message);
    }
}
