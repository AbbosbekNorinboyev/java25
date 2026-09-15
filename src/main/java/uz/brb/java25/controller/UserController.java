package uz.brb.java25.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.brb.java25.dto.request.UserRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public Response<?> createUser(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/getAll")
    public Response<?> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @GetMapping("/get/{id}")
    public Response<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/update/{id}")
    public Response<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public Response<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
