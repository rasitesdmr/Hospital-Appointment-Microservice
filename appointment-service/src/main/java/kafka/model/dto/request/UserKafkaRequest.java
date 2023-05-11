package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserKafkaRequest {

    private String identityNumber;
    private String firstName;
    private String lastName;
    private String email;

}
