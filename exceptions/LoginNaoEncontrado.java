package br.GATEKEEPERS.exceptions;

public class LoginNaoEncontrado extends RuntimeException {
    public LoginNaoEncontrado(String message) {
        super(message);
    }
}
