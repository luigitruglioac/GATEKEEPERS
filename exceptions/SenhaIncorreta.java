package br.GATEKEEPERS.exceptions;

public class SenhaIncorreta extends RuntimeException {
    public SenhaIncorreta(String message) {
        super(message);
    }
}
