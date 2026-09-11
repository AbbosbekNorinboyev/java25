package uz.brb.java25.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.brb.java25.dto.request.RegionRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.service.RegionService;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping("/create")
    public Response<?> createRegion(@Valid @RequestBody RegionRequest request) {
        return regionService.createRegion(request);
    }

    @GetMapping("/getAll")
    public Response<?> getAllRegion(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return regionService.getAllRegion(PageRequest.of(page, size));
    }

    @GetMapping("/get/{id}")
    public Response<?> getRegion(@PathVariable Long id) {
        return regionService.getRegion(id);
    }

    @PutMapping("/update/{id}")
    public Response<?> updateRegion(@PathVariable Long id, @Valid @RequestBody RegionRequest request) {
        return regionService.updateRegion(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public Response<?> deleteRegion(@PathVariable Long id) {
        return regionService.deleteRegion(id);
    }
}
