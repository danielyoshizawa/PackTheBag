import br.ufsc.inf.leobr.cliente.Jogada;

public class Posicao implements Jogada{

    protected int x;
    protected int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Usado para identificar a posição mapeada em um HashMap
    public String identificador() {
        return "[" + x + "," + y + "]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}