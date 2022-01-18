//Classe para ter acesso total do codigo atribuido a cada valor da enumerção
package com.adrieljosias.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Físoca"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	//Criar atributos para armazenar od valores da PF e PJ 
	private int cod;
	private String descricao;
	
	//Criação de construtor private para evitar erro no PJ e PF
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	//Necessario somente os gets 
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//Criar Operação que recebe um cod que retorna um obj TipoCliente ja instanciado
	public static TipoCliente toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		//Percorre todos os valores possiveis do tipocliente
		for (TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		//Caso nao encontrado o cod informado na busca, criar exceção
		throw new IllegalArgumentException("Id inválido" + cod);
	}
}
