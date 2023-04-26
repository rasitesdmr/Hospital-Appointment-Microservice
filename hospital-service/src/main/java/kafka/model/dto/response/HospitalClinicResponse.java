package kafka.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalClinicResponse {

    private long hospitalId;
    private long clinicId;
}
