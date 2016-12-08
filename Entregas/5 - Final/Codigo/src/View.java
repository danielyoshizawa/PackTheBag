import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class View {

    protected Stage primaryStage;
    protected Group grupo;
    protected int largura;
    protected int altura;
    protected String titulo;
    protected String idUsuario;
    protected String servidor;
    protected GerenteDeEventos gerenteDeEventos;
    protected Button conectarButton;
    protected Button comecarPartidaButton;
    protected Button desconectarButton;
    protected String nomeJogador1;
    protected String nomeJogador2;
    protected String nomeJogadorDaVez;
    protected Text nomeJogador1Text;
    protected Text nomeJogador2Text;
    protected Text nomeJogadorDaVezText;
    protected Text pontuacaoJogador1Text;
    protected Text pontuacaoJogador2Text;
    protected GridPane gridPane;
    protected GradeView gradeJogador1;
    protected GradeView gradeJogador2;
    protected Scene scene;
    protected Text aguardandoText;
    protected ArrayList<ComponentesGraficos> listaDeComponentes;
    protected ArrayList<PecaView> pecasDisponiveis;

    public View(Stage primaryStage, String titulo, int largura, int altura, GerenteDeEventos gerenteDeEventos) {
        this.primaryStage = primaryStage;
        grupo = new Group();
        gridPane = new GridPane();
        listaDeComponentes = new ArrayList<>();
        pecasDisponiveis = new ArrayList<>();
        this.largura = largura;
        this.altura = altura;
        this.titulo = titulo;
        this.gerenteDeEventos = gerenteDeEventos;
        scene = new Scene(grupo, largura, altura);
        aguardandoText = new Text();
        grupo.getStylesheets().add("style.css");

        nomeJogador1Text = new Text("");
        nomeJogador2Text = new Text("");
        nomeJogadorDaVezText = new Text("");
        pontuacaoJogador1Text = new Text("");
        pontuacaoJogador2Text = new Text("");

        conectarButton = new Button("Conectar");
        comecarPartidaButton = new Button("Começar Partida");
        desconectarButton = new Button("Desconectar");

        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_CONECTAR);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_INICIAR_PARTIDA);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_DESCONECTAR);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_PECA_SELECIONADA);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_GRADE_SELECIONADA);
    }

    public void iniciar() {

        nomeJogador1Text.setX(100);
        nomeJogador1Text.setY(550);
        nomeJogador2Text.setX(800);
        nomeJogador2Text.setY(550);
        nomeJogadorDaVezText.setX(500);
        nomeJogadorDaVezText.setY(100);
        pontuacaoJogador1Text.setX(100);
        pontuacaoJogador1Text.setY(450);
        pontuacaoJogador2Text.setX(800);
        pontuacaoJogador2Text.setY(450);

        aguardandoText.setX(450);
        aguardandoText.setY(600);
        aguardandoText.getStyleClass().add("aguardandoTexto");

        gridPane.add(conectarButton, 1, 1);
        gridPane.add(comecarPartidaButton, 2, 1);
        gridPane.add(desconectarButton, 3, 1);

        grupo.getChildren().add(gridPane);
        grupo.getChildren().add(nomeJogador1Text);
        grupo.getChildren().add(nomeJogador2Text);
        grupo.getChildren().add(nomeJogadorDaVezText);
        grupo.getChildren().add(pontuacaoJogador1Text);
        grupo.getChildren().add(pontuacaoJogador2Text);

        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
        primaryStage.show();

        conectarEventos();
        grupo.getChildren().add(aguardandoText);
    }

    protected void conectarEventos() {

        conectarButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_CONECTAR);
        });
        comecarPartidaButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_INICIAR_PARTIDA);
        });

        desconectarButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_DESCONECTAR);
        });

        // INFO : So recebe eventos de click realizados sobre componentes em grupo
        grupo.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Posicao posicaoClick = new Posicao(0,0);

                for (ComponentesGraficos componente : listaDeComponentes) {
                    if (componente.pontoPertenceAoComponente((int)event.getX(), (int)event.getY(), posicaoClick)) {
                        if (componente instanceof GradeView) {
                            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_GRADE_SELECIONADA, ((GradeView) componente).getIdUsuario(), posicaoClick);
                        }
                        else if (componente instanceof PecaView) {
                            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_PECA_SELECIONADA, ((PecaView) componente).getIdentificador(), posicaoClick);
                        }
                    }
                }
            }
        });
    }

    public String obterIdJogador() {
        TextInputDialog dialogoNome = new TextInputDialog();
        dialogoNome.setTitle("Entrada de idUsuario");
        dialogoNome.setHeaderText("Entre com seu idUsuario");
        dialogoNome.setContentText("Nome:");
        dialogoNome.showAndWait().ifPresent(v -> idUsuario = v);
        return idUsuario;
    }

    public String obterIdServidor() {
        TextInputDialog dialogoNome = new TextInputDialog();
        dialogoNome.setTitle("Entrada de Servidor");
        dialogoNome.setHeaderText("Entre com o ip do servidor");
        dialogoNome.setContentText("IP servidor:");
        dialogoNome.showAndWait().ifPresent(v -> servidor = v);
        return servidor;
    }

    public void mensagemDeStatus(String mensagem) {
        aguardandoText.setText(mensagem);
    }

    public void iniciarPartida() {
        mensagemDeStatus("");

        gradeJogador1 = new GradeView(nomeJogador1);
        gradeJogador2 = new GradeView(nomeJogador2);

        listaDeComponentes.add(gradeJogador1);
        listaDeComponentes.add(gradeJogador2);

        gradeJogador1.linhas(5).colunas(5).posicaoX(Configuracoes.POSICAO_X_GRADE_1).posixaoY(Configuracoes.POSICAO_Y_GRADE_1);
        gradeJogador2.linhas(5).colunas(5).posicaoX(Configuracoes.POSICAO_X_GRADE_2).posixaoY(Configuracoes.POSICAO_Y_GRADE_2);

        gradeJogador1.desenhar(grupo);
        gradeJogador2.desenhar(grupo);
    }

    public void configurarJogador1(String idUsuario) {
        this.nomeJogador1 = idUsuario;
        nomeJogador1Text.setText(idUsuario);
        nomeJogador1Text.getStyleClass().add("jogador-idUsuario");
    }

    public void configurarJogador2(String idUsuario) {
        this.nomeJogador2 = idUsuario;
        nomeJogador2Text.setText(idUsuario);
        nomeJogador2Text.getStyleClass().add("jogador-idUsuario");
    }

    public void ExibirMensagemDeErro(String mensagemDeErro) {
        Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
        dialogoErro.setTitle("Ocorreu um Erro");
        dialogoErro.setHeaderText("Erro");
        dialogoErro.setContentText(mensagemDeErro);
        dialogoErro.showAndWait();
    }

    public void novasPecas(ArrayList<Peca> pecas) {

        limparPecasDisponiveis();

        for (int i = 0; i < pecas.size(); i++) {
            PecaView pecaView = new PecaView(pecas.get(i).pegarPosicoes());
            pecaView.setIdentificador(pecas.get(i).getIdentificador());
            // TODO : Rever essas posicoes
            pecaView.posixaoY(200);
            pecaView.posicaoX(100 + (5*i * Configuracoes.UNIT));
            pecaView.cor(pecas.get(i).getCor());
            pecaView.desenhar(grupo);
            pecasDisponiveis.add(pecaView);
            listaDeComponentes.add(pecaView);
        }
    }

    protected void limparPecasDisponiveis() {
        for (PecaView peca : pecasDisponiveis) {
            peca.remover(grupo);
            listaDeComponentes.remove(peca);
        }

        pecasDisponiveis.clear();
    }

    public void setNomeJogadorDaVez(String jogadorDaVez) {
        this.nomeJogadorDaVez = jogadorDaVez;
        nomeJogadorDaVezText.setText("Jogador da vez : " + jogadorDaVez);
    }

    public void aplicarJogada(JogadaPack jogada) {
        if (jogada.getIdUsuario().equals(nomeJogador1)) {
            gradeJogador1.aplicarJogada(jogada.peca.pegarPosicoes(), jogada.peca.getCor());
        } else if (jogada.getIdUsuario().equals(nomeJogador2)) {
            gradeJogador2.aplicarJogada(jogada.peca.pegarPosicoes(), jogada.peca.getCor());
        }
    }

    public void exibirPontuacao(int pontuacaoJogador1, int pontuacaoJogador2) {
        pontuacaoJogador1Text.setText(nomeJogador1 + " fez " + pontuacaoJogador1 + " pontos.");
        pontuacaoJogador2Text.setText(nomeJogador2 + " fez " + pontuacaoJogador2 + " pontos.");
    }

    public boolean notificarInterromperPartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Interromper Partida");
        alert.setHeaderText("Deseja finalizar a partida");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    public void limparComponentesGraficos() {
        nomeJogador1Text.setText("");
        nomeJogador2Text.setText("");
        nomeJogadorDaVezText.setText("");
        pontuacaoJogador1Text.setText("");
        pontuacaoJogador2Text.setText("");
        gradeJogador1 = null;
        gradeJogador2 = null;
        listaDeComponentes.clear();
        limparPecasDisponiveis();
        mensagemDeStatus("");
    }
}
