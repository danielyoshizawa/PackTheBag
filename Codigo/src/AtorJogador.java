import javafx.stage.Stage;

import java.util.ArrayList;

public class AtorJogador {

    protected String idUsuario;
    // TODO : Aqui será criada uma dependencia ciclica, rever isso
    protected AtorNetGames rede;
    protected Jogo jogo;
    protected String nome;
    protected String servidor;
    protected View view;
    protected GerenteDeEventos gerenteEventos;
    protected String nomeJogador1;
    protected String nomeJogador2;

    public AtorJogador() {
        super();
        // TODO : Repensar sobre essa dependencia ciclica, talvez implementar um sistema de eventos
        rede = new AtorNetGames(this);
        gerenteEventos = new GerenteDeEventos();
        jogo = new Jogo();
    }

    public boolean conectar() {
        return rede.conectar("127.0.0.1", nome);
    }

    public String obterDadosConexao() {
        return "";
    }

    public int iniciarPartida(boolean ehMinhaVez) {
        String nomeOutroJogador = rede.obterNomeAdversario() ;

        if (ehMinhaVez) {
            nomeJogador1 = this.nome;
            nomeJogador2 = nomeOutroJogador;
        } else {
            nomeJogador1 = nomeOutroJogador;
            nomeJogador2 = this.nome;
        }

        jogo.criarJogador1(nomeJogador1);
        jogo.criarJogador2(nomeJogador2);

        view.configurarJogador1(nomeJogador1);
        view.configurarJogador2(nomeJogador2);

        view.iniciarPartida();

        return 0;
    }

    public boolean avaliarInterrupcao() {
        return false;
    }

    public int desconectar() {
        return 0;
    }

    public void tratarInciarPartida() {
        rede.iniciarPartida();
        System.out.println("Tratar Iniciar Partida");
        view.iniciarPartida();
    }

    public void receberJogada(Jogada jogada) {
        System.out.print("Jogada Recebida");
    }

    public int tratarClick(float posicaoX, float posicaoY) {
        return 0;
    }

    public void enviarJogada(Peca peca, int posicaoX, int posicaoY, String idUsuario) {

    }

    // TODO : Analizar como este metodo sera implementado
    public ArrayList<Integer> informarEstado() {

        return null;
    }

    public ArrayList<Integer> notificarPecasDisponiveis() {
        return null;
    }

    public void comecar(Stage primaryStage) {

        view = new View(primaryStage, Configurations.APPNOME, Configurations.JANELA_LARGURA, Configurations.JANELA_ALTURA, gerenteEventos);
        view.start();

        nome = view.obterIdJogador();
        servidor = view.obterIdServidor();

        if (!nome.isEmpty() && !servidor.isEmpty()) {
            if (conectar())
                jogo.estabelecerConectado(true);
            else
                view.ExibirMensagemDeErro("Falha ao conectar com o servidor");
        }

        gerenteEventos.AdicionarOuvinte(Configurations.EVENTO_INICIAR_PARTIDA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                rede.iniciarPartida();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configurations.EVENTO_DESCONECTAR, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                if (jogo.informarConectado())
                    rede.desconectar();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configurations.EVENTO_ENVIAR_JOGADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                rede.enviarJogada(new Jogada());
            }
        });

        view.mensagemDeAguardo("Aguardando o outro Jogador");
    }
}
