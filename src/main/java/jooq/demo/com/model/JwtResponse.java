package jooq.demo.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import jooq.demo.com.entites.Role;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class JwtResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String type = "Bearer";
    @JsonProperty("expired_time")
    private long expiredTime ;
    private Long id;
    private String username;
    private Set<Role> roles;

    public JwtResponse(String accessToken, long expiredTime, Long id, String username, Set<Role> roles) {
        this.accessToken = accessToken;
        this.expiredTime  = expiredTime;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}
