package org.ashe.alpha.auth.domain.annotation;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.base.exc.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 自定义注解统一切面
 * <>p</>
 * 所用自定义注解的切面逻辑统一写入此类
 */
@Component
@Aspect
@Slf4j
public class CustomAspect {

    /*========================================= @Auth ==========================================*/

    /**
     * 权限注解切面逻辑实现
     */
    @Around("@annotation(org.ashe.alpha.auth.domain.annotation.Auth)")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取token
        String jwtToken = getJwtToken(joinPoint);
        if (ObjectUtils.isEmpty(jwtToken)) {
            throw new BusinessException("权限不足");
        }
        // TODO 通过token解析出请求者的身份
        /*
            甚至可以取当前方法名，与当前用户的角色，进行匹配，做到灵活限制权限
         */
        return joinPoint.proceed();
    }

    private String getJwtToken(ProceedingJoinPoint joinPoint) {
        String jwtToken;
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 从参数中筛选出HttpServletRequest对象
        Optional<Object> objectOptional = Arrays.stream(args).filter(HttpServletRequest.class::isInstance).findFirst();
        if (objectOptional.isPresent()) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) objectOptional.get();
            String authorization = httpServletRequest.getHeader("Authorization");
            jwtToken = authorization.substring("Bearer ".length());
        } else {
            throw new BusinessException("what's up ?");
        }
        return jwtToken;
    }

    /*========================================= @DoLog ==========================================*/


}
