package controller;

import model.Produtos;
import model.ProdutosDAO;
import model.Supermercado;
import view.PanelCompras;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController {
    private PanelCompras panel;
    private Supermercado supermercado;
    private ProdutosDAO produtosDAO;
    private List<Produtos> carrinho;
    private Navegador navegador;

    public CompraController(Navegador navegador, Supermercado supermercado) {
        this.panel = navegador.getPanelCompras();
        this.supermercado = supermercado;
        this.navegador = navegador;
        this.produtosDAO = new ProdutosDAO();
        this.carrinho = new ArrayList<>();
        
        carregarProdutos();
        
        this.panel.sair(e -> {
            navegador.mostrarTela("login");
        });
        
        this.panel.adicionarAoCarrinho(e -> {
            int linha = panel.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Selecione um produto.");
                return;
            }

            String nome = (String) panel.getModeloProdutos().getValueAt(linha, 0);
            double preco = (double) panel.getModeloProdutos().getValueAt(linha, 1);
            int estoque = (int) panel.getModeloProdutos().getValueAt(linha, 2);

            if (estoque <= 0) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Produto não está disponível");
                return;
            }

            String qtdStr = JOptionPane.showInputDialog(navegador.getJanela(), "Quantidade:", "1");
            if (qtdStr == null) return; 
            int qtd;
            try {
                qtd = Integer.parseInt(qtdStr);
            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Escolha uma quantidade válida.");
                return;
            }

            if (qtd > estoque) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Estoque insuficiente.");
                return;
            }

            Produtos item = new Produtos();
            item.setNome(nome);
            item.setPreco(preco);
            item.setQuantidade(qtd);
            carrinho.add(item);

            panel.getModeloCarrinho().addRow(new Object[]{
                    nome, preco, qtd, preco * qtd
            });

            atualizarTotal();
        });
        
        this.panel.removerDoCarrinho(e -> {
            int linha = panel.getTabelaCarrinho().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Selecione um item do carrinho.");
                return;
            }
            carrinho.remove(linha);
            panel.getModeloCarrinho().removeRow(linha);
            atualizarTotal();
        });
        
        this.panel.finalizarCompra(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(navegador.getJanela(), "Carrinho está vazio.");
                return;
            }

            for (Produtos item : carrinho) {
                produtosDAO.diminuirEstoque(item.getNome(), item.getQuantidade());
            }

            StringBuilder nota = new StringBuilder();
            nota.append("=== NOTA FISCAL ===\n");
            nota.append("Cliente: ").append(supermercado.getUsuarioLogado().getUser()).append("\n");
            nota.append("CPF: ").append(supermercado.getUsuarioLogado().getCpf()).append("\n\n");
            double total = 0;
            for (Produtos p : carrinho) {
                double subtotal = p.getPreco() * p.getQuantidade();
                nota.append(p.getNome()).append(" - Qtd: ").append(p.getQuantidade())
                    .append(" - R$ ").append(String.format("%.2f", subtotal)).append("\n");
                total += subtotal;
            }
            nota.append("\nTOTAL: R$ ").append(String.format("%.2f", total));

            JOptionPane.showMessageDialog(navegador.getJanela(), nota.toString(), "Nota Fiscal", JOptionPane.INFORMATION_MESSAGE);

            carrinho.clear();
            panel.getModeloCarrinho().setRowCount(0);
            atualizarTotal();
            carregarProdutos();
        });
    }

    public void carregarProdutos() {
        List<Produtos> lista = produtosDAO.listarProdutos();
        panel.getModeloProdutos().setRowCount(0);
        for (Produtos p : lista) {
            panel.getModeloProdutos().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
            });
        }
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produtos p : carrinho) {
            total += p.getPreco() * p.getQuantidade();
        }
        panel.getLblTotal().setText("Total: R$ " + String.format("%.2f", total));
    }
}