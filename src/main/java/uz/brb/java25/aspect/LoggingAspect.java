package uz.brb.java25.aspect;

import module java.base;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.brb.java25.service.ApiLogService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final ApiLogService apiLogService;

    // 🎯 Barcha controller methodlarini kuzatamiz
    @Pointcut("execution(* uz.brb.java25.controller..*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object writeLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // Metod nomi
        String methodName = joinPoint.getSignature().toShortString();

        // User (agar Spring Security ishlatsa)
        String username = "ANONYMOUS";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }

        // HTTP request path olish
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        // Method bajarilishi
        Object proceed = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        apiLogService.saveLog(username, methodName, path, LocalDateTime.now(), duration);
        return proceed;
    }
}
