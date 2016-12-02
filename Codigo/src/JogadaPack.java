import br.ufsc.inf.leobr.cliente.Jogada;

public class JogadaPack implements Jogada {

    protected Peca peca;
    protected Posicao posicaoNaGrade;
    protected String idUsuario;

    // TODO : pensar sobre inicialização no construtor ou lazy
    public JogadaPack() {

    }

    public void criar(Peca peca, Posicao posicaoNaGrade, String idUsuario) {
        this.peca = peca;
        this.posicaoNaGrade = posicaoNaGrade;
        this.idUsuario = idUsuario;
    }

    // TODO : será necessario ?
    public void iniciar(Peca peca, int posicaoX, int posicaoY, String idUsuario) {

    }

    public Posicao getPosicaoNaGrade() {
        return posicaoNaGrade;
    }

    public Peca getPeca() {
        return peca;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
