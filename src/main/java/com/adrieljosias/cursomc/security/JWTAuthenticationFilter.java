//classe de filtro de autenticação para login de usuarios

package com.adrieljosias.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.adrieljosias.cursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager; 
	
	private JWTUtil jwtUtil;
	
	//instanciar pelo construtor
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());// instanciar metodo de erro 401
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override//metodo que tenta atenticar
	 public Authentication attemptAuthentication(HttpServletRequest req,
			 									 HttpServletResponse res) throws AuthenticationException {
		try {
			CredenciaisDTO creds = new ObjectMapper() //tenta pegar os dados que vieram na requisição de login 
	                .readValue(req.getInputStream(), CredenciaisDTO.class);

			//instanciar token do security passando usuario, senha e lista(vazia)
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

	        //metodo que verifica se usuario e senha são validos
	        Authentication auth = authenticationManager.authenticate(authToken);
	        return auth;
		}
		catch (IOException e) { //se deu erro no try
			throw new RuntimeException(e);
		}
	}
	
	@Override //se der certo a autenticação oque fazer
	protected void successfulAuthentication(HttpServletRequest req, 
											HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		
		String username = ((UserSS) auth.getPrincipal()).getUsername();//retorna usuario do springsecurity. pega o email
        String token = jwtUtil.generateToken(username); // apartir do email, chama o jwt 
        res.addHeader("Authorization", "Bearer " + token);// retorna o token jwt no cabeçalho da resposta
	}

	//metodo para erro 401
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
	
	//add filtro na classe SecurityConfig http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
}
