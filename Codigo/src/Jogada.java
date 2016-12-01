
public class Jogada implements br.ufsc.inf.leobr.cliente.Jogada {

    protected Peca peca;
    protected Grade posicaoDesejada;
    protected long tempoDisponivel;
    protected String idUsuario;

    // TODO : pensar sobre inicialização no construtor ou lazy
    public Jogada() {

    }

    public void criar(Peca peca, Grade posicaoDesejada, String idUsuario) {
        this.peca = peca;
        this.posicaoDesejada = posicaoDesejada;
        this.idUsuario = idUsuario;
    }

    public void iniciar(Peca peca, int posicaoX, int posicaoY, String idUsuario) {

    }

    public Grade getPosicaoDesejada() {
        return posicaoDesejada;
    }

    public Peca getPeca() {
        return peca;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
