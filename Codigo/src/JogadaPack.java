import br.ufsc.inf.leobr.cliente.Jogada;

public class JogadaPack implements Jogada {

    protected Peca peca;
    protected Posicao posicaoNaGrade;
    protected String idUsuario;
    protected Posicao posicaoNaPeca;
    protected Boolean finalizarPartida;

    public JogadaPack() {

    }

    // TODO : talvez receber a posicao da peca tbm
    public void iniciar(Peca peca, Posicao posicaoNaGrade, Posicao posicaoNaPeca, String idUsuario, boolean finalizarPartida) {
        this.peca = peca;
        this.posicaoNaGrade = posicaoNaGrade;
        this.idUsuario = idUsuario;
        this.posicaoNaPeca = posicaoNaPeca;
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

    public Posicao getPosicaoNaPeca() {
        return posicaoNaPeca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public boolean finalizarPartida() {
        return finalizarPartida;
    }
}
