package com.zws.demo.web.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Aspect
@Component
public class ValidateAspect {

    @Around("execution(* com.zws.demo.web.controller.UserController.*(..))")
    public Object handleValidateResult(ProceedingJoinPoint pjp) throws Throwable {


        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult errors = (BindingResult)arg;
                if (errors.hasErrors()) {
                    throw new ValidateException(errors.getAllErrors());
                }
            }
        }

        Object result = pjp.proceed();

        return result;
    }

}
