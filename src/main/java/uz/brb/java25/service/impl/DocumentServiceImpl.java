package uz.brb.java25.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.java25.dto.request.DocumentRequest;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.Document;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.DocumentRepository;
import uz.brb.java25.service.DocumentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final EntityManager entityManager;

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
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Response<?> getAll(String search, Pageable pageable) {
        if (search == null || search.trim().isEmpty()) {
            throw CustomException.badRequest("Search parameter cannot be empty");
        }
        List<Objects[]> documents = entityManager.createQuery(
                        "SELECT d FROM Document d" +
                                " WHERE d.url LIKE :search")
                .setParameter("search", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
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
