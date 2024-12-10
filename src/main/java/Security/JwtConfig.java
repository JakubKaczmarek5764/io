package Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // W sekundach

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }
}

