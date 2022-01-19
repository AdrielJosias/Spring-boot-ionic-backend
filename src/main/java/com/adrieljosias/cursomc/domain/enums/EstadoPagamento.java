package com.adrieljosias.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	//Criar atributos para armazenar od valores da PF e PJ 
		private int cod;
		private String descricao;
		
		//Criação de construtor private para evitar erro no PJ e PF
		private EstadoPagamento(int cod, String descricao) {
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
		
		//Criar Operação que recebe um cod que retorna um obj EstadoPagamento ja instanciado
		public static EstadoPagamento toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			//Percorre todos os valores possiveis do EstadoPagamento
			for (EstadoPagamento x : EstadoPagamento.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			
			//Caso nao encontrado o cod informado na busca, criar exceção
			throw new IllegalArgumentException("Id inválido" + cod);
		}
	}

