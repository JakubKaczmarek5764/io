package Security;

import Classes.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final JwtConfig jwtConfig;

    public JwtUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration() * 1000);

        return Jwts.builder()
                .setSubject(user.getLoginHash())
                .claim("nickName", user.getNickName())
                .claim("email", user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }
}
