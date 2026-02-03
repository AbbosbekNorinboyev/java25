package uz.brb.java25.aspect;

import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.brb.java25.entity.AuthUser;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<@NonNull Long> {

    @Nonnull
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        // 👇 UserDetails dan ID olish
        if (principal instanceof AuthUser userDetails) {
            return Optional.ofNullable(userDetails.getId()); // foydalanuvchi ID
        }
        return Optional.empty(); // principal AuthUser emas bo‘lsa
    }
}