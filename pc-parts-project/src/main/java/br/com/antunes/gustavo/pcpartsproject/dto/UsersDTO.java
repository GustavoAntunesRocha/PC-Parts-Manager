package br.com.antunes.gustavo.pcpartsproject.dto;

import br.com.antunes.gustavo.pcpartsproject.model.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {

    private int id;

    @NotBlank
    private String email;

    private String role;

    public UsersDTO() {
    }

    public UsersDTO(int id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static UsersDTO fromUser(Users user) {
        return new UsersDTO(user.getId(), user.getEmail(), user.getRole());
    }

}
