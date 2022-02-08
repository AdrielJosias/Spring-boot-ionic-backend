//Classe para ter acesso total do codigo atribuido a cada valor da enumerção
package com.adrieljosias.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	//Criar atributos para armazenar od valores 
	private int cod;
	private String descricao;
	
	//Criação de construtor private para evitar erro no PJ e PF
	private Perfil(int cod, String descricao) {
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
	
	//pega o numero interiro e converte para o perfil equivalnte
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		//Percorre todos os valores possiveis do tipocliente
		for (Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		//Caso nao encontrado o cod informado na busca, criar exceção
		throw new IllegalArgumentException("Id inválido" + cod);
	}
}
