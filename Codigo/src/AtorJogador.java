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

        return 0; // Contexto da JogadaPack
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

    public void receberJogada(JogadaPack jogadaPack) {
        System.out.print("JogadaPack Recebida");
        jogo.receberJogada(jogadaPack);

        view.novasPecas(jogo.pegarListaDePecas());
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

        view = new View(primaryStage, Configuracoes.APPNOME, Configuracoes.JANELA_LARGURA, Configuracoes.JANELA_ALTURA, gerenteEventos);
        view.start();

        nome = view.obterIdJogador();
        servidor = view.obterIdServidor();

        if (!nome.isEmpty() && !servidor.isEmpty()) {
            if (conectar())
                jogo.estabelecerConectado(true);
            else
                view.ExibirMensagemDeErro("Falha ao conectar com o servidor");
        }

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_INICIAR_PARTIDA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                rede.iniciarPartida();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_DESCONECTAR, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                if (jogo.informarConectado())
                    rede.desconectar();
            }
        });

        gerenteEventos.AdicionarOuvinte(Configuracoes.EVENTO_ENVIAR_JOGADA, new OuvinteDeEventos() {
            @Override
            public void realizaAcao() {
                JogadaPack jogadaPack = new JogadaPack();
                jogadaPack.criar(new Peca(new Posicao(1,1)), new Posicao(0,0), nome);
                rede.enviarJogada(jogadaPack);
            }
        });

        view.mensagemDeAguardo("Aguardando o outro Jogador");
    }
}
