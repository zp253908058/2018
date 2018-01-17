package com.teeny.wms.app.config;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.UserWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserHandlerMethodArgumentResolver
 * @since 2018/1/15
 */
@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        UserWrapper userWrapper = (UserWrapper) nativeWebRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
//        UserEntity entity = new UserEntity();
//        entity.setId(25);
//        entity.setUsername("张三");
//        entity.setSerialNumber("1701");
//        return entity;
        Principal principal = webRequest.getUserPrincipal();
        System.out.println(principal);
        UserEntity entity = new UserEntity();
        entity.setId(25);
        entity.setUsername("张三");
        entity.setSerialNumber("1701");
        return entity;
    }
}
