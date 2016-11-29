import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.*;

public class AtorNetGames implements OuvidorProxy {

    protected Proxy proxy;
    protected AtorJogador atorJogador;
    protected boolean ehMinhaVez = false;

    public AtorNetGames(AtorJogador atorJogador) {
        super();
        this.atorJogador = atorJogador;
        proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }

    public boolean conectar(String servidor, String nome) {

        try {
            proxy.conectar(servidor, nome);
            return true;
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
            System.out.println("Iniciar Partida");
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
        System.out.println("Iniciar NOVA Partida");

        if (posicao == 1) {
            ehMinhaVez = true;
        } else {
            ehMinhaVez = false;
        }

        atorJogador.iniciarPartida(ehMinhaVez);
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

    public String obterNomeAdversario() {
        String nome = "";

        if (ehMinhaVez) {
            nome = proxy.obterNomeAdversario(2);
        } else {
            nome = proxy.obterNomeAdversario(1);
        }

        return nome;
    }
}
