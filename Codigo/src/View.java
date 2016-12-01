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
    protected Text nomeJogador1Text;
    protected Text nomeJogador2Text;
    protected GridPane gridPane;
    // TOOD : Remover esse botão, utilizar apenas para teste de envio de jogada
    protected Button enviarJogadaButton;
    protected GradeView gradeJogador1;
    protected GradeView gradeJogador2;
    protected Scene scene;
    protected Text aguardandoText;
    protected ArrayList<ComponentesGraficos> listaDeComponentes;

    public View(Stage primaryStage, String title, int width, int height, GerenteDeEventos gerenteDeEventos) {
        this.primaryStage = primaryStage;
        grupo = new Group();
        gridPane = new GridPane();
        listaDeComponentes = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.title = title;
        this.gerenteDeEventos = gerenteDeEventos;
        gradeJogador1 = new GradeView(grupo);
        gradeJogador2 = new GradeView(grupo);
        scene = new Scene(grupo, width, height);
        aguardandoText = new Text();

        nomeJogador1Text = new Text("");
        nomeJogador2Text = new Text("");

        comecarPartidaButton = new Button("Começar Partida");
        desconectarButton = new Button("Desconectar");
        enviarJogadaButton = new Button("Enviar Jogada");

        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_DESCONECTAR);
        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_ENVIAR_JOGADA);

        listaDeComponentes.add(gradeJogador1);
        listaDeComponentes.add(gradeJogador2);
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
        gridPane.add(enviarJogadaButton, 3, 1);

        grupo.getChildren().add(gridPane);
        grupo.getChildren().add(nomeJogador1Text);
        grupo.getChildren().add(nomeJogador2Text);

        // TODO : Descobrir o problema ao criar as grades no metodo Iniciar Partida || erro encontrado InvocationTargetException
        // TODO 2 : Repensar sobre essas constantes
        gradeJogador1.linhas(5).colunas(5).posicaoX(Configurations.POSICAO_X_GRADE_1).posixaoY(Configurations.POSICAO_Y_GRADE_1);
        gradeJogador2.linhas(5).colunas(5).posicaoX(Configurations.POSICAO_X_GRADE_2).posixaoY(Configurations.POSICAO_Y_GRADE_2);

        gradeJogador1.desenhar();
        gradeJogador2.desenhar();

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();

        conectarEventos();
        grupo.getChildren().add(aguardandoText);
    }

    private void conectarEventos() {
        comecarPartidaButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
        });

        desconectarButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_DESCONECTAR);
        });

        enviarJogadaButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_ENVIAR_JOGADA);
        });

        // INFO : So recebe eventos de click realizados sobre componentes em grupo
        grupo.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Mouse clicado em : " + event.getX() + " : " + event.getY());
                for (ComponentesGraficos componente : listaDeComponentes) {
                    if (componente.pontoPertenceAoComponente((int)event.getX(), (int)event.getY()) == true) {
                        System.out.println("Bloco Encontrado !!!");
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

    public void mensagemDeAguardo(String mensagem) {
        aguardandoText.setText(mensagem);
    }

    public void iniciarPartida() {
        System.out.println("View - Iniciar Partida");

        mensagemDeAguardo("");

        // TODO : Descobrir pq estes objetos nao aparecem na tela dentro desse metodo, somente no start funciona
        /*gradeJogador1.linhas(3).colunas(5).posicaoX(100).posixaoY(400);
        gradeJogador2.linhas(3).colunas(5).posicaoX(600).posixaoY(400);

        gradeJogador1.desenhar();
        gradeJogador2.desenhar();*/

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
}
