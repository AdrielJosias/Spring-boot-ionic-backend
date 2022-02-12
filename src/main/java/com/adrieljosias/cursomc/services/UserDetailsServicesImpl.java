//classe que permite a busca pelo nome do usuario

package com.adrieljosias.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adrieljosias.cursomc.domain.Cliente;
import com.adrieljosias.cursomc.repositories.ClienteRepository;
import com.adrieljosias.cursomc.security.UserSS;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

	//instaciar classe que busca o email do usuario
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //metodos que recebe o usuario e retorna USERDETAILS
		//chamar metodo para nbuscar email e msg de exceçoes se houver
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email); //mensage de erro caso email não encontrado
		}
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
