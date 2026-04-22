package uz.brb.java25.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponse {
    private Long id;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
}
