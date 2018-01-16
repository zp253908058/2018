package com.teeny.wms.app.config;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.UserWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import java.security.Principal;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WmsAutoApprovalHandler
 * @since 2018/1/15
 */
public class WmsAutoApprovalHandler implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (methodParameter.getParameterType() == UserEntity.class && methodParameter.getParameterAnnotation(User.class) != null) {
            Principal principal = webRequest.getUserPrincipal();
            UserWrapper userDetails = (UserWrapper) ((Authentication) principal).getPrincipal();
            return userDetails.getUserEntity();
        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
    }
}
