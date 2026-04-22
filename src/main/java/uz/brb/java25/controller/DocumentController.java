package uz.brb.java25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.brb.java25.dto.request.DocumentRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody DocumentRequest request) {
        return documentService.create(request);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(name = "search", required = false) String search) {
        return documentService.getAll(search);
    }
}
