package controller;

import model.Produtos;
import model.ProdutosDAO;
import model.Supermercado;
import view.PanelAdmin;

import javax.swing.*;
import java.util.List;

public class ProdutoController {
    private PanelAdmin panel;
    private Supermercado supermercado;
    private ProdutosDAO produtosDAO;
    private Navegador navegador;

    public ProdutoController(Navegador navegador, Supermercado supermercado) {
        this.panel = navegador.getPanelAdmin();
        this.supermercado = supermercado;
        this.navegador = navegador;
        this.produtosDAO = new ProdutosDAO();
        
        carregarProdutos();
        
        this.panel.sair(e -> {
            navegador.mostrarTela("login");
        });
        
        this.panel.adicionarProduto(e -> {
            String nome = panel.getTxtNome().getText().trim();
            String precoStr = panel.getTxtPreco().getText().trim();
            String qtdStr = panel.getTxtQuantidade().getText().trim();

            if (nome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Preencha todos os campos.");
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
                JOptionPane.showMessageDialog(navegador.getJanela(), "Produto cadastrado com sucesso.");
                carregarProdutos();

            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Preço e quantidade devem ser numéricos.");
            }
        });
        
        this.panel.editarProduto(e -> {
            int linha = panel.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Selecione um produto.");
                return;
            }

            String nomeOriginal = (String) panel.getModeloTabela().getValueAt(linha, 0);

            String novoNome = panel.getTxtNome().getText().trim();
            String precoStr = panel.getTxtPreco().getText().trim();
            String qtdStr = panel.getTxtQuantidade().getText().trim();

            if (novoNome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Preencha todos os campos.");
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
                JOptionPane.showMessageDialog(navegador.getJanela(), "Produto atualizado.");
                carregarProdutos();

            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Escolha um Preço e Quantidade válidos.");
            }
        });
        
        this.panel.removerProduto(e -> {
            int linha = panel.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Selecione um produto.");
                return;
            }

            String nome = (String) panel.getModeloTabela().getValueAt(linha, 0);

            int confirm = JOptionPane.showConfirmDialog(navegador.getJanela(),
                    "Remover o produto " + nome + "?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                produtosDAO.removerProduto(nome);
                JOptionPane.showMessageDialog(navegador.getJanela(), "Produto removido com sucesso.");
                carregarProdutos();
            }
        });
    }

    public void carregarProdutos() {
        List<Produtos> lista = produtosDAO.listarProdutos();
        panel.getModeloTabela().setRowCount(0);
        for (Produtos p : lista) {
            panel.getModeloTabela().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
            });
        }
    }
}