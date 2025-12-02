

package exceptions;

public class BancoDadosException extends SistemaException {
    public BancoDadosException(String mensagem, Throwable causa) {
        super("Erro no banco");
    }
}

