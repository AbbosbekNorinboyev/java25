package uz.brb.java25.service;

import org.springframework.data.domain.Pageable;
import uz.brb.java25.dto.request.UserRequest;
import uz.brb.java25.dto.response.Response;

public interface UserService {

    Response<?> createUser(UserRequest request);

    Response<?> getAllUsers(Pageable pageable);

    Response<?> getUser(Long id);

    Response<?> updateUser(Long id, UserRequest request);

    Response<?> deleteUser(Long id);
}
