package za.co.wedela.backend.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wedela.backend.model.role.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private String username;
    private String password;
    private String email;
    private Role role;
}
