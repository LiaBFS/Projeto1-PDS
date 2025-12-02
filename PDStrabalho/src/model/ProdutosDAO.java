package model;

import exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    // Inserir produto com tratamento de exceção
    public void inserirProduto(Produtos produto) throws BancoDadosException, ValidacaoException {
        // Validação
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new ValidacaoException("Nome do produto não pode ser vazio");
        }
        if (produto.getPreco() <= 0) {
            throw new ValidacaoException("Preço deve ser maior que zero");
        }
        if (produto.getQuantidade() < 0) {
            throw new ValidacaoException("Quantidade não pode ser negativa");
        }
        
        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());

            stmt.executeUpdate();
            System.out.println("Produto cadastrado!");
            
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao inserir produto", e);
        }
    }

    // Atualizar produto
    public void atualizarProduto(String nomeOriginal, Produtos produto) 
            throws BancoDadosException, ValidacaoException, ProdutoNaoEncontradoException {
        
        // Validação
        if (produto.getPreco() <= 0) {
            throw new ValidacaoException("Preço deve ser maior que zero");
        }
        if (produto.getQuantidade() < 0) {
            throw new ValidacaoException("Quantidade não pode ser negativa");
        }
        
        // Verifica se o produto existe
        if (buscarProduto(nomeOriginal) == null) {
            throw new ProdutoNaoEncontradoException(nomeOriginal);
        }
        
        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE nome = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, nomeOriginal);

            stmt.executeUpdate();
            System.out.println("Produto atualizado!");
            
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao atualizar produto", e);
        }
    }

    // Remover produto
    public void removerProduto(String nome) throws BancoDadosException, ProdutoNaoEncontradoException {
        // Verifica se existe
        if (buscarProduto(nome) == null) {
            throw new ProdutoNaoEncontradoException(nome);
        }
        
        String sql = "DELETE FROM produtos WHERE nome = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Produto removido!");
            
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao remover produto", e);
        }
    }

    // Buscar produto por nome
    public Produtos buscarProduto(String nome) throws BancoDadosException {
        String sql = "SELECT * FROM produtos WHERE nome = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produtos(
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade")
                );
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao buscar produto", e);
        }
        return null;
    }

    // Listar produtos
    public List<Produtos> listarProdutos() throws BancoDadosException {
        List<Produtos> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        
        try (Connection conn = BancoDeDados.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produtos p = new Produtos(
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade")
                );
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao listar produtos", e);
        }
        return produtos;
    }

    // Diminuir estoque
    public void diminuirEstoque(String nome, int quantidade) 
            throws BancoDadosException, EstoqueInsuficienteException, ProdutoNaoEncontradoException {
        
        // Verifica se há estoque suficiente
        Produtos produto = buscarProduto(nome);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException(nome);
        }
        if (produto.getQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException(nome, produto.getQuantidade());
        }
        
        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE nome = ?";
        try (Connection conexao = BancoDeDados.conectar();
             PreparedStatement pstm = conexao.prepareStatement(sql)) {

            pstm.setInt(1, quantidade);
            pstm.setString(2, nome);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao atualizar estoque", e);
        }
    }
}