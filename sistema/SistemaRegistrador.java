package br.GATEKEEPERS.sistema;
import br.GATEKEEPERS.database.ArquivoDB;
import br.GATEKEEPERS.exceptions.*;
import br.GATEKEEPERS.login.LoginSistema;
import br.GATEKEEPERS.login.VerificarLogin;
import gatekeepers.verificarsenhas.GerarSenhaSegura;
import gatekeepers.verificarsenhas.VerificadorSenha;
import br.GATEKEEPERS.database.LoginDB;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class SistemaRegistrador implements InterfaceServico {


    private List<Registrador> registros = new ArrayList<>();
    private final ArquivoDB arquivo = new ArquivoDB("DataBaseGK.yml");
    private final LoginDB logins = new LoginDB("logins.yml");
    public List<Registrador> getRegistros(){
        return registros;
    }
    private String user;

    @Override
    public void registrar(String aplicativo, String usuario, String email, String senha){
        Registrador rg = new Registrador(aplicativo, usuario, email, senha);
        registros.add(rg);
    }
    @Override
    public List<Registrador> procuraAplicativo (String aplicativo) throws AplicativoInexistenteException {
        List<Registrador> lista = new ArrayList<>();
        for (Registrador r : registros) {
            if (r.getAplicativo().equalsIgnoreCase(aplicativo)) {
                lista.add(r);
            }
        }
        if(lista.isEmpty()) throw new AplicativoInexistenteException("Aplicativo não registrado");
        return lista;
    }
    @Override
    public List<Registrador> procuraUsuario (String usuario) throws UsuarioInexistenteException {
        List<Registrador> procuraUsuario = new ArrayList<>();
        for(Registrador r : registros){
            if(r.getUsuario().equalsIgnoreCase(usuario)){
                procuraUsuario.add(r);
            }
        }
        if(procuraUsuario.isEmpty()) throw new UsuarioInexistenteException("Usuário não registrado");
        return procuraUsuario;


    }

    public void replacePassword(Registrador r1, String senha) {
        try {
            if(r1.getSenha().equals(senha)) {
                throw new DadoIgualException("Falha: senha igual");
            } else {
                r1.setSenha(senha);
                JOptionPane.showMessageDialog(null,"Senha alterada com sucesso!");
            }
        } catch (DadoIgualException _) {

        }
    }

    public void replaceUser(Registrador r1, String usuario) {
        try {
            if(r1.getUsuario().equals(usuario)) {
                throw new DadoIgualException("Falha: usuário igual");
            } else {
                r1.setUsuario(usuario);
                JOptionPane.showMessageDialog(null,"Usuario alterada com sucesso!");
            }
        } catch (DadoIgualException _) {

        }


    }


    public void replaceEmail(Registrador r1, String email) {
        try {
            if(r1.getEmail().equals(email)) {
                throw new DadoIgualException("Falha: email igual");
            } else {
                r1.setEmail(email);
                JOptionPane.showMessageDialog(null,"Email alterada com sucesso!");
            }
        } catch (DadoIgualException _) {

        }
    }

    public void deleteDado(Registrador r1){
        arquivo.deletarDado(user, r1);
        registros.remove(r1);

    }
    public void formatar() {
        arquivo.deletarTudo(user);
        registros.clear();

    }


    public void salvarTudo() {
        arquivo.salvarTudo(user, registros);
    }

    public void importarDados() {
        registros = arquivo.carregar(user);
    }

    public String gerarSenhaForte() {
        return GerarSenhaSegura.gerarSenha(10);
    }

    public boolean verificarSenha(String senha) {
        return VerificadorSenha.senhaSegura(senha);
    }

    public boolean login(String user, String senha) throws SenhaIncorreta, LoginNaoEncontrado, NoSuchAlgorithmException {
        try {
            boolean deuCerto = VerificarLogin.verificarSenha(senha, logins.carregarSenha(user.toLowerCase()));
            if (deuCerto) {
                this.user = user;
                return true;
            }
        } catch(Exception e) {
            if(Objects.equals(e.getMessage(), "Usuário não encontrado!")){
                JOptionPane.showMessageDialog(null, "Usuario Inexistente!", "Login - GateKeepers", 3);
            }
        }
        return false;
    }

    public boolean registrarLogin(String user, String senha) throws NoSuchAlgorithmException {
        String senhaParaSalvar = LoginSistema.criarHashComSalt(senha);
        try {
            if(logins.procurarUser(user)) return false;
            logins.salvarLogin(user, senhaParaSalvar);
            this.user = user;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean procurarUsuarioLogin(String user) {
        return logins.procurarUser(user);
    }
}