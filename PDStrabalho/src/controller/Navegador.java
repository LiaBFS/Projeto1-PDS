package controller;

import view.Janela;
import view.PanelAdmin;
import view.PanelCadastro;
import view.PanelCompras;
import view.PanelLogin;

public class Navegador {
    
    private Janela janela;

    public Navegador() {
        this.janela = new Janela();
        this.janela.setVisible(true);
    }
    
    public void mostrarTela(String nomeTela) {
        janela.mostrarTela(nomeTela);
    }
    
    
    
    public PanelLogin getPanelLogin() {
        return janela.getPanelLogin();
    }
    
    public PanelCadastro getPanelCadastro() {
        return janela.getPanelCadastro();
    }
    
    public PanelAdmin getPanelAdmin() {
        return janela.getPanelAdmin();
    }
    
    public PanelCompras getPanelCompras() {
        return janela.getPanelCompras();
    }
    
    public Janela getJanela() {
        return janela;
    }
}
