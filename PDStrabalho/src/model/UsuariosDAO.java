package model;

import exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

    // Inserir usuário
    public void inserirUsuario(Usuarios usuario) 
            throws BancoDadosException, ValidacaoException, UsuarioDuplicadoException {
        
        // Validações
        if (usuario.getUser() == null || usuario.getUser().trim().isEmpty()) {
            throw new ValidacaoException("Nome de usuário não pode ser vazio");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new ValidacaoException("CPF não pode ser vazio");
        }
        if (!validarCPF(usuario.getCpf())) {
            throw new ValidacaoException("CPF inválido");
        }
        
        // Verifica se já existe usuário com este CPF
        if (buscarUsuarioPorCpf(usuario.getCpf()) != null) {
            throw new UsuarioDuplicadoException(usuario.getCpf());
        }
        
        String sql = "INSERT INTO usuarios (user, cpf, admin) VALUES (?, ?, ?)";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUser());
            stmt.setString(2, usuario.getCpf());
            stmt.setBoolean(3, usuario.isAdmin());

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
            
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao inserir usuário", e);
        }
    }

    // Buscar usuário por CPF e nome (login)
    public Usuarios buscarUsuario(String user, String cpf) 
            throws BancoDadosException, UsuarioNaoEncontradoException {
        
        String sql = "SELECT * FROM usuarios WHERE user = ? AND cpf = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuarios(
                    rs.getString("user"),
                    rs.getString("cpf"),
                    rs.getBoolean("admin")
                );
            } else {
                throw new UsuarioNaoEncontradoException();
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao buscar usuário", e);
        }
    }
    
    // Método auxiliar para buscar por CPF (usado na validação)
    private Usuarios buscarUsuarioPorCpf(String cpf) throws BancoDadosException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuarios(
                    rs.getString("user"),
                    rs.getString("cpf"),
                    rs.getBoolean("admin")
                );
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao buscar usuário", e);
        }
        return null;
    }

    // Listar todos os usuários
    public List<Usuarios> listarUsuarios() throws BancoDadosException {
        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = BancoDeDados.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuarios u = new Usuarios(
                    rs.getString("user"),
                    rs.getString("cpf"),
                    rs.getBoolean("admin")
                );
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

    // Remover usuário
    public void removerUsuario(String cpf) throws BancoDadosException {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";
        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Usuário removido!");
            }
        } catch (SQLException e) {
            throw new BancoDadosException("Erro ao remover usuário", e);
        }
    }
    
    // Validação simples de CPF (apenas formato)
    private boolean validarCPF(String cpf) {
        // Remove pontos e traços
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // CPF deve ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        return true;
    }
}