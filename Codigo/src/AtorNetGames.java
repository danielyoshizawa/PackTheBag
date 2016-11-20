import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;

public class AtorNetGames implements OuvidorProxy {

    protected Proxy proxy;
    protected AtorJogador atorJogador;

    public AtorNetGames(AtorJogador atorJogador) {
        super();
        this.atorJogador = atorJogador;
        proxy = Proxy.getInstance();
    }

    public boolean conectar(String servidor, String nome) {
        return false;
    }

    public void iniciarPartida() {

    }

    public boolean desconectar() {
        return false;
    }

    public String informarNomeAdversario(String idUsuario) {
        return "";
    }

    public void enviarJogada(Jogada jogada) {

    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {

    }

    @Override
    public void finalizarPartidaComErro(String message) {

    }

    @Override
    public void receberMensagem(String msg) {

    }

    @Override
    public void receberJogada(br.ufsc.inf.leobr.cliente.Jogada jogada) {

    }

    @Override
    public void tratarConexaoPerdida() {

    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {

    }
}
