package helha.trocappbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWT {
    private String accessToken;
    private String refreshToken;
}
