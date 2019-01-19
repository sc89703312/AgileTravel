package nju.agile.travel.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by ShirokoSama on 2019/1/19.
 */
@Aspect
@Component
public class HttpLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * nju.agile.travel.controller..*.*(..))")
    public void logHttpMessage() {}

    @Before("logHttpMessage()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.info("URL : " + request.getRequestURL().toString());
            logger.info("HTTP_METHOD : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        }
        else
            logger.error(joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName() + " have no ServletRequestAttributes");
    }

}
