package br.GATEKEEPERS.criptografia;

import java.util.*;

public class Criptografia {

    private static final Map<Character, String> mapaTelefone = new HashMap<>();
    private static final Map<String, Character> mapaInverso = new HashMap<>();
    private static final String DELIMITADOR = ".~.";
    private static final String PREFIX_MAIUSCULA = "=-==";
    static {
        for (char c = '0'; c <= '9'; c++) {
            mapaTelefone.put(c, String.valueOf(c));
        }

        mapaTelefone.put('a', "22"); mapaTelefone.put('b', "222"); mapaTelefone.put('c', "2222");
        mapaTelefone.put('d', "33"); mapaTelefone.put('e', "333"); mapaTelefone.put('f', "3333");
        mapaTelefone.put('g', "44"); mapaTelefone.put('h', "444"); mapaTelefone.put('i', "4444");
        mapaTelefone.put('j', "55"); mapaTelefone.put('k', "555"); mapaTelefone.put('l', "5555");
        mapaTelefone.put('m', "66"); mapaTelefone.put('n', "666"); mapaTelefone.put('o', "6666");
        mapaTelefone.put('p', "77"); mapaTelefone.put('q', "777"); mapaTelefone.put('r', "7777"); mapaTelefone.put('s', "77777");
        mapaTelefone.put('t', "88"); mapaTelefone.put('u', "888"); mapaTelefone.put('v', "8888");
        mapaTelefone.put('w', "99"); mapaTelefone.put('x', "999"); mapaTelefone.put('y', "9999"); mapaTelefone.put('z', "99999");

        for (Map.Entry<Character, String> e : mapaTelefone.entrySet()) {
            mapaInverso.put(e.getValue(), e.getKey());
        }
    }

    // Primeira camada: letra -> número
    private static String letraParaNumero(String senha) {
        StringBuilder sb = new StringBuilder();
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(PREFIX_MAIUSCULA);
                sb.append(mapaTelefone.getOrDefault(Character.toLowerCase(c), String.valueOf(c)));
            } else {
                sb.append(mapaTelefone.getOrDefault(c, String.valueOf(c)));
            }
            sb.append(DELIMITADOR);
        }
        return sb.toString();
    }

    private static String numeroParaLetra(String senha) {
        StringBuilder original = new StringBuilder();
        boolean maiuscula = false;
        String[] partes = senha.split(DELIMITADOR);

        for (String parte : partes) {
            if (parte.isEmpty()) continue;
            if (parte.startsWith(PREFIX_MAIUSCULA)) {
                maiuscula = true;
                parte = parte.substring(PREFIX_MAIUSCULA.length());
            }
            char letra = mapaInverso.getOrDefault(parte, parte.charAt(0));
            if (maiuscula) {
                letra = Character.toUpperCase(letra);
                maiuscula = false;
            }
            original.append(letra);
        }

        return original.toString();
    }

    private static String stringParaHex(String senha) {
        StringBuilder sb = new StringBuilder();
        for (char c : senha.toCharArray()) {
            sb.append(String.format("%02x", (int) c));
        }
        return sb.toString();
    }

    private static String hexParaString(String senha) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < senha.length(); i += 2) {
            String par = senha.substring(i, i + 2);
            int decimal = Integer.parseInt(par, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    public static String criptografar(String senha) {
        String camada1 = letraParaNumero(senha);
        return stringParaHex(camada1);
    }

    public static String descriptografar(String cript) {
        String camada1 = hexParaString(cript);
        return numeroParaLetra(camada1);
    }

}