package uz.brb.java25.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.java25.dto.request.DocumentRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.Document;
import uz.brb.java25.repository.DocumentRepository;
import uz.brb.java25.service.DocumentService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public Response<?> create(DocumentRequest request) {
        Document document = Document.builder()
                .url(request.getUrl())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
        documentRepository.save(document);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Document successfully created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll() {
        List<Document> documents = documentRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Document list successfully found")
                .data(documents)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
