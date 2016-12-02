import br.ufsc.inf.leobr.cliente.Jogada;

public class JogadaPack implements Jogada {

    protected Peca peca;
    protected Posicao posicaoPrimeiraPeca;
    protected String idUsuario;

    // TODO : pensar sobre inicialização no construtor ou lazy
    public JogadaPack() {

    }

    public void criar(Peca peca, Posicao posicaoPrimeiraPeca, String idUsuario) {
        this.peca = peca;
        this.posicaoPrimeiraPeca = posicaoPrimeiraPeca;
        this.idUsuario = idUsuario;
    }

    public void iniciar(Peca peca, int posicaoX, int posicaoY, String idUsuario) {

    }

    public Posicao getPosicaoPrimeiraPeca() {
        return posicaoPrimeiraPeca;
    }

    public Peca getPeca() {
        return peca;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
