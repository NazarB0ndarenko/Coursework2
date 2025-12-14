package lt.viko.bondarenko.dto.response;

import java.time.LocalDateTime;

import lt.viko.bondarenko.entity.ProjectMember;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PublicProjectDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime memberSince;
    private OwnerShortDto owner;
    private boolean member;
    private boolean hasPendingRequest;
    private ProjectMember.Role projectRole;
}
