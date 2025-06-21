package br.com.petsalus.enums;

public enum RegistroMedicoStatus {
    EMERGENCIA("Emergência"),
    ROTINA_AGENDAMENTO("Rotina/Agendamento");

    private final String descricao;

    RegistroMedicoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
