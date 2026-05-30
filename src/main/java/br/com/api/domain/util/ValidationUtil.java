package br.com.api.domain.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> validaCampos(Object dto) {
        Map<String, Object> original = objectMapper.convertValue(
                dto,
                new TypeReference<>() {}
        );

        Map<String, Object> updateMap = new HashMap<>();

        flatten(original, "", updateMap);

        return updateMap;
    }

    private static void flatten(
            Map<String, Object> origem,
            String prefixo,
            Map<String, Object> destino) {

        for (var entry : origem.entrySet()) {
            String chave = prefixo.isEmpty()
                    ? entry.getKey()
                    : prefixo + "." + entry.getKey();

            Object valor = entry.getValue();

            if (valor instanceof Map<?, ?> subMapa) {
                flatten(
                        (Map<String, Object>) subMapa,
                        chave,
                        destino
                );
            } else if (valor != null) {
                destino.put(chave, valor);
            }
        }
    }
}
