package view;

import controller.CompraController;
import model.Supermercado;
import model.Usuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class TelaCompras extends JFrame {
    private JTable tabelaProdutos;
    private JTable tabelaCarrinho;
    private DefaultTableModel modeloProdutos;
    private DefaultTableModel modeloCarrinho;
    private JButton btnAdicionar;
    private JButton btnRemover;
    private JButton btnFinalizar;
    private JButton btnSair;
    private JLabel lblTotal;

    

    public TelaCompras() {
        super("Compra de Produtos");

        

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     
        
        modeloProdutos = new DefaultTableModel(new Object[]{"Nome", "Preço", "Quantidade"}, 0);
        tabelaProdutos = new JTable(modeloProdutos);
        
        

        JPanel panelProdutos = new JPanel(new BorderLayout());
        panelProdutos.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));
        panelProdutos.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

       
        modeloCarrinho = new DefaultTableModel(new Object[]{"Nome", "Preço", "Qtd", "Subtotal"}, 0);
        tabelaCarrinho = new JTable(modeloCarrinho);

        JPanel panelCarrinho = new JPanel(new BorderLayout());
        panelCarrinho.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras"));
        panelCarrinho.add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        
        JPanel panelBotoes = new JPanel(new GridLayout(2, 2, 5, 5));
        btnAdicionar = new JButton("Adicionar ao Carrinho");
        btnRemover = new JButton("Remover do Carrinho");
        btnFinalizar = new JButton("Finalizar Compra");
        btnSair = new JButton("Sair");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnFinalizar);
        panelBotoes.add(btnSair);

  
        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.add(lblTotal);

      
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelProdutos, panelCarrinho);
        split.setDividerLocation(250);

        add(split, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        add(panelTotal, BorderLayout.NORTH);

     

    }

   
    public DefaultTableModel getModeloProdutos() { 
    	return modeloProdutos; 
    	
    }
    public DefaultTableModel getModeloCarrinho() { 
    	return modeloCarrinho; 
    	
    }
    public JTable getTabelaProdutos() { 
    	return tabelaProdutos; 
    	
    }
    public JTable getTabelaCarrinho() { 
    	return tabelaCarrinho; 
    	
    }
    public JLabel getLblTotal() { 
    	return lblTotal; 
    	
    }
    
    
    public void adicionarAoCarrinho(ActionListener actionListener) {
    	this.btnAdicionar.addActionListener(actionListener);
    }
    
    public void removerDoCarrinho(ActionListener actionListener) {
    	this.btnRemover.addActionListener(actionListener);
    }
    
    public void finalizarCompra(ActionListener actionListener) {
    	this.btnFinalizar.addActionListener(actionListener);
    }
    
    public void sair(ActionListener actionListener) {
    	this.btnSair.addActionListener(actionListener);
    }
}