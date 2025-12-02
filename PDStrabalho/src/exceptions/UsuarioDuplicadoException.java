
package exceptions;

public class UsuarioDuplicadoException extends SistemaException {
    public UsuarioDuplicadoException(String cpf) {
        super("cpf ja utilizado: " + cpf);
    }
}
