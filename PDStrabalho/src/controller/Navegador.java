package controller;

import javax.swing.JFrame;

import view.TelaAdmin;
import view.TelaCadastro;
import view.TelaCompras;
import view.TelaLogin;

public class Navegador {
	
	
	private TelaAdmin telaAdmin;
	private TelaCadastro telaCadastro;
	private TelaCompras telaCompras;
	private TelaLogin telaLogin;
	
	
	

	public Navegador() {
		
		this.telaAdmin = new TelaAdmin();
		this.telaCadastro = new TelaCadastro();
		this.telaCompras = new TelaCompras();
		this.telaLogin = new TelaLogin();
		
		
		mostrarTela("login");
		
		
	}
	
	
	public void mostrarTela(String nomeTela) {
		
		this.telaAdmin.setVisible(false);
		this.telaCadastro.setVisible(false);
		this.telaCompras.setVisible(false);
		this.telaLogin.setVisible(false);
		
		if(nomeTela.equals("admin")) {
			this.telaAdmin.setVisible(true);
		}
		if(nomeTela.equals("login")) {
			this.telaLogin.setVisible(true);
		}
		if(nomeTela.equals("cadastro")) {
			this.telaCadastro.setVisible(true);
		}
		if(nomeTela.equals("compras")) {
			this.telaCompras.setVisible(true);
		}
		
	}
	
	
	public void fecharTela(JFrame tela) {
		tela.setVisible(false);
	}
	
	public TelaCadastro getTelaCadastro() {
		return telaCadastro;
	}
	

	public TelaLogin getTelaLogin() {
		return telaLogin;
	}
	
	public TelaCompras getTelaCompras() {
		return telaCompras;
	}

	public TelaAdmin getTelaAdmin() {
		return telaAdmin;
	}

}
