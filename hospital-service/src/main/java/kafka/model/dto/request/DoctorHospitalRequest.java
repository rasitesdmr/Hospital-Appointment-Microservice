package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorHospitalRequest {

    private Long doctorId;
    private long hospitalId;
}
