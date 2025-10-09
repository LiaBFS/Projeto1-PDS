package view;

import controller.LoginController;
import model.Supermercado;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionListener;

import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JFrame {
    private JTextField txtUser;
    private JFormattedTextField txtCpf;
    private JButton btnLogin;
    private JButton btnCadastrar;
   

    public TelaLogin() {
        super("Login - Sistema de Mercado");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

      
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "[189px][189px]", "[50px][50px][50px]"));

        panel.add(new JLabel("Usuário:"), "cell 0 0,grow");
        txtUser = new JTextField();
        panel.add(txtUser, "cell 1 0,growx,aligny center");

        panel.add(new JLabel("CPF:"), "cell 0 1,grow");
        
        
        MaskFormatter cpfMask = null;

		try {

		    cpfMask = new MaskFormatter("###########"); // 11 números

		    cpfMask.setPlaceholderCharacter('_'); // mostra "_" nos espaços vazios

		} catch (Exception e) {

		    e.printStackTrace();

		}
        
        txtCpf = new JFormattedTextField();
        panel.add(txtCpf, "cell 1 1,growx,aligny center");

        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar");

        panel.add(btnLogin, "cell 0 2,growx,aligny center");
        panel.add(btnCadastrar, "cell 1 2,growx,aligny center");

        getContentPane().add(panel, BorderLayout.CENTER);


   }
    
    public JTextField getTxtUser() {
    	return txtUser;
    }
    
    public JTextField getTxtCPF() {
    	return txtCpf;
    }
    
    public String getUser() {
		return this.txtUser.getText().trim();
	}


	public String getCpf() {
		return this.txtCpf.getText().trim();
	}


	public void login(ActionListener actionListener) {
		this.btnLogin.addActionListener(actionListener);
	}


	public void irCadastro(ActionListener actionListener) {
		this.btnCadastrar.addActionListener(actionListener);
	}

    
   
    
}