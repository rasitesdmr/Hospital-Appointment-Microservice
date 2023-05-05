package kafka.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalRequest {


    private String name;

    private String address;

    private long cityId;

}
