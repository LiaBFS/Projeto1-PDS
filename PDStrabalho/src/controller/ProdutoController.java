package controller;

import exceptions.*;
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
    
    
    public ProdutoController(Navegador navegador) {
        this.produtosDAO = new ProdutosDAO();
        this.panel = navegador.getPanelAdmin();
    }

    public ProdutoController(Navegador navegador, Supermercado supermercado) {
        this.panel = navegador.getPanelAdmin();
        this.supermercado = supermercado;
        this.navegador = navegador;
        this.produtosDAO = new ProdutosDAO();
        
        this.panel.sair(e -> {
            navegador.mostrarTela("login");
        });
        
        this.panel.adicionarProduto(e -> {
            try {
                String nome = panel.getTxtNome().getText().trim();
                String precoStr = panel.getTxtPreco().getText().trim();
                String qtdStr = panel.getTxtQuantidade().getText().trim();

                if (nome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                    throw new ValidacaoException("Preencha todos os campos.");
                }

                double preco = Double.parseDouble(precoStr);
                int quantidade = Integer.parseInt(qtdStr);

                Produtos novo = new Produtos();
                novo.setNome(nome);
                novo.setPreco(preco);
                novo.setQuantidade(quantidade);

                produtosDAO.inserirProduto(novo);
                
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Produto cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                carregarProdutos();
                
                panel.getTxtNome().setText("");
                panel.getTxtPreco().setText("");
                panel.getTxtQuantidade().setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Preço e quantidade devem ser números válidos.",
                    "Erro de Formato",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao cadastrar produto. Tente novamente.",
                    "Erro no Sistema",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro inesperado: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        this.panel.editarProduto(e -> {
            try {
                int linha = panel.getTabelaProdutos().getSelectedRow();
                if (linha == -1) {
                    throw new ValidacaoException("Selecione um produto para editar.");
                }

                String nomeOriginal = (String) panel.getModeloTabela().getValueAt(linha, 0);

                String novoNome = panel.getTxtNome().getText().trim();
                String precoStr = panel.getTxtPreco().getText().trim();
                String qtdStr = panel.getTxtQuantidade().getText().trim();

                if (novoNome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                    throw new ValidacaoException("Preencha todos os campos.");
                }

                double preco = Double.parseDouble(precoStr);
                int quantidade = Integer.parseInt(qtdStr);

                Produtos editado = new Produtos();
                editado.setNome(novoNome);
                editado.setPreco(preco);
                editado.setQuantidade(quantidade);

                produtosDAO.atualizarProduto(nomeOriginal, editado);
                
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Produto atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                carregarProdutos();
                
                panel.getTxtNome().setText("");
                panel.getTxtPreco().setText("");
                panel.getTxtQuantidade().setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Preço e quantidade devem ser números válidos.",
                    "Erro de Formato",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (ProdutoNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Produto Não Encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao atualizar produto. Tente novamente.",
                    "Erro no Sistema",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro inesperado: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        this.panel.removerProduto(e -> {
            try {
                int linha = panel.getTabelaProdutos().getSelectedRow();
                if (linha == -1) {
                    throw new ValidacaoException("Selecione um produto para remover.");
                }

                String nome = (String) panel.getModeloTabela().getValueAt(linha, 0);

                int confirm = JOptionPane.showConfirmDialog(
                    navegador.getJanela(),
                    "Tem certeza que deseja remover o produto " + nome + "?", 
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    produtosDAO.removerProduto(nome);
                    
                    JOptionPane.showMessageDialog(
                        navegador.getJanela(), 
                        "Produto removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    carregarProdutos();
                }
                
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (ProdutoNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    ex.getMessage(),
                    "Produto Não Encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
                
            } catch (BancoDadosException ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro ao remover produto. Tente novamente.",
                    "Erro no Sistema",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    navegador.getJanela(), 
                    "Erro inesperado: " + ex.getMessage(),
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
            panel.getModeloTabela().setRowCount(0);
            for (Produtos p : lista) {
                panel.getModeloTabela().addRow(new Object[]{
                    p.getNome(), p.getPreco(), p.getQuantidade()
                });
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
}