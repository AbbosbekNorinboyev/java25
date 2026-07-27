package uz.brb.java25.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.brb.java25.config.CustomUserDetailsService;
import uz.brb.java25.dto.request.LoginRequest;
import uz.brb.java25.dto.request.RegisterRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.AuthUser;
import uz.brb.java25.enums.Role;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.AuthUserRepository;
import uz.brb.java25.service.AuthUserService;
import uz.brb.java25.util.JWTUtil;

import java.time.LocalDateTime;
import java.util.Optional;

import static uz.brb.java25.util.PasswordHasher.hashPassword;
import static uz.brb.java25.util.PasswordValidator.validatePassword;
import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final JWTUtil jwtUtil;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Response<?> register(RegisterRequest registerRequest) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerRequest.getUsername());
        if (byUsername.isPresent()) {
            throw CustomException.badRequest("Username already exists");
        }
        AuthUser authUser = new AuthUser();
        authUser.setFullName(registerRequest.getFullName());
        authUser.setUsername(registerRequest.getUsername());
        authUser.setPassword(hashPassword(registerRequest.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AuthUser successfully register")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> login(LoginRequest loginRequest) {
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> CustomException.notFound("AuthUser not found by username: " + loginRequest.getUsername()));
        if (!validatePassword(loginRequest.getPassword(), authUser.getPassword())) {
            throw CustomException.badRequest("Invalid password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message(jwtToken)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> me(AuthUser authUser) {
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Current User")
                .data(authUser)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}