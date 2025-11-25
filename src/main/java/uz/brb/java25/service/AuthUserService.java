package uz.brb.java25.service;

import uz.brb.java25.dto.request.LoginRequest;
import uz.brb.java25.dto.request.RegisterRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.AuthUser;

public interface AuthUserService {
    Response<?> register(RegisterRequest registerRequest);

    Response<?> login(LoginRequest loginRequest);

    Response<?> me(AuthUser authUser);
}