package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorClinicRequest {

    private Long doctorId;
    private long clinicId;
}
