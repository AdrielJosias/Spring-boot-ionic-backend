package com.adrieljosias.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adrieljosias.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS() {
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) { //trocar collection por set perfil lista de perfis
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList()); //converter a lista de perfis para lista de grantedauthohrities
	}

	public Integer getId() { //criar metodo para ter aceso ao id
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //metodos perfis do usuarios
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true; //por padrao retornar verdadeiro
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;//padrao contra desbloqueada
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;//padrao nao expiradas
	}

	@Override
	public boolean isEnabled() {
		return true;//por padrao ativo
	}

}
