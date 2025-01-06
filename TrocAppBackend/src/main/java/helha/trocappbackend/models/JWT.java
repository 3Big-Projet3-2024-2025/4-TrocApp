package helha.trocappbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a JSON Web Token (JWT) containing an access token and a refresh token.
 * This class is used to encapsulate the token pair returned during authentication.
 */
@Data
@AllArgsConstructor
public class JWT {

    /**
     * The access token used for authentication and authorization.
     */
    private String accessToken;

    /**
     * The refresh token used to obtain a new access token when the current one expires.
     */
    private String refreshToken;
}