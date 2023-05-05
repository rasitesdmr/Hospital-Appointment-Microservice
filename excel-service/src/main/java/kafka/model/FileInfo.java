package kafka.model;

import lombok.*;

import java.io.InputStream;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    private String name;
    private String type;
    private Long size;
    private byte[] data;
    private InputStream inputStream;

}
