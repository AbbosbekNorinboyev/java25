package uz.brb.java25.service;

import uz.brb.java25.dto.request.DocumentRequest;
import uz.brb.java25.dto.response.Response;

public interface DocumentService {
    Response<?> create(DocumentRequest request);

    Response<?> getAll(String search);
}