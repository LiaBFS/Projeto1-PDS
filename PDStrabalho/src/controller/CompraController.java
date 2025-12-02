package controller;

import exceptions.*;
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
    
    
    public CompraController(Navegador navegador) {
        this.panel = navegador.getPanelCompras();
        this.produtosDAO = new ProdutosDAO();
    }

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
            try {
                int linha = panel.getTabelaProdutos().getSelectedRow();
                if (linha == -1) {
                    throw new ValidacaoException("Selecione um produto.");
                }

                String nome = (String) panel.getModeloProdutos().getValueAt(linha, 0);
                double preco = (double) panel.getModeloProdutos().getValueAt(linha, 1);
                int estoque = (int) panel.getModeloProdutos().getValueAt(linha, 2);

                if (estoque <= 0) {
                    throw new EstoqueInsuficienteException(nome, 0);
                }

                String qtdStr = JOptionPane.showInputDialog(navegador.getJanela(), "Quantidade:", "1");
                if (qtdStr == null) return; 
                
                int qtd;
                try {
                    qtd = Integer.parseInt(qtdStr);
                } catch (NumberFormatException ex) {
                    throw new ValidacaoException("Digite uma quantidade válida.");
                }
                
                if (qtd <= 0) {
                    throw new ValidacaoException("Quantidade deve ser maior que zero.");
                }

                if (qtd > estoque) {
                    throw new EstoqueInsuficienteException(nome, estoque);
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
                
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Produto adicionado ao carrinho!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (EstoqueInsuficienteException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Estoque Insuficiente",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao adicionar produto ao carrinho.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        this.panel.removerDoCarrinho(e -> {
            try {
                int linha = panel.getTabelaCarrinho().getSelectedRow();
                if (linha == -1) {
                    throw new ValidacaoException("Selecione um item do carrinho para remover.");
                }
                
                carrinho.remove(linha);
                panel.getModeloCarrinho().removeRow(linha);
                atualizarTotal();
                
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Item removido do carrinho!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao remover item do carrinho.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        this.panel.finalizarCompra(e -> {
            try {
                if (carrinho.isEmpty()) {
                    throw new ValidacaoException("Carrinho está vazio.");
                }

                // Diminui estoque de cada produto
                for (Produtos item : carrinho) {
                    produtosDAO.diminuirEstoque(item.getNome(), item.getQuantidade());
                }

                // Gera nota fiscal
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

                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    nota.toString(), 
                    "Nota Fiscal", 
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Limpa carrinho e recarrega produtos
                carrinho.clear();
                panel.getModeloCarrinho().setRowCount(0);
                atualizarTotal();
                carregarProdutos();
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (EstoqueInsuficienteException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage() + "\nCompra cancelada.",
                    "Estoque Insuficiente",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (ProdutoNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage() + "\nCompra cancelada.",
                    "Produto Não Encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao finalizar compra. Tente novamente.",
                    "Erro no Sistema",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro inesperado ao finalizar compra.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
    }

    public void carregarProdutos() {
        try {
            List<Produtos> lista = produtosDAO.listarProdutos();
            panel.getModeloProdutos().setRowCount(0);
            for (Produtos p : lista) {
                panel.getModeloProdutos().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
                });
                System.out.println(p.getNome() + " " + p.getPreco() + " " + p.getQuantidade());
            }
        } catch (BancoDadosException ex) {
            JOptionPane.showMessageDialog(
                navegador.getJanela(), 
                "Erro ao carregar produtos.",
                "Erro no Sistema",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                navegador.getJanela(), 
                "Erro inesperado ao carregar produtos.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
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