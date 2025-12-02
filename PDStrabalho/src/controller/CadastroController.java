package controller;

import exceptions.*;
import model.UsuariosDAO;
import model.Usuarios;
import model.Supermercado;
import view.PanelCadastro;
import javax.swing.*;

public class CadastroController {
    private PanelCadastro panel;
    private UsuariosDAO usuariosDAO;
    private Supermercado supermercado;
    private Navegador navegador;

    public CadastroController(Navegador navegador, Supermercado supermercado) {
        this.panel = navegador.getPanelCadastro();
        this.supermercado = supermercado;
        this.navegador = navegador;
        this.usuariosDAO = new UsuariosDAO();
        
        this.panel.cadastrar(e -> {
            try {
                String user = panel.getUser().trim();
                String cpf = panel.getCpf().trim();
                String senhaAdmin = panel.getTextoSenhaAdmin();
                boolean admin = panel.getChkAdmin().isSelected();
                
                if(user.isEmpty() || cpf.isEmpty()) {
                    throw new ValidacaoException("Preencha todos os campos.");
                }
                
                if(admin && !senhaAdmin.equals("admin")) {
                    throw new ValidacaoException("Senha de admin incorreta");
                }
                
                Usuarios usuario = new Usuarios(user, cpf, admin);
                usuariosDAO.inserirUsuario(usuario);
                
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Usuário cadastrado",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                panel.getTxtUser().setText("");
                panel.getTxtCPF().setText("");
                panel.getTxtSenhaAdmin().setText("");
                panel.getChkAdmin().setSelected(false);
                
                navegador.mostrarTela("login");
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Erro de Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (UsuarioDuplicadoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Usuário Duplicado",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao cadastrar usuário. Tente novamente.",
                    "Erro no Sistema",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro inesperado: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        this.panel.registrarAcaoChkAdmin(e -> {
            if(this.panel.getChkAdmin().isSelected()) {
                this.panel.getSenhaAdmin().setVisible(true);
                this.panel.getTxtSenhaAdmin().setVisible(true);
            } else {
                this.panel.getSenhaAdmin().setVisible(false);
                this.panel.getTxtSenhaAdmin().setVisible(false);
            }
        });
        
        this.panel.voltar(e -> {
            navegador.mostrarTela("login");
        });
    }
}