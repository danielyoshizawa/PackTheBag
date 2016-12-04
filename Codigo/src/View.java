import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class View {

    protected Stage primaryStage;
    protected Group grupo;
    protected int width;
    protected int height;
    protected String title;
    protected String nome;
    protected String servidor;
    protected GerenteDeEventos gerenteDeEventos;
    protected Button comecarPartidaButton;
    protected Button desconectarButton;
    protected String nomeJogador1;
    protected String nomeJogador2;
    protected String nomeJogadorDaVez;
    protected Text nomeJogador1Text;
    protected Text nomeJogador2Text;
    protected GridPane gridPane;
    protected GradeView gradeJogador1;
    protected GradeView gradeJogador2;
    protected Scene scene;
    protected Text aguardandoText;
    protected ArrayList<ComponentesGraficos> listaDeComponentes;
    protected ArrayList<PecaView> pecasDisponiveis;

    public View(Stage primaryStage, String title, int width, int height, GerenteDeEventos gerenteDeEventos) {
        this.primaryStage = primaryStage;
        grupo = new Group();
        gridPane = new GridPane();
        listaDeComponentes = new ArrayList<>();
        pecasDisponiveis = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.title = title;
        this.gerenteDeEventos = gerenteDeEventos;
        scene = new Scene(grupo, width, height);
        aguardandoText = new Text();

        nomeJogador1Text = new Text("");
        nomeJogador2Text = new Text("");

        comecarPartidaButton = new Button("Começar Partida");
        desconectarButton = new Button("Desconectar");


        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_INICIAR_PARTIDA);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_DESCONECTAR);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_PECA_SELECIONADA);
        gerenteDeEventos.AdicionarEvento(Configuracoes.EVENTO_GRADE_SELECIONADA);
    }

    public void start() {
        nomeJogador1Text.setX(100);
        nomeJogador1Text.setY(100);
        nomeJogador2Text.setX(1000);
        nomeJogador2Text.setY(100);

        aguardandoText.setX(550);
        aguardandoText.setY(450);

        gridPane.add(comecarPartidaButton, 1, 1);
        gridPane.add(desconectarButton, 2, 1);

        grupo.getChildren().add(gridPane);
        grupo.getChildren().add(nomeJogador1Text);
        grupo.getChildren().add(nomeJogador2Text);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();

        conectarEventos();
        grupo.getChildren().add(aguardandoText);
    }

    private void conectarEventos() {
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
                System.out.println("Mouse clicado em : " + event.getX() + " : " + event.getY());
                System.out.println("Jogador da Vez : " + nomeJogadorDaVez);
                Posicao posicaoClick = new Posicao(0,0);

                // TODO : Repensar sobre essa logica inserida na view
                if (!nome.equals(nomeJogadorDaVez)) {
                    mensagemDeStatus("Ainda não é sua vez, seu apressadinho!!!");
                    return;
                }

                for (ComponentesGraficos componente : listaDeComponentes) {
                    if (componente.pontoPertenceAoComponente((int)event.getX(), (int)event.getY(), posicaoClick) == true) {
                        if (componente instanceof GradeView) {
                            System.out.println("Grade Encontrado !!! na posicao : " + posicaoClick.getX() + " - " + posicaoClick.getY());
                            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_GRADE_SELECIONADA, ((GradeView) componente).getIdUsuario(), posicaoClick);
                        }
                        else if (componente instanceof PecaView) {
                            System.out.println("Peca Encontrada !!! na posicao : " + posicaoClick.getX() + " - " + posicaoClick.getY());
                            gerenteDeEventos.NotificarEvento(Configuracoes.EVENTO_PECA_SELECIONADA, ((PecaView) componente).getIdentificador(), posicaoClick);
                        }
                    }
                }
            }
        });
    }

    public String obterIdJogador() {
        TextInputDialog dialogoNome = new TextInputDialog();
        dialogoNome.setTitle("Entrada de nome");
        dialogoNome.setHeaderText("Entre com seu nome");
        dialogoNome.setContentText("Nome:");
        dialogoNome.showAndWait().ifPresent(v -> nome = v);
        return nome;
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
        System.out.println("View - Iniciar Partida");

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

    public void configurarJogador1(String nome) {
        this.nomeJogador1 = nome;
        nomeJogador1Text.setText(nome);
    }

    public void configurarJogador2(String nome) {
        this.nomeJogador2 = nome;
        nomeJogador2Text.setText(nome);
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

    private void limparPecasDisponiveis() {
        for (PecaView peca : pecasDisponiveis) {
            peca.remover(grupo);
            listaDeComponentes.remove(peca);
        }

        pecasDisponiveis.clear();
    }

    public void setNomeJogadorDaVez(String jogadorDaVez) {
        this.nomeJogadorDaVez = jogadorDaVez;
    }

    public void aplicarJogada(JogadaPack jogada) {
        if (jogada.getIdUsuario().equals(nomeJogador1)) {
            gradeJogador1.aplicarJogada(jogada.peca.pegarPosicoes(), jogada.peca.getCor());
        } else if (jogada.getIdUsuario().equals(nomeJogador2)) {
            gradeJogador2.aplicarJogada(jogada.peca.pegarPosicoes(), jogada.peca.getCor());
        }
    }
}
