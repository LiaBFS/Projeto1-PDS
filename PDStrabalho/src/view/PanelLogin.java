package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class PanelLogin extends JPanel {
    
    private JTextField txtUser;
    private JFormattedTextField txtCpf;
    private JButton btnLogin;
    private JButton btnCadastrar;

    public PanelLogin() {
        setLayout(new MigLayout("", "[grow][189px][grow 50][189px][grow]", "[grow][50px][grow 50][50px][grow 50][50px][grow]"));

        add(new JLabel("Usuário:"), "cell 1 1,grow");
        txtUser = new JTextField();
        add(txtUser, "cell 3 1,growx,aligny center");

        add(new JLabel("CPF:"), "cell 1 3,grow");
        
        MaskFormatter cpfMask = null;
        try {
            cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        txtCpf = new JFormattedTextField();
        add(txtCpf, "cell 3 3,growx,aligny center");

        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar");

        add(btnLogin, "cell 1 5,growx,aligny center");
        add(btnCadastrar, "cell 3 5,growx,aligny center");
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