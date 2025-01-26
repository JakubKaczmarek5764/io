package Security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
public class JwtConfig {
    //Na potrzeby testów, oraz dlatego, że nie wiem czemu testy nie pobierają tych wartości są zhardcodowane xD

    public SecretKey getSecretKey() {
        String secretKey = "8pEoo9OqhE0D25AEIZHyvKLUZMBg3MSMq0DVTFOHyqo=";
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public long getExpiration() {
        // W sekundach
        return 3600;
    }
}

