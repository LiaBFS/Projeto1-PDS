
package exceptions;

public class UsuarioNaoEncontradoException extends SistemaException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado");
    }
}

