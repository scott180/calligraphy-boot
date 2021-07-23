package com.xu.calligraphy.boot.common.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xu.calligraphy.boot.common.CalligraphyBootException;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.util.CommonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author xu
 * @date 2020/1/11
 * https://www.cnblogs.com/moris5013/p/11026653.html
 * https://www.cnblogs.com/bigdataZJ/p/springboot-log.html
 */
@Aspect
@Component
@Order(1)
public class AdviceAspectLogConfig {

    private static final Logger logger = LoggerFactory.getLogger(AdviceAspectLogConfig.class);

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 每种通配符表示的含义: | *表示任意字符  | ..表示本包和子包 或者是任意参数  |
     * 切入点：修饰符是public ,返回值任意类型，  service包和他的子包，以Service结尾的类，任意的方法
     */
    @Pointcut("execution(* com.xu.calligraphy.boot.*.*.*(..)) ")
    public void aroundMethod() {
    }


    /**
     * 可以在方法执行的前后添加非功能性的代码
     */
    @Around("aroundMethod()")
    public java.lang.Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Long begin = System.currentTimeMillis();
        java.lang.Object result = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String target = String.format("targetMethod=%s_%s_%s", targetMethod.getName(), joinPoint.getTarget(), begin);
        java.lang.Object[] args = joinPoint.getArgs();
        String argsText = JSON.toJSONString(args);
        try {
            logger.info("###{}.begin,argsText={}", target, argsText);
            result = joinPoint.proceed(joinPoint.getArgs());
            logger.info("###{}.after,argsText={} ", target, argsText);
        } catch (CalligraphyBootException e) {
            logger.error("###{}.exception,CalligraphyBootException ：{}", target, e.getMessage(), e);
            result = Result.error(e);
        } catch (Throwable e) {
            JSONObject object = new JSONObject(new LinkedHashMap<>());
            object.put(String.format("CalligraphyBoot【%s】接口", profile), target);
            object.put("时间", CommonUtil.SIMPLE_DATE_FORMAT.format(new Date()));
            object.put("操作者", "test");
            object.put("参数", argsText);
            object.put("异常", e.getClass());
            object.put("原因", e.getMessage());
            logger.error("###{} Throwable ：{}", target, object.toJSONString(), e);
//            dingDingRobotUtil.sendDingDingRobotMessage(object.toJSONString());
            result = Result.error(StringUtils.isEmpty(e.getMessage()) ? "fail" : e.getMessage());
        } finally {
            logger.info("###{}.finally,result={}", target, JSON.toJSONString(result));
            Long end = System.currentTimeMillis();
            logger.info("###{}.end,spend_time={}ms", target, end - begin);
        }
        return result;
    }

}