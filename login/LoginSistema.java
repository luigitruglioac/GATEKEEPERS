package br.GATEKEEPERS.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class LoginSistema {


    public static String criarHashComSalt(String senha) throws NoSuchAlgorithmException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);


        MessageDigest md = MessageDigest.getInstance("SHA-256");


        md.update(salt);


        byte[] hashSenha = md.digest(senha.getBytes(StandardCharsets.UTF_8));


        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashSenhaBase64 = Base64.getEncoder().encodeToString(hashSenha);


        return saltBase64 + ":" + hashSenhaBase64;
    }


}
