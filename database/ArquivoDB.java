package br.GATEKEEPERS.database;

import br.GATEKEEPERS.criptografia.Criptografia;
import br.GATEKEEPERS.sistema.Registrador;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class ArquivoDB {

    private final String nomeArquivo;
    private List<Registrador> lista;

    public ArquivoDB(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.lista = new ArrayList<>();
    }

    public void salvarTudo(String user, List<Registrador> registros) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        Map<String, Object> dadosArquivo = carregarArquivoCompleto();

        if (!dadosArquivo.containsKey("registros") || !(dadosArquivo.get("registros") instanceof Map)) {
            dadosArquivo.put("registros", new LinkedHashMap<String, Object>());
        }
        Map<String, Object> todosOsUsuarios = (Map<String, Object>) dadosArquivo.get("registros");

        List<Map<String, String>> listaDeRegistrosMap = new ArrayList<>();
        for (Registrador r : registros) {
            Map<String, String> registroMap = new LinkedHashMap<>();
            registroMap.put("aplicativo", r.getAplicativo());
            registroMap.put("usuario", r.getUsuario());
            registroMap.put("email", r.getEmail());
            registroMap.put("senha", Criptografia.criptografar(r.getSenha()));
            listaDeRegistrosMap.add(registroMap);
        }

        todosOsUsuarios.put(user, listaDeRegistrosMap);

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            yaml.dump(dadosArquivo, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo YAML: " + e.getMessage(), e);
        }
    }

    public List<Registrador> carregar(String user) {
        Map<String, Object> dadosArquivo = carregarArquivoCompleto();
        List<Registrador> registrosDoUsuario = new ArrayList<>();

        if (dadosArquivo.containsKey("registros") && dadosArquivo.get("registros") instanceof Map) {
            Map<String, Object> todosOsUsuarios = (Map<String, Object>) dadosArquivo.get("registros");
            List<Map<String, String>> listaDeRegistrosMap = (List<Map<String, String>>) todosOsUsuarios.get(user);

            if (listaDeRegistrosMap != null) {
                for (Map<String, String> registroMap : listaDeRegistrosMap) {
                    String aplicativo = registroMap.get("aplicativo");
                    String usuario = registroMap.get("usuario");
                    String email = registroMap.get("email");
                    String senhaCriptografada = registroMap.get("senha");
                    String senha = (senhaCriptografada != null) ? Criptografia.descriptografar(senhaCriptografada) : "";
                    registrosDoUsuario.add(new Registrador(aplicativo, usuario, email, senha));
                }
            }
        }

        this.lista = registrosDoUsuario;
        return registrosDoUsuario;
    }

    private Map<String, Object> carregarArquivoCompleto() {
        Yaml yaml = new Yaml();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return new LinkedHashMap<>();
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Map<String, Object> data = yaml.load(reader);
            return (data != null) ? data : new LinkedHashMap<>();
        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }

    public void deletarDado(String user, Registrador r1) {
        List<Registrador> registrosAtuais = carregar(user);
        registrosAtuais.removeIf(reg -> reg.getAplicativo().equals(r1.getAplicativo()));
        salvarTudo(user, registrosAtuais);
    }

    public void deletarTudo(String user) {
        salvarTudo(user, new ArrayList<>());
    }

    public void formatar() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("registros", new LinkedHashMap<>());

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            yaml.dump(dados, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao formatar o arquivo YAML: " + e.getMessage(), e);
        }
    }
}