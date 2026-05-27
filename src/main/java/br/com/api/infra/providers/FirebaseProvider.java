package br.com.api.infra.providers;

import com.google.firebase.auth.FirebaseAuth;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FirebaseProvider {

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
