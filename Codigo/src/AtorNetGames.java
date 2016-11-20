import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.*;

public class AtorNetGames implements OuvidorProxy {

    protected Proxy proxy;
    protected AtorJogador atorJogador;

    public AtorNetGames(AtorJogador atorJogador) {
        super();
        this.atorJogador = atorJogador;
        proxy = Proxy.getInstance();
    }

    public boolean conectar(String servidor, String nome) {

        try {
            proxy.conectar(servidor, nome);
        } catch (JahConectadoException e) {
            e.printStackTrace();
        } catch (NaoPossivelConectarException e) {
            e.printStackTrace();
        } catch (ArquivoMultiplayerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void iniciarPartida() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            proxy.desconectar();
        } catch (NaoConectadoException e) {
            e.printStackTrace();
        }
    }

    public String informarNomeAdversario(String idUsuario) {
        return "";
    }

    public void enviarJogada(Jogada jogada) {
        try {
            proxy.enviaJogada(jogada);
        } catch (NaoJogandoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
        atorJogador.iniciarPartida();
    }

    @Override
    public void finalizarPartidaComErro(String message) {

    }

    @Override
    public void receberMensagem(String msg) {

    }

    // TODO : alterar o nome de Jogada para remover esse conflito com o framework
    @Override
    public void receberJogada(br.ufsc.inf.leobr.cliente.Jogada jogada) {
        Jogada jogadaRecebida = (Jogada) jogada;
        atorJogador.receberJogada(jogadaRecebida);
    }

    @Override
    public void tratarConexaoPerdida() {

    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {

    }
}
