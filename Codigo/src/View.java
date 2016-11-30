import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {

    protected Stage primaryStage;
    protected Group group;
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

    public View(Stage primaryStage, String title, int width, int height, GerenteDeEventos gerenteDeEventos) {
        this.primaryStage = primaryStage;
        group = new Group();
        gridPane = new GridPane();
        this.width = width;
        this.height = height;
        this.title = title;
        this.gerenteDeEventos = gerenteDeEventos;
        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_DESCONECTAR);
    }

    public void start() {
        Scene scene = new Scene(group, width, height);

        nomeJogador1Text = new Text("");
        nomeJogador2Text = new Text("");

        comecarPartidaButton = new Button("Começar Partida");
        desconectarButton = new Button("Desconectar");

        gridPane.add(comecarPartidaButton, 1, 1);
        gridPane.add(desconectarButton, 2, 1);

        group.getChildren().add(gridPane);

        comecarPartidaButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
        });

        desconectarButton.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_DESCONECTAR);
        });

        nomeJogador1Text.setX(100);
        nomeJogador1Text.setY(100);
        nomeJogador2Text.setX(1000);
        nomeJogador2Text.setY(100);

        group.getChildren().add(nomeJogador1Text);
        group.getChildren().add(nomeJogador2Text);

        GradeView grade = new GradeView();
        grade.colunas(4).linhas(4).posicaoX(200).posixaoY(200);

        grade.desenhar(group);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void mensagemDeAguardo() {
        Text aguardandoText = new Text("Aguardando o outro Jogador");
        aguardandoText.setX(550);
        aguardandoText.setY(450);
        group.getChildren().add(aguardandoText);
    }

    public void iniciarPartida() {
        System.out.println("View - Iniciar Partida");

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
