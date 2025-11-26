package controller;

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
            String user = panel.getUser().trim();
            String cpf = panel.getCpf().trim();
            String senhaAdmin = panel.getTextoSenhaAdmin();
            boolean admin = panel.getChkAdmin().isSelected();
            
            if(user.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Preencha todos os campos.");
                return;
            }
            
            if(panel.getChkAdmin().isSelected()) {
                if(!panel.getTextoSenhaAdmin().equals("admin")) {
                    JOptionPane.showMessageDialog(navegador.getJanela(), "Senha Incorreta. Cadastro não realizado.");
                    return;
                }
            }
            
            Usuarios usuario = new Usuarios(user, cpf, admin);
            usuariosDAO.inserirUsuario(usuario);
            
            JOptionPane.showMessageDialog(navegador.getJanela(), "Usuário cadastrado");
            
            panel.getTxtUser().setText("");
            panel.getTxtCPF().setText("");
            panel.getTxtSenhaAdmin().setText("");
            panel.getChkAdmin().setSelected(false);
            
            navegador.mostrarTela("login");
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