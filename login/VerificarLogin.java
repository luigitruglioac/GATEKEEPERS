package br.GATEKEEPERS.login;

import br.GATEKEEPERS.exceptions.UsuarioInexistenteException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class VerificarLogin {

    public static boolean verificarSenha(String senhaDigitada, String hashArmazenado) throws NoSuchAlgorithmException {

        String[] partes = hashArmazenado.split(":");
        if(partes.length == 0) throw new UsuarioInexistenteException("Usuário não encontrado!");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato inválido para o hash armazenado.");
        }
        String saltBase64 = partes[0];
        String hashSenhaBase64 = partes[1];


        byte[] salt = Base64.getDecoder().decode(saltBase64);


        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] novoHash = md.digest(senhaDigitada.getBytes(StandardCharsets.UTF_8));


        String novoHashBase64 = Base64.getEncoder().encodeToString(novoHash);

        return novoHashBase64.equals(hashSenhaBase64);
    }

}
