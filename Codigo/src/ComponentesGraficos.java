import javafx.scene.Group;

public abstract class ComponentesGraficos {

    protected int numLinhas = 0;
    protected int numColunas = 0;
    protected int posicaoX = 0;
    protected int posicaoY = 0;
    protected String cor = "white";

    public ComponentesGraficos linhas(int num) {
        numLinhas = num;
        return this;
    }

    public ComponentesGraficos colunas(int num) {
        numColunas = num;
        return this;
    }

    public ComponentesGraficos posicaoX(int pos) {
        posicaoX = pos;
        return this;
    }

    public ComponentesGraficos posixaoY(int pos) {
        posicaoY = pos;
        return this;
    }

    public ComponentesGraficos cor(String cor) {
        this.cor = cor;
        return this;
    }

    public abstract void desenhar(Group grupo);


    public abstract boolean pontoPertenceAoComponente(int x, int y, Posicao posicao);

    public abstract void remover(Group grupo);
}
