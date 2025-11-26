package view;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Janela extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    
    // Painéis
    private PanelLogin panelLogin;
    private PanelCadastro panelCadastro;
    private PanelAdmin panelAdmin;
    private PanelCompras panelCompras;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Janela frame = new Janela();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Janela() {
        setTitle("Sistema de Mercado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setLocationRelativeTo(null);
        
        // Inicializa o CardLayout
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        
        // Cria os painéis
        panelLogin = new PanelLogin();
        panelCadastro = new PanelCadastro();
        panelAdmin = new PanelAdmin();
        panelCompras = new PanelCompras();
        
        // Adiciona os painéis ao CardLayout
        contentPane.add(panelLogin, "login");
        contentPane.add(panelCadastro, "cadastro");
        contentPane.add(panelAdmin, "admin");
        contentPane.add(panelCompras, "compras");
        
        setContentPane(contentPane);
        
        // Mostra a tela de login inicialmente
        mostrarTela("login");
    }
    
    /**
     * Alterna entre as telas
     */
    public void mostrarTela(String nomeTela) {
        cardLayout.show(contentPane, nomeTela);
        
        // Ajusta o título da janela de acordo com a tela
        switch(nomeTela) {
            case "login":
                setTitle("Login - Sistema de Mercado");
                break;
            case "cadastro":
                setTitle("Cadastro de Usuário");
                break;
            case "admin":
                setTitle("Administração de Produtos");
                break;
            case "compras":
                setTitle("Compra de Produtos");
                break;
        }
    }
    
    // Getters para os painéis
    public PanelLogin getPanelLogin() {
        return panelLogin;
    }
    
    public PanelCadastro getPanelCadastro() {
        return panelCadastro;
    }
    
    public PanelAdmin getPanelAdmin() {
        return panelAdmin;
    }
    
    public PanelCompras getPanelCompras() {
        return panelCompras;
    }
}