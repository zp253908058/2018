package com.teeny.wms.app.exception.handler;

import com.teeny.wms.app.model.ResponseEntity;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HttpExceptionHandler
 * @since 2017/11/1
 */

@RestController
public class HttpExceptionHandler implements ErrorController {


    private final static String ERROR_PATH = "/error";

    /**
     * Supports other formats like JSON, XML
     *
     * @return error entity.
     */
    @RequestMapping(value = ERROR_PATH, produces = "application/json;charset=UTF-8")
    public ResponseEntity error() {
        return ResponseEntity.create(2, "访问错误.");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
