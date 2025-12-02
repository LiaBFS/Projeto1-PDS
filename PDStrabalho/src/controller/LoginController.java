package controller;

import exceptions.*;
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
            try {
                String user = panel.getUser().trim();
                String cpf = panel.getCpf().trim();
                
                if (user.isEmpty() || cpf.isEmpty()) {
                    throw new ValidacaoException("Preencha todos os campos.");
                }
                
                Usuarios usuario = usuariosDAO.buscarUsuario(user, cpf);
                
                supermercado.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Login realizado",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );

                if (usuario.isAdmin()) {
                    navegador.mostrarTela("admin");
                    controllerAdmin.carregarProdutos();
                } else {
                    navegador.mostrarTela("compras");
                    controllerCliente.carregarProdutos();
                }
                
                panel.getTxtUser().setText("");
                panel.getTxtCPF().setText("");
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (UsuarioNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Login Falhou",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro no sistema",
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
    }

    public void iniciarLogin() {
        navegador.mostrarTela("login");
    }
}