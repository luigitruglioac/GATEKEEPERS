package br.GATEKEEPERS.database;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginDB {

    private final String nomeArquivo;
    private Map<String, String> cacheLogins;

    public LoginDB(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.cacheLogins = carregarArquivoCompleto();
    }

    public void recarregarCache() {
        this.cacheLogins = carregarArquivoCompleto();
    }

    public List<String> carregarTodosOsUsuarios() {
        return new ArrayList<>(this.cacheLogins.keySet());
    }

    public void salvarLogin(String user, String senhaHash) {
        this.cacheLogins.put(user, senhaHash);
        salvarArquivoCompleto(this.cacheLogins);
    }

    public String carregarSenha(String user) {
        return this.cacheLogins.get(user);
    }

    public boolean verificarUsuario(String user) {
        return this.cacheLogins.containsKey(user);
    }

    public void deletarLogin(String user) {
        this.cacheLogins.remove(user);
        salvarArquivoCompleto(this.cacheLogins);
    }

    private Map<String, String> carregarArquivoCompleto() {
        Yaml yaml = new Yaml();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return new LinkedHashMap<>();
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Map<String, String> data = yaml.load(reader);
            return (data != null) ? data : new LinkedHashMap<>();
        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }

    private void salvarArquivoCompleto(Map<String, String> dados) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            yaml.dump(dados, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo de logins: " + e.getMessage(), e);
        }
    }

    public boolean procurarUser(String user) {
        return cacheLogins.containsKey(user);
    }
}