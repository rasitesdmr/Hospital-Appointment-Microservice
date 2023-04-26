package kafka.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorClinicResponse {

    private long doctorId;
    private long clinicId;
}
