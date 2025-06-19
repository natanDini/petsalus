package br.com.petsalus.enums;

public enum ModeloComercial {
	CLINICA("Clínica"),
	PET_SHOP("Pet Shop"),
	CLINICA_E_PET_SHOP("Clínica e Pet Shop");

	private final String descricao;

	ModeloComercial(String descricao) {
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