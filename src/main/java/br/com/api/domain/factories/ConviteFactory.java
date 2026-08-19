package br.com.api.domain.factories;

import br.com.api.domain.entities.subcollections.Convite;
import br.com.api.domain.enums.StatusConvite;
import com.google.cloud.Timestamp;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;

@ApplicationScoped
public class ConviteFactory {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TAMANHO_TOKEN = 24;
    private static final SecureRandom RANDOM = new SecureRandom();

    public Convite criar(String nomeCampanha) {
        Convite convite = new Convite();
        convite.setNomeCampanha(nomeCampanha);
        convite.setToken(gerarToken());
        convite.setStatus(StatusConvite.ATIVO);
        convite.setCriadoEm(Timestamp.now());
        return convite;
    }

    private String gerarToken() {
        StringBuilder sb = new StringBuilder(TAMANHO_TOKEN);
        for (int i = 0; i < TAMANHO_TOKEN; i++) {
            sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
        }
        return sb.toString();
    }
}