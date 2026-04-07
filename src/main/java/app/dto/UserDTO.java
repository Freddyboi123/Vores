package app.dto;

import app.entities.Roles;
import app.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties (ignoreUnknown = true)
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();

    public UserDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        roles.add(user.getRolesAsString());

    }

    public UserDTO(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        roles.add(role);
        this.email = email;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UserDTO dto = (UserDTO)o;
            return Objects.equals(this.username, dto.username) && Objects.equals(this.roles, dto.roles);
        } else {
            return false;
        }
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.username, this.roles});
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public String toString() {
        String var10000 = this.getUsername();
        return "UserDTO(username=" + var10000 + ", password=" + this.getPassword() + ", roles=" + this.getRoles() + ")";
    }

    public UserDTO(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO() {
    }
    public static class UserDTOBuilder {
        private String username;
        private String password;
        private Set<String> roles;

        UserDTOBuilder() {
        }

        public UserDTO.UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTO.UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTO.UserDTOBuilder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this.username, this.password, this.roles);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(username=" + this.username + ", password=" + this.password + ", roles=" + this.roles + ")";
        }
    }
}


