package lt.viko.bondarenko.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInteractionDto {
    private Long id;
    private UserShortDto user;
    private LocalDateTime initAt;
}