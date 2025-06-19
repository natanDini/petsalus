package br.com.petsalus.enums;

public enum TipoServico {
    CLINICO("Serviço Clínico/Médico"),
    PET_SHOP("Serviço de PetShop/Estética");

    private final String descricao;

    TipoServico(String descricao) {
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