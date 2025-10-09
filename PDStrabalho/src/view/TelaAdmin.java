package view;

import controller.ProdutoController;
import model.Supermercado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class TelaAdmin extends JFrame {
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JButton btnSair;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;

    

    public TelaAdmin() {
        super("Administração de Produtos");

        

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new MigLayout("", "[191px][191px][191px]", "[23px][23px][23px][23px]"));

        panelTop.add(new JLabel("Nome:"), "cell 0 0,grow");
        
                JLabel label = new JLabel("Preço:");
                panelTop.add(label, "cell 1 0,grow");
        
                JLabel label_1 = new JLabel("Quantidade:");
                panelTop.add(label_1, "cell 2 0,grow");
        txtNome = new JTextField();
        panelTop.add(txtNome, "cell 0 1,grow");
        txtPreco = new JTextField();
        panelTop.add(txtPreco, "cell 1 1,grow");
        txtQuantidade = new JTextField();
        panelTop.add(txtQuantidade, "cell 2 1,grow");

        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnSair = new JButton("Sair");

        panelTop.add(btnAdicionar, "cell 0 2,grow");
        panelTop.add(btnEditar, "cell 1 2,grow");
        panelTop.add(btnRemover, "cell 2 2,grow");
        panelTop.add(btnSair, "cell 0 3,grow");

        getContentPane().add(panelTop, BorderLayout.NORTH);

       
        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Preço", "Quantidade"}, 0);
        tabelaProdutos = new JTable(modeloTabela);
        getContentPane().add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        
       





    }

  
    public JTextField getTxtNome() { 
    	return txtNome; 
    	
    }
    public JTextField getTxtPreco() { 
    	return txtPreco; 
    	
    }
    public JTextField getTxtQuantidade() { 
    	return txtQuantidade; 
    	
    }
    public DefaultTableModel getModeloTabela() { 
    	return modeloTabela; 
    	
    }
    public JTable getTabelaProdutos() { 
    	return tabelaProdutos; 
    	
    }
    
    public void adicionarProduto(ActionListener actionListener) {
    	this.btnAdicionar.addActionListener(actionListener);
    }
    
    public void editarProduto(ActionListener actionListener) {
    	this.btnEditar.addActionListener(actionListener);
    }
    
    public void removerProduto(ActionListener actionListener) {
    	this.btnRemover.addActionListener(actionListener);
    }
    
    public void sair(ActionListener actionListener) {
    	this.btnSair.addActionListener(actionListener);
    }
    
    
    
}