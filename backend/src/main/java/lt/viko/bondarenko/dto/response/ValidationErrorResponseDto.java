package lt.viko.bondarenko.dto.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ValidationErrorResponseDto {
    private Map<String, String> errors;
}