package lt.viko.bondarenko.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HasUnreadDto {
    private Boolean hasUnreadMessages;

    public boolean getHasUnread() {
    return hasUnreadMessages;
    }
}
