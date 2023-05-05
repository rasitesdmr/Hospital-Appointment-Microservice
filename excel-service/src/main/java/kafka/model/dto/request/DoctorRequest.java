package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRequest {

    private String identityNumber;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String phoneNumber;

    private String email;

    private String profession;
}
