package penyaka.petproject.spring_rest_web_mvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class CustomerDTO {
    private UUID id;
    private Integer version;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
