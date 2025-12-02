
package exceptions;

public class UsuarioDuplicadoException extends SistemaException {
    public UsuarioDuplicadoException(String cpf) {
        super("Já existe um usuário cadastrado com o CPF: " + cpf);
    }
}
