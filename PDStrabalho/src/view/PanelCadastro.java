package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class PanelCadastro extends JPanel {
    
    private JTextField txtUser;
    private JFormattedTextField txtCpf;
    private JCheckBox chkAdmin;
    private JButton btnCadastrar;
    private JButton btnVoltar;
    private JPasswordField txtSenhaAdmin;
    private JLabel senhaAdmin;

    public PanelCadastro() {
        setLayout(new MigLayout("", "[grow][][grow][grow][][grow]", "[grow][49px][grow 50][49px][grow 50][49px][grow 50][49px][grow]"));

        add(new JLabel("Usuário:"), "cell 1 1,grow");
        txtUser = new JTextField();
        add(txtUser, "cell 3 1 2 1,growx,aligny center");

        add(new JLabel("CPF:"), "cell 1 3,grow");
        
        MaskFormatter cpfMask = null;
        try {
            cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        txtCpf = new JFormattedTextField(cpfMask);
        add(txtCpf, "cell 3 3 2 1,growx,aligny center");

        add(new JLabel("Administrador?"), "cell 1 5,grow");
        chkAdmin = new JCheckBox("Sim");
        add(chkAdmin, "cell 3 5,grow");

        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");
        
        this.senhaAdmin = new JLabel("Senha:");
        add(senhaAdmin, "cell 4 5,grow");
        senhaAdmin.setVisible(false);
        
        txtSenhaAdmin = new JPasswordField();
        add(txtSenhaAdmin, "cell 5 5,growx");
        txtSenhaAdmin.setColumns(10);
        txtSenhaAdmin.setVisible(false);

        add(btnCadastrar, "cell 1 7,growx,aligny center");
        add(btnVoltar, "cell 3 7,growx,aligny center");
    }

    public JLabel getSenhaAdmin() {
        return senhaAdmin;
    }
    
    public JPasswordField getTxtSenhaAdmin() {
        return txtSenhaAdmin;
    }

    public String getTextoSenhaAdmin() {
        System.out.println(new String(this.txtSenhaAdmin.getPassword()));
        return new String(this.txtSenhaAdmin.getPassword());
    }
    
    public JTextField getTxtUser() {
        return txtUser;
    }
    
    public JTextField getTxtCPF() {
        return txtCpf;
    }

    public String getUser() { 
        return this.txtUser.getText(); 
    }
    
    public String getCpf() {
        return this.txtCpf.getText(); 
    }
    
    public JCheckBox getChkAdmin() { 
        return chkAdmin; 
    }
    
    public void cadastrar(ActionListener actionListener) {
        this.btnCadastrar.addActionListener(actionListener);
    }
    
    public void voltar(ActionListener actionListener) {
        this.btnVoltar.addActionListener(actionListener);
    }
    
    public void registrarAcaoChkAdmin(ActionListener actionListener) {
        this.chkAdmin.addActionListener(actionListener);
    }
}