/**
 * Created by daniel on 11/29/16.
 */
public class Posicao {

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

}
