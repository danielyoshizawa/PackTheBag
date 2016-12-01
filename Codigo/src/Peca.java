import br.ufsc.inf.leobr.cliente.Jogada;

public class Peca implements Jogada {

    protected String nome;
    protected Grade gradePeca;
    protected String cor;

    public Peca() {

    }

    public boolean ehPosicaoValida(float posicaoX, float posicaoY) {
        return false;
    }
}
