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
        panel.setLayout(new MigLayout("", "[grow][189px][grow 50][189px][grow]", "[grow][50px][grow 50][50px][grow 50][50px][grow]"));

        panel.add(new JLabel("Usuário:"), "cell 1 1,grow");
        txtUser = new JTextField();
        panel.add(txtUser, "cell 3 1,growx,aligny center");

        panel.add(new JLabel("CPF:"), "cell 1 3,grow");
        
        
        MaskFormatter cpfMask = null;

		try {

		    cpfMask = new MaskFormatter("###########"); // 11 números

		    cpfMask.setPlaceholderCharacter('_'); // mostra "_" nos espaços vazios

		} catch (Exception e) {

		    e.printStackTrace();

		}
        
        txtCpf = new JFormattedTextField();
        panel.add(txtCpf, "cell 3 3,growx,aligny center");

        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar");

        panel.add(btnLogin, "cell 1 5,growx,aligny center");
        panel.add(btnCadastrar, "cell 3 5,growx,aligny center");

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