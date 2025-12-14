package lt.viko.bondarenko.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private PublicProjectDto projectInfo;
    private ConfigDto config;
}
