package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalClinicRequest {

    private long hospitalId;
    private long clinicId;
}
