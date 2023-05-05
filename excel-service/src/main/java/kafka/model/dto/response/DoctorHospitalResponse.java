package kafka.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorHospitalResponse {

    private Long doctorId;
    private long hospitalId;
}
