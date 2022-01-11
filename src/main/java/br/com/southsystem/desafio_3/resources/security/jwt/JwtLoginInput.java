package br.com.southsystem.desafio_3.resources.security.jwt;

import lombok.Data;

@Data
class JwtLoginInput {

	private String username;
    private String password;
    
}