
package exceptions;

public class UsuarioNaoEncontradoException extends SistemaException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado. Verifique os dados e tente novamente.");
    }
}

