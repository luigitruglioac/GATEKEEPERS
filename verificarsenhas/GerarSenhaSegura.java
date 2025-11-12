package gatekeepers.verificarsenhas;

import java.security.SecureRandom;

public class GerarSenhaSegura {
    private static final String MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String ESPECIAIS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String TODOS = MAIUSCULAS + MINUSCULAS + NUMEROS + ESPECIAIS;

    private static SecureRandom random = new SecureRandom();

    public static String gerarSenha(int tamanho) {
        if (tamanho < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        StringBuilder senha = new StringBuilder();

        senha.append(MAIUSCULAS.charAt(random.nextInt(MAIUSCULAS.length())));
        senha.append(MINUSCULAS.charAt(random.nextInt(MINUSCULAS.length())));
        senha.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        senha.append(ESPECIAIS.charAt(random.nextInt(ESPECIAIS.length())));
        for (int i= 4;i<tamanho;i++){
            senha.append(TODOS.charAt(random.nextInt(TODOS.length())));
        }
        return embaralhar(senha.toString());
    }
    private static String embaralhar(String senha) {
        char[] caracteres = senha.toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            int l= random.nextInt(caracteres.length);
            char temp= caracteres [i];
            caracteres[i]= caracteres [l];
            caracteres[l]= temp;
        }
        return new String(caracteres);
    }
}