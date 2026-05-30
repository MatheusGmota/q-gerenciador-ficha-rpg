package br.com.api.domain.enums;

public enum TipoOrigem {
    ACADEMICO("Acadêmico"),
    AGENTE_DE_SAUDE("Agente de Saúde"),
    AMNESICO("Amnésico"),
    ARTISTA("Artista"),
    ATLETA("Atleta"),
    CHEF("Chef"),
    CIENTISTA_FORENSE("Cientista Forense"),
    CRIMINOSO("Criminoso"),
    CULTISTA_ARREPENDIDO("Cultista Arrependido"),
    DESGARRADO("Desgarrado"),
    DUBLE("Dublê"),
    ENGENHEIRO("Engenheiro"),
    ESCRITOR("Escritor"),
    EXECUTIVO("Executivo"),
    GAUDERIO_ABUTRE("Gaudério Abutre"),
    GINASTA("Ginasta"),
    INVESTIGADOR("Investigador"),
    JORNALISTA("Jornalista"),
    LUTADOR("Lutador"),
    MAGNATA("Magnata"),
    MERCENARIO("Mercenário"),
    MILITAR("Militar"),
    OPERARIO("Operário"),
    POLICIAL("Policial"),
    PROFESSOR("Professor"),
    RELIGIOSO("Religioso"),
    REVOLTADO("Revoltado"),
    SERVIDOR_PUBLICO("Servidor Público"),
    TEORICO_DA_CONSPIRACAO("Teórico da Conspiração"),
    TI("T.I."),
    TRABALHADOR_RURAL("Trabalhador Rural"),
    TRAMBIQUEIRO("Trambiqueiro"),
    UNIVERSITARIO("Universitário"),
    VITIMA("Vítima");

    private final String valor;

    TipoOrigem(String valor) {
        this.valor = valor;
    }
}
