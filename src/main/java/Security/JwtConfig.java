package Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {
    //Na potrzeby testów, oraz dlatego, że nie wiem czemu testy nie pobierają tych wartości są zhardcodowane xD

    @Value("${jwt.secret}")
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);;

    @Value("${jwt.expiration}")
    private final long expiration = 3600; // W sekundach

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public long getExpiration() {
        return expiration;
    }
}

