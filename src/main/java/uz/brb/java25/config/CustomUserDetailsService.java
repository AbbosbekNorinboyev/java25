package uz.brb.java25.config;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.AuthUserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;

    @Nonnull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> CustomException.userNotFound("Username not found by username: " + username));
    }
}