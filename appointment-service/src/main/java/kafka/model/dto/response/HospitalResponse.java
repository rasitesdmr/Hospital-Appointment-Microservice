package kafka.model.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {

    private Long id;

    private String name;

    private String address;

    private long cityId;

}
