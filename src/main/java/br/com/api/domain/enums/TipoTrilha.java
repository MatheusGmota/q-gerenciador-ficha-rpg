package br.com.api.domain.enums;

public enum TipoTrilha {

    // --- COMBATENTE ---
    AGENTE_SECRETO("Agente Secreto", TipoClasse.COMBATENTE),
    ANIQUILADOR("Aniquilador", TipoClasse.COMBATENTE),
    CACADOR("Caçador", TipoClasse.COMBATENTE),
    COMANDANTE_DE_CAMPO("Comandante de Campo", TipoClasse.COMBATENTE),
    GUERREIRO_MONSTRUOSO("Guerreiro Monstruoso", TipoClasse.COMBATENTE),
    OPERACOES_ESPECIAIS("Operações Especiais", TipoClasse.COMBATENTE),
    TROPA_DE_CHOQUE("Tropa de Choque", TipoClasse.COMBATENTE),

    // --- ESPECIALISTA ---
    ATIRADOR_DE_ELITE("Atirador de Elite", TipoClasse.ESPECIALISTA),
    BIBLIOTECARIO("Bibliotecário", TipoClasse.ESPECIALISTA),
    INFILTRADOR("Infiltrador", TipoClasse.ESPECIALISTA),
    MEDICO_DE_CAMPO("Médico de Campo", TipoClasse.ESPECIALISTA),
    MUAMBEIRO("Muambeiro", TipoClasse.ESPECIALISTA),
    NEGOCIADOR("Negociador", TipoClasse.ESPECIALISTA),
    PERSEVERANTE("Perseverante", TipoClasse.ESPECIALISTA),
    TECNICO("Técnico", TipoClasse.ESPECIALISTA),

    // --- OCULTISTA ---
    CONDUITE("Conduíte", TipoClasse.OCULTISTA),
    EXORCISTA("Exorcista", TipoClasse.OCULTISTA),
    FLAGELADOR("Flagelador", TipoClasse.OCULTISTA),
    GRADUADO("Graduado", TipoClasse.OCULTISTA),
    INTUITIVO("Intuitivo", TipoClasse.OCULTISTA),
    LAMINA_PARANORMAL("Lâmina Paranormal", TipoClasse.OCULTISTA),
    PARAPSICOLOGO("Parapsicólogo", TipoClasse.OCULTISTA),
    POSSUIDO("Possuído", TipoClasse.OCULTISTA);

    private final String valor;
    private final TipoClasse classe;

    TipoTrilha(String valor, TipoClasse classe) {
        this.valor = valor;
        this.classe = classe;
    }

    public boolean pertenceAClasse(TipoClasse classe) {
        return this.classe == classe;
    }
}
