package br.com.api.infra.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@ApplicationScoped
public class FirebaseConfig {

    @ConfigProperty(name = "google.cloud.credentials-path", defaultValue = "serviceAccount.json")
    String credentialsPath;

    private Firestore firestore;

    void onStart(@Observes StartupEvent ev) {
        if (firestore != null) return;

        try {
            FileInputStream serviceAccount = new FileInputStream(credentialsPath);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            var options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);

                log.info("FirebaseApp inicializado app-name={}", app.getName());
            }

            firestore = FirestoreClient.getFirestore();
            log.info("Firestore inicializado project-id={}", credentials.getProjectId());
        } catch (IOException ioe) {
            log.error("Erro ao capturar arquivo");
            throw new RuntimeException("Arquivo de credenciais não encontrado: "+ credentialsPath);
        } catch (Exception e) {
            log.error("Erro ao inicializar Firebase", e);
            throw new RuntimeException("Erro ao inicializar Firebase", e);
        }
    }

    @Produces
    @ApplicationScoped
    public Firestore produceFirestore() {
        if (firestore == null) {
            throw new IllegalStateException("Firestore não foi inicializado");
        }

        return firestore;
    }
}
