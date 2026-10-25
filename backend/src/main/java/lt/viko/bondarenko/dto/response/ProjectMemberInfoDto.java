package lt.viko.bondarenko.dto.response;

import java.time.LocalDateTime;

import lt.viko.bondarenko.entity.ProjectMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberInfoDto {
    private Long userId;
    private Long projectMemberId;
    private String email;
    private String firstName;
    private String lastName;
    private ProjectMember.Role projectRole;
    private LocalDateTime joinDate;
}
