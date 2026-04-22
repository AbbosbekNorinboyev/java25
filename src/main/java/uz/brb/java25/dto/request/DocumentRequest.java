package uz.brb.java25.dto.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequest {
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
}
