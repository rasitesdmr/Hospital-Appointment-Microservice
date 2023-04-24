package kafka.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String identityNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
