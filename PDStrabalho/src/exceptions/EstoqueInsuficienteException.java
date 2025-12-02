
package exceptions;

public class EstoqueInsuficienteException extends SistemaException {
    public EstoqueInsuficienteException(String produto, int qtdDisponivel) {
        super("Estoque insuficiente");
    }
}

