package controller;

import model.UsuariosDAO;
import model.Usuarios;
import model.Supermercado;
import view.TelaCadastro;
import view.TelaLogin;

import javax.swing.*;

public class CadastroController {
    private TelaCadastro tela;
    private UsuariosDAO usuariosDAO;
    private Supermercado supermercado;

    public CadastroController(Navegador navegador, Supermercado supermercado) {
        this.tela = navegador.getTelaCadastro();
        this.supermercado = supermercado;
        this.usuariosDAO = new UsuariosDAO();
        
        
        this.tela.cadastrar(e -> {
            String user = tela.getUser().trim();
            String cpf = tela.getCpf().trim();
            String senhaAdmin = tela.getTextoSenhaAdmin();
            boolean admin = tela.getChkAdmin().isSelected();
            
            if(user.isEmpty() || cpf.isEmpty()) {
            	JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
                return;
            }
            if(tela.getChkAdmin().isSelected()) {
            	
            	if(!tela.getTextoSenhaAdmin().equals("admin")) {
            		JOptionPane.showMessageDialog(tela, "Senha Incorreta. Cadastro não realizado.");
            		return;
            		
            	}
            	
            }
            
            
            Usuarios usuario = new Usuarios(user, cpf, admin);
            usuariosDAO.inserirUsuario(usuario);
            
            
            JOptionPane.showMessageDialog(tela, "Usuário cadastrado");
            
            tela.getTxtUser().setText("");
            tela.getTxtCPF().setText("");
            tela.getTxtSenhaAdmin().setText("");
            tela.getChkAdmin().setSelected(false);
            
            
            navegador.mostrarTela("login");
            navegador.fecharTela(this.tela);

            
        });
        
        this.tela.registrarAcaoChkAdmin(e -> {
        	if(this.tela.getChkAdmin().isSelected()) {
        		this.tela.getSenhaAdmin().setVisible(true);
        		this.tela.getTxtSenhaAdmin().setVisible(true);
        	}
        	else {
        		this.tela.getSenhaAdmin().setVisible(false);
        		this.tela.getTxtSenhaAdmin().setVisible(false);
        	}
        	
        });
        
        this.tela.voltar(e -> {
        	
        	navegador.mostrarTela("login");
            navegador.fecharTela(this.tela);
        	
        });
        
        
        
    }

    
}
