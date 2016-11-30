/*
*   Classe que vai representar um bloco tanto para a grade quando para as peças
*   assim temos um modelo minimo unico para representar estas entidades
*
* */

public class Bloco {

    protected String cor;
    protected Posicao posicao;
    protected int x;
    protected boolean ocupado;

    public Bloco(String cor, Posicao posicao) {
        this.cor = cor;
        this.posicao = posicao;
        ocupado = false;
    }

    public boolean Ocupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

}
