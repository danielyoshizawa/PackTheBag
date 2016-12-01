import javafx.scene.Group;

public abstract class ComponentesGraficos {

    protected int numLinhas = 0;
    protected int numColunas = 0;
    protected int posicaoX = 0;
    protected int posicaoY = 0;
    protected String cor = "white";
    protected Group grupo;

    protected ComponentesGraficos(Group grupo) {
        this.grupo = grupo;
    }

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
        return this;
    }

    public abstract void desenhar();


    // TODO : Rever o retorno talvez devolver posicao
    public abstract boolean pontoPertenceAoComponente(int x, int y);
}
