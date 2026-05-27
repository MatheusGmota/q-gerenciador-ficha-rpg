package br.com.api.infra.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
@ApplicationScoped
public class FirebaseConfig {

    @ConfigProperty(name = "google.cloud.credentials-path", defaultValue = "serviceAccount.json")
    String credentialsPath;

    @ConfigProperty(name = "google.cloud.database-id", defaultValue = "(default)")
    String databaseId;

    private Firestore firestore;

    void onStart(@Observes StartupEvent ev) {
        if (firestore != null) return;

        try {
            GoogleCredentials credentials = getGoogleCredentials();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
//                    .setProjectId(credentials.getProjectId())
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);

                log.info("FirebaseApp inicializado app-name={}", app.getName());
            }

            firestore = FirestoreOptions.getDefaultInstance()
                    .toBuilder()
                    .setCredentials(credentials)
//                    .setProjectId(credentials.getProjectId())
                    .setDatabaseId(databaseId)
                    .build()
                    .getService();

            log.info("Firestore inicializado project-id={} database-id={}", credentials.getProjectId(), databaseId);
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

    private GoogleCredentials getGoogleCredentials() throws IOException {

        try {
            return GoogleCredentials.getApplicationDefault();

        } catch (Exception e) {
            log.warn("Application Default Credentials não encontradas. Usando arquivo local.");

            InputStream inputStream =
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream(credentialsPath);

            if (inputStream == null) {
                throw new RuntimeException("Arquivo de credenciais não encontrado: " + credentialsPath);
            }
            return GoogleCredentials.fromStream(inputStream);
        }
    }
}
