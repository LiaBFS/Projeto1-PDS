package controller;

import model.Produtos;
import model.ProdutosDAO;
import model.Supermercado;
import view.TelaAdmin;
import view.TelaLogin;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class ProdutoController {
    private TelaAdmin tela;
    private Supermercado supermercado;
    private ProdutosDAO produtosDAO;


    public ProdutoController(Navegador navegador, Supermercado supermercado) {
        this.tela = navegador.getTelaAdmin();
        this.supermercado = supermercado;
        this.produtosDAO = new ProdutosDAO();
        
        carregarProdutos();
        
        
        this.tela.sair(e -> {
        	
        	navegador.fecharTela(tela);
        	navegador.mostrarTela("login");
        	
        	
        });
        
        
        this.tela.adicionarProduto(e -> {
        	
        	String nome = tela.getTxtNome().getText().trim();
            String precoStr = tela.getTxtPreco().getText().trim();
            String qtdStr = tela.getTxtQuantidade().getText().trim();

            if (nome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
                return;
            }

            try {
                double preco = Double.parseDouble(precoStr);
                int quantidade = Integer.parseInt(qtdStr);

                Produtos novo = new Produtos();
                novo.setNome(nome);
                novo.setPreco(preco);
                novo.setQuantidade(quantidade);

                produtosDAO.inserirProduto(novo);
                JOptionPane.showMessageDialog(tela, "Produto cadastrado com sucesso.");
                carregarProdutos();

            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(tela, "Preço e quantidade devem ser numéricos.");
            }
        	
        });
        
        
        this.tela.editarProduto(e -> {
        	
        	int linha = tela.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um produto.");
                return;
            }

            String nomeOriginal = (String) tela.getModeloTabela().getValueAt(linha, 0);

            String novoNome = tela.getTxtNome().getText().trim();
            String precoStr = tela.getTxtPreco().getText().trim();
            String qtdStr = tela.getTxtQuantidade().getText().trim();

            if (novoNome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
                return;
            }

            try {
                double preco = Double.parseDouble(precoStr);
                int quantidade = Integer.parseInt(qtdStr);

                Produtos editado = new Produtos();
                editado.setNome(novoNome);
                editado.setPreco(preco);
                editado.setQuantidade(quantidade);

                produtosDAO.atualizarProduto(nomeOriginal, editado);
                JOptionPane.showMessageDialog(tela, "Produto atualizado.");
                carregarProdutos();

            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(tela, "Escolha um Preço e Quantidade válidos.");
            }
        	
        });
        
        this.tela.removerProduto(e -> {
        	
            int linha = tela.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um produto.");
                return;
            }

            String nome = (String) tela.getModeloTabela().getValueAt(linha, 0);

            int confirm = JOptionPane.showConfirmDialog(tela,
                    "Remover o produto " + nome + "?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                produtosDAO.removerProduto(nome);
                JOptionPane.showMessageDialog(tela, "Produto removido com sucesso.");
                carregarProdutos();
            }
        	
        });
        
        
    }

    public void carregarProdutos() {
        List<Produtos> lista = produtosDAO.listarProdutos();
        tela.getModeloTabela().setRowCount(0);
        for (Produtos p : lista) {
            tela.getModeloTabela().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
            });
        }
    }

    
}