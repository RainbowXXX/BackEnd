package site.rainbowx.backend.aspect;

import com.alibaba.fastjson2.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import site.rainbowx.backend.annotation.SubController;
import site.rainbowx.backend.utils.ParameterUtils;

import java.lang.reflect.Method;

@Aspect
@Component
public class SubControllerAspect {

    @Pointcut("@annotation(SubController)")
    public void validateReturnValuePointcut() {}

    @AfterReturning(pointcut = "validateReturnValuePointcut()", returning = "result")
    public void validateReturnValue(JoinPoint joinPoint, Object result) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SubController annotation = method.getAnnotation(SubController.class);

        if (result instanceof JSONObject value) {
            ParameterUtils.ReturnChecker(value);
        } else {
            throw new IllegalArgumentException("The return value of the method "
                    + method.getName() + " is not a number");
        }
    }
}

