package uz.brb.java25.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ip addressni aniqlayadi
 */

@Component
public class RequestUtils {
    public String getClientIp() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) return "unknown";

        HttpServletRequest request = attributes.getRequest();

        String xfHeader = request.getHeader("x-forwarded-for");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
