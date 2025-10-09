package controller;

import model.Produtos;
import model.ProdutosDAO;
import model.Supermercado;
import model.Usuarios;
import view.TelaCompras;
import view.TelaLogin;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController {
    private TelaCompras tela;
    private Supermercado supermercado;
    private ProdutosDAO produtosDAO;
    private List<Produtos> carrinho;

    public CompraController(Navegador navegador, Supermercado supermercado) {
        this.tela = navegador.getTelaCompras();
        this.supermercado = supermercado;
        this.produtosDAO = new ProdutosDAO();
        this.carrinho = new ArrayList<>();
        
        carregarProdutos();
        
        this.tela.sair(e -> {
        	
        	navegador.fecharTela(tela);
        	navegador.mostrarTela("login");
        	
        });
        
        this.tela.adicionarAoCarrinho(e -> {
        	
        	int linha = tela.getTabelaProdutos().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um produto.");
                return;
            }

            String nome = (String) tela.getModeloProdutos().getValueAt(linha, 0);
            double preco = (double) tela.getModeloProdutos().getValueAt(linha, 1);
            int estoque = (int) tela.getModeloProdutos().getValueAt(linha, 2);

            if (estoque <= 0) {
                JOptionPane.showMessageDialog(tela, "Produto não está disponível");
                return;
            }

            String qtdStr = JOptionPane.showInputDialog(tela, "Quantidade:", "1");
            if (qtdStr == null) return; 
            int qtd;
            try {
                qtd = Integer.parseInt(qtdStr);
            } catch (NumberFormatException i) {
                JOptionPane.showMessageDialog(tela, "Escolha uma quantidade válida.");
                return;
            }

            if (qtd > estoque) {
                JOptionPane.showMessageDialog(tela, "Estoque insuficiente.");
                return;
            }

            Produtos item = new Produtos();
            item.setNome(nome);
            item.setPreco(preco);
            item.setQuantidade(qtd);
            carrinho.add(item);

            tela.getModeloCarrinho().addRow(new Object[]{
                    nome, preco, qtd, preco * qtd
            });

            atualizarTotal();
        	
        });
        
        
        
        this.tela.removerDoCarrinho(e -> {
        	
        	int linha = tela.getTabelaCarrinho().getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(tela, "Selecione um item do carrinho.");
                return;
            }
            carrinho.remove(linha);
            tela.getModeloCarrinho().removeRow(linha);
            atualizarTotal();
        	
        });
        
        
        this.tela.finalizarCompra(e -> {
        	
        	if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Carrinho está vazio.");
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

            JOptionPane.showMessageDialog(tela, nota.toString(), "Nota Fiscal", JOptionPane.INFORMATION_MESSAGE);

            
            carrinho.clear();
            tela.getModeloCarrinho().setRowCount(0);
            atualizarTotal();
            carregarProdutos();
        	
        });
      
    }

    public void carregarProdutos() {
        List<Produtos> lista = produtosDAO.listarProdutos();
        tela.getModeloProdutos().setRowCount(0);
        for (Produtos p : lista) {
            tela.getModeloProdutos().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
            });
        }
    }



    private void atualizarTotal() {
        double total = 0;
        for (Produtos p : carrinho) {
            total += p.getPreco() * p.getQuantidade();
        }
        tela.getLblTotal().setText("Total: R$ " + String.format("%.2f", total));
    }

}