import br.ufsc.inf.leobr.cliente.Jogada;

public class JogadaPack implements Jogada {

    protected Peca peca;
    protected String idUsuario;

    public JogadaPack() {

    }

    public void iniciar(Peca peca, String idUsuario) {
        this.peca = peca;
        this.idUsuario = idUsuario;
    }

    public Peca getPeca() {
        return peca;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
