package uz.brb.java25.service.impl;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.java25.dto.request.UserRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.dto.response.UserResponse;
import uz.brb.java25.entity.AuthUser;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.AuthUserRepository;
import uz.brb.java25.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.java25.util.PasswordHasher.hashPassword;
import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;

    @Override
    @Transactional
    public Response<?> createUser(UserRequest request) {
        if (authUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw CustomException.badRequest("Username already exists");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw CustomException.badRequest("password can not be null or empty");
        }
        AuthUser authUser = AuthUser.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(hashPassword(request.getPassword()))
                .role(request.getRole())
                .build();
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("User successfully created")
                .data(toResponse(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Response<?> getAllUsers(Pageable pageable) {
        Page<@NonNull AuthUser> page = authUserRepository.findAll(pageable);
        List<UserResponse> users = page.map(this::toResponse).toList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("User list successfully found")
                .data(users)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .elements(page.getTotalElements())
                .pages(page.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Response<?> getUser(Long id) {
        AuthUser authUser = findById(id);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("User successfully found")
                .data(toResponse(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateUser(Long id, UserRequest request) {
        AuthUser authUser = findById(id);
        authUserRepository.findByUsername(request.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw CustomException.badRequest("Username already exists");
                });
        authUser.setFullName(request.getFullName());
        authUser.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            authUser.setPassword(hashPassword(request.getPassword()));
        }
        if (request.getRole() != null) {
            authUser.setRole(request.getRole());
        }
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("User successfully updated")
                .data(toResponse(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional
    public Response<?> deleteUser(Long id) {
        AuthUser authUser = findById(id);
        authUserRepository.delete(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("User successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    private AuthUser findById(Long id) {
        return authUserRepository.findById(id)
                .orElseThrow(() -> CustomException.notFound("User not found by id: " + id));
    }

    private UserResponse toResponse(AuthUser authUser) {
        return UserResponse.builder()
                .id(authUser.getId())
                .fullName(authUser.getFullName())
                .username(authUser.getUsername())
                .role(authUser.getRole())
                .createdAt(authUser.getCreatedAt())
                .updatedAt(authUser.getUpdatedAt())
                .build();
    }
}
