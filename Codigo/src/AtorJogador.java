import javafx.stage.Stage;

import java.util.ArrayList;

public class AtorJogador {

    protected String idUsuario;
    // TODO : Aqui será criada uma dependencia ciclica, rever isso
    protected AtorNetGames rede;
    protected Jogo jogo;
    protected String nome;
    protected String servidor;
    protected boolean conectado;
    protected View view;

    public AtorJogador() {
        super();
        // TODO : Repensar sobre essa dependencia ciclica, talvez implementar um sistema de eventos
        rede = new AtorNetGames(this);
    }

    public boolean conectar() {
        return rede.conectar("127.0.0.1", nome);
    }

    public String obterDadosConexao() {
        return "";
    }

    public int iniciarPartida(boolean ehMinhaVez) {
        String nomeOutroJogador = rede.obterNomeAdversario() ;
        jogo = new Jogo();

        if (ehMinhaVez) {
            jogo.criarJogador(this.nome);
            jogo.criarJogador(nomeOutroJogador);
        } else {
            jogo.criarJogador(nomeOutroJogador);
            jogo.criarJogador(this.nome);
        }

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
        view.iniciarParida();
    }

    public void receberJogada(Jogada jogada) {

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

        view = new View(primaryStage, Configurations.APPNOME, Configurations.JANELA_LARGURA, Configurations.JANELA_ALTURA);
        view.start();

        nome = view.obterIdJogador();
        servidor = view.obterIdServidor();

        if (!nome.isEmpty() && !servidor.isEmpty()) {
            conectado = conectar();
        }

        if (conectado) {
            System.out.println("Conectado");
        }

        view.mensagemDeAguardo();
    }
}
