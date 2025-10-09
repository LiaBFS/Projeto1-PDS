package controller;

import model.UsuariosDAO;
import model.Usuarios;
import model.Supermercado;
import view.TelaLogin;
import view.TelaCadastro;
import view.TelaAdmin;
import view.TelaCompras;

import javax.swing.*;

public class LoginController {
    private TelaLogin tela;
    private UsuariosDAO usuariosDAO;
    private Supermercado supermercado;

    public LoginController(Navegador navegador, Supermercado supermercado) {
        this.tela = navegador.getTelaLogin();
        this.supermercado = supermercado;
        this.usuariosDAO = new UsuariosDAO();
        
        
        this.tela.irCadastro(e -> {
        
        	navegador.mostrarTela("cadastro");
        	navegador.fecharTela(tela);
        	
        });
        
        this.tela.login(e -> {
        	Usuarios usuario = usuariosDAO.buscarUsuario(tela.getUser(), tela.getCpf());

            if (usuario != null) {
                supermercado.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(tela, "Login realizado.");

                if (usuario.isAdmin()) {
                    navegador.mostrarTela("admin");
                    
                } else {
                    navegador.mostrarTela("compras");
                }
                
                tela.getTxtUser().setText("");
                tela.getTxtCPF().setText("");
                
                navegador.fecharTela(tela);
            } else {
                JOptionPane.showMessageDialog(tela, "Usuário inexistente");
            }
        	
        });
        
    }



   

	public void iniciarLogin() {
		this.tela.setVisible(true);
		
	}
}