package br.com.petsalus.enums;

public enum Sexo {
	MASCULINO("Masculino"),
	FEMININO("Feminino"),
	NAO_IDENTIFICADO("Não Identificado");

	private final String descricao;

	Sexo(String descricao) {
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