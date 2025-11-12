package gatekeepers.verificarsenhas;

public class VerificadorSenha {
    public static boolean senhaSegura(String senha) {
        try {
            if (senha == null) return false;
            if (senha.length() < 8) return false;

            boolean ExisteMaiuscula = false;
            boolean ExisteMinuscula = false;
            boolean ExisteNumero = false;
            boolean NumerosRepetidos = false;
            boolean CaractereEspecial = false;

            for (char c : senha.toCharArray()) {
                if (Character.isUpperCase(c)) ExisteMaiuscula = true;
                if (Character.isLowerCase(c)) ExisteMinuscula = true;
                if (Character.isDigit(c)) ExisteNumero = true;
                if (verificaProcedencia(senha)) NumerosRepetidos = true;
                if (!Character.isLetterOrDigit(c))CaractereEspecial= true;
            }

            if (!(ExisteMaiuscula && ExisteMinuscula && ExisteNumero && NumerosRepetidos && CaractereEspecial)) {
                return false;
            }

        } catch (Exception _) {
        }
        return true;
    }


    public static boolean verificaProcedencia(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);
            char c3 = s.charAt(i + 2);

            if (Character.isDigit(c1) && Character.isDigit(c2) && Character.isDigit(c3)) {
                int n1 = c1 - '0';
                int n2 = c2 - '0';
                int n3 = c3 - '0';

                if ((n2 == n1 + 1 && n3 == n2 + 1) || (n2 == n1 - 1 && n3 == n2 - 1)) {
                    return false;
                }
            }
        }

        if (s.length() >= 2) {
            for (int i = 0; i < s.length() - 1; i++) {
                char c1 = s.charAt(i);
                char c2 = s.charAt(i + 1);
                if (Character.isDigit(c1) && Character.isDigit(c2) && c1 == c2) return false;
            }
        }
        return true;
    }
}