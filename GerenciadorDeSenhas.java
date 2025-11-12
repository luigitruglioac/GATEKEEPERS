package br.GATEKEEPERS;
import br.GATEKEEPERS.exceptions.AplicativoInexistenteException;
import br.GATEKEEPERS.exceptions.UsuarioInexistenteException;
import br.GATEKEEPERS.sistema.Registrador;
import br.GATEKEEPERS.sistema.SistemaRegistrador;

import javax.swing.JOptionPane;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class GerenciadorDeSenhas {
    public static void main(String[] args) throws AplicativoInexistenteException, UsuarioInexistenteException, NoSuchAlgorithmException {
        boolean sair = false;
        String app;
        Registrador r1;
        List<Registrador> rL;
        SistemaRegistrador sistema = new SistemaRegistrador();
        sistema.importarDados();
        String loginOuRegistro;
        do {
            loginOuRegistro = JOptionPane.showInputDialog(null, "O que deseja fazer:\n1.Login\n2.Registrar", "Menu De Login - GateKeepers", JOptionPane.QUESTION_MESSAGE);
            if(loginOuRegistro == null) {
                JOptionPane.showMessageDialog(null, "Saindo...", "Login - GatekKeepers", JOptionPane.QUESTION_MESSAGE);
                System.exit(0);
            }
            while (true) {
                int numero;
                try {
                    numero = Integer.parseInt(loginOuRegistro);
                    switch (numero) {
                        case 1:
                            String usuario = JOptionPane.showInputDialog(null, "Digite seu usuário:", "Login - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                            String senha = JOptionPane.showInputDialog(null, "Digite sua senha:", "Login - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                            if(!sistema.procurarUsuarioLogin(usuario)) {
                                String outraEscolha = JOptionPane.showInputDialog(null, "Usuario não encontrado, deseja se registrar?", "Menu De Login - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                                if (outraEscolha.equalsIgnoreCase("Sim") || outraEscolha.equalsIgnoreCase("S")) {
                                    loginOuRegistro = "2";
                                } else {
                                    JOptionPane.showMessageDialog(null, "Saindo...", "Login - GatekKeepers", JOptionPane.QUESTION_MESSAGE);
                                    System.exit(0);
                                }
                            } else if (sistema.login(usuario, senha)) {
                                loginOuRegistro = "iniiiiiidaidaiwdaiwdiadiaiwdiaw";
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Senha Incorreta", "Login - GatekKeepers", JOptionPane.QUESTION_MESSAGE);
                            }
                            break;
                        case 2:
                            usuario = JOptionPane.showInputDialog(null, "Digite seu usuário:", "Registro - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                            senha = JOptionPane.showInputDialog(null, "Digite sua senha:", "Registro - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                            if (sistema.registrarLogin(usuario, senha)) {
                                loginOuRegistro = "iniiiiiidaidaiwdaiwdiadiaiwdiaw";
                            } else {
                                JOptionPane.showMessageDialog(null, "Este usuário já existe!", "Registro - GatekKeepers", JOptionPane.QUESTION_MESSAGE);
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opção inválida.", "Login - GateKeepers", JOptionPane.QUESTION_MESSAGE);
                            break;
                    }

                } catch (NumberFormatException e) {
                    break;
                }
            }

        } while (!loginOuRegistro.equalsIgnoreCase("iniiiiiidaidaiwdaiwdiadiaiwdiaw"));

        while (!sair) {
            String opcaos = JOptionPane.showInputDialog(
                    "Digite uma opção:\n1.Cadastrar\n"
                            + "2.Procurar dados por aplicativo\n"
                            + "3.Procurar dados por usuário\n"
                            + "4.Alterar senha\n"
                            + "5.Alterar usuario\n"
                            + "6.Alterar email\n"
                            + "7.Deletar um cadastro\n"
                            + "8.Formatar\n"
                            + "9.Salvar tudo\n"
                            + "10.Verificar segurança da senha\n"
                            + "11.Sair \n");

            if (opcaos == null) {
                JOptionPane.showMessageDialog(null, "Saindo...");
                sistema.salvarTudo();
                break;
            } else {
                int opcao;
                try {
                    opcao = Integer.parseInt(opcaos);
                    switch (opcao) {
                        case 1:
                            try {
                                app = JOptionPane.showInputDialog("Nome do aplicativo");
                                String usuario = JOptionPane.showInputDialog("Digite o usuário do aplicativo: " + app);
                                String email = JOptionPane.showInputDialog("Digite o email do aplicativo: " + app);
                                String senha = JOptionPane.showInputDialog("Digite a senha do aplicativo: " + app);
                                if (email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || email.endsWith("outlook.com"))
                                    sistema.registrar(app, usuario, email, senha);
                                else {
                                    JOptionPane.showMessageDialog(null, "Email inválido, (use: gmail, hotmail ou outlook)");
                                }
                            } catch (Exception _) {
                            }
                            break;

                        case 2:
                            try {
                                String nome = JOptionPane.showInputDialog("Nome do aplicativo:");
                                List<Registrador> rg = sistema.procuraAplicativo(nome);
                                for (Registrador r : rg) {
                                    JOptionPane.showMessageDialog(null, "==========================\n" +
                                            "Aplicativo: " + r.getAplicativo() + "\n" +
                                            "Usuário: " + r.getUsuario() + "\n" +
                                            "Email: " + r.getEmail() + "\n" +
                                            "Senha: " + r.getSenha());
                                }
                            } catch (AplicativoInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());

                            }
                            break;

                        case 3:
                            try {
                                String nome = JOptionPane.showInputDialog("Qual o usuário:");
                                rL = sistema.procuraUsuario(nome);
                                for (Registrador r : rL) {
                                    JOptionPane.showMessageDialog(null, "==========================" + "\n" +
                                            "Usuário: " + r.getUsuario() + "\n"
                                            + "Senha: " + r.getSenha() + "\n" + "Email: " + r.getEmail()
                                            + "\n" + "Aplicativo: " + r.getAplicativo());
                                }
                            } catch (UsuarioInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                            break;

                        case 4:
                            try {
                                String nome = JOptionPane.showInputDialog("Deseja alterar a senha de qual aplicativo? ");
                                String usuario = JOptionPane.showInputDialog("Deseja alterar a senha de qual usuário? ");
                                for (Registrador r : sistema.procuraAplicativo(nome)) {
                                    if (r.getAplicativo().equalsIgnoreCase(nome) && r.getUsuario().equals(usuario)) {
                                        String senha = JOptionPane.showInputDialog("Digite uma nova senha");
                                        sistema.replacePassword(r, senha);
                                    }

                                }
                            } catch (AplicativoInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                            break;

                        case 5:
                            try {
                                String usuario = JOptionPane.showInputDialog("Deseja alterar o usuário de qual aplicativo?");
                                String email = JOptionPane.showInputDialog("Deseja alterar o usuário de qual email? ");
                                for (Registrador r : sistema.procuraAplicativo(usuario)) {
                                    if (r.getAplicativo().equals(usuario) && r.getEmail().equals(email)) {
                                        String usuarioNovo = JOptionPane.showInputDialog("Digite um novo usuário");
                                        sistema.replaceUser(r, usuarioNovo);
                                    }
                                }
                            } catch (AplicativoInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                            break;

                        case 6:
                            try {
                                String nome = JOptionPane.showInputDialog("Deseja alterar o email de qual aplicativo?");
                                String usuario = JOptionPane.showInputDialog("Deseja alterar o email de qual usuário? ");
                                for (Registrador r : sistema.procuraAplicativo(nome)) {
                                    if (r.getAplicativo().equalsIgnoreCase(nome) && r.getUsuario().equals(usuario)) {
                                        String email = JOptionPane.showInputDialog("Digite um novo email");
                                        if (email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || email.endsWith("outlook.com"))
                                            sistema.replaceEmail(r, email);
                                        else {
                                            JOptionPane.showMessageDialog(null, "Email inválido, (use: gmail, hotmail ou outlook)");
                                        }
                                    }
                                }
                            } catch (AplicativoInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                            break;

                        case 7:
                            try {
                                String nome = JOptionPane.showInputDialog("Deseja excluir os dados de qual aplicativo?");
                                String user = JOptionPane.showInputDialog("Deseja excluir os dados de qual usuário?");
                                for (Registrador r : sistema.procuraAplicativo(nome)) {
                                    if (r.getAplicativo().equalsIgnoreCase(nome) && r.getUsuario().equals(user)) {
                                        sistema.deleteDado(r);
                                    }
                                }
                            } catch (AplicativoInexistenteException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                            break;

                        case 8:
                            try {
                                String confirmar = JOptionPane.showInputDialog("Realmente deseja formatar? (Sim|S/\n" +
                                        "Não|N)");
                                if (confirmar.equalsIgnoreCase("sim") || confirmar.equalsIgnoreCase("s")) {
                                    sistema.formatar();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Operação cancelada");
                                }
                            } catch (Exception _) {
                            }
                            break;

                        case 9:
                            try {
                                if (sistema.getRegistros().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Não há dados para salvar.");
                                } else {
                                    sistema.salvarTudo();
                                    JOptionPane.showMessageDialog(null, "Os dados foram salvos com sucesso!");
                                }
                            } catch (Exception _) {
                            }
                            break;

                        case 10:
                            try {

                                String apps = JOptionPane.showInputDialog("Insira o nome do aplicativo para pesquisar a senha: ");
                                String user = JOptionPane.showInputDialog("Insira o nome do usuário para pesquisar a senha: ");
                                for (Registrador r : sistema.getRegistros()) {
                                    if (r.getAplicativo().equalsIgnoreCase(apps) && r.getUsuario().equalsIgnoreCase(user)) {
                                        if (sistema.verificarSenha(r.getSenha())) {
                                            JOptionPane.showMessageDialog(null, "A senha é forte o suficiente.");
                                        } else {
                                            String senhaForte = sistema.gerarSenhaForte();
                                            String resposta = JOptionPane.showInputDialog("A senha não é forte o suficiente, gostaria de alterar para nossa sugestão? (Sim|S/Não|N) \nSenha sugerida: " + senhaForte);
                                            if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("S")) {
                                                sistema.replacePassword(r, senhaForte);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception _) {
                            }
                            break;

                        case 11:
                            sistema.salvarTudo();
                            sair = true;
                            break;

                        default:
                            break;
                    }
                } catch (NumberFormatException _) {
                }
            }
        }


    }
}