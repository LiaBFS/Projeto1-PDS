package main;

import controller.CadastroController;
import controller.CompraController;
import controller.LoginController;
import controller.Navegador;
import controller.ProdutoController;
import model.Supermercado;
import view.TelaLogin;

public class Main {
    public static void main(String[] args) {
      
        Supermercado supermercado = new Supermercado();

       
        Navegador navegador = new Navegador();
        
        LoginController controller2 = new LoginController(navegador, supermercado);
        CadastroController controller1 = new CadastroController(navegador, supermercado);
        ProdutoController controller3 = new ProdutoController(navegador, supermercado);
        CompraController controller4 = new CompraController(navegador, supermercado);
        
        
    }
}