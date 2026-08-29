package br.com.api.infra.security;

import com.google.firebase.auth.FirebaseToken;
import lombok.Getter;

import java.security.Principal;

@Getter
public class FirebaseUserPrincipal implements Principal {

    private final FirebaseToken firebaseToken;

    public FirebaseUserPrincipal(FirebaseToken firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    @Override
    public String getName() {
        return firebaseToken.getName();
    }

    public String getUid() {
        return firebaseToken.getUid();
    }

    public String getEmail() {
        return firebaseToken.getEmail();
    }

    public Object getClaim(String nome) {
        return firebaseToken.getClaims().get(nome);
    }

    public boolean isAdmin() {
        return Boolean.TRUE.equals(getClaim("admin"));
    }

}