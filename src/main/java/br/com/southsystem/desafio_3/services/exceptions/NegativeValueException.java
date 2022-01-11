package br.com.southsystem.desafio_3.services.exceptions;

public class NegativeValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NegativeValueException(String msg) {
		super(msg);
	}
}
