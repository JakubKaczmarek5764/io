package Security;

public class LoginResponse {
    private String id;
    private String token;
    private String status;

    public LoginResponse(String id, String token, String status) {
        this.id = id;
        this.token = token;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
