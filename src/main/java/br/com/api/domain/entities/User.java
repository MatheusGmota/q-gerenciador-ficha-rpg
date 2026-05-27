package br.com.api.domain.entities;

import br.com.api.domain.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String uid;

    private String username;
    private String email;
    private String telefone;
    private String password;
    private String photoUrl;

    private UserRole userRole;
}