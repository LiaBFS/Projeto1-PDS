
package exceptions;

public class ProdutoNaoEncontradoException extends SistemaException {
    public ProdutoNaoEncontradoException(String nomeProduto) {
        super("Produto não encontrado");
    }
}

