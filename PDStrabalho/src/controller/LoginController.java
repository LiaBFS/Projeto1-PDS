package controller;

import model.UsuariosDAO;
import model.Usuarios;
import model.Supermercado;
import view.PanelLogin;

import javax.swing.*;

public class LoginController {
    private PanelLogin panel;
    private UsuariosDAO usuariosDAO;
    private Supermercado supermercado;
    private Navegador navegador;
    
    ProdutoController controllerAdmin;
    CompraController controllerCliente;

    public LoginController(Navegador navegador, Supermercado supermercado) {
        this.panel = navegador.getPanelLogin();
        this.supermercado = supermercado;
        this.navegador = navegador;
        this.usuariosDAO = new UsuariosDAO();
        this.controllerAdmin = new ProdutoController(navegador);
        this.controllerCliente = new CompraController(navegador);
        
        this.panel.irCadastro(e -> {
            navegador.mostrarTela("cadastro");
        });
        
        this.panel.login(e -> {
            Usuarios usuario = usuariosDAO.buscarUsuario(panel.getUser(), panel.getCpf());

            if (usuario != null) {
                supermercado.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(navegador.getJanela(), "Login realizado.");

                if (usuario.isAdmin()) {
                    navegador.mostrarTela("admin");
                    controllerAdmin.carregarProdutos();
                } else {
                    navegador.mostrarTela("compras");
                    controllerCliente.carregarProdutos();
                }
                
                panel.getTxtUser().setText("");
                panel.getTxtCPF().setText("");
            } else {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Usuário inexistente");
            }
            
        });
    }

    public void iniciarLogin() {
        navegador.mostrarTela("login");
    }
}