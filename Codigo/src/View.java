import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
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
    protected Button comecarPartida;
    protected String nomeJogador1;
    protected String nomeJogador2;
    protected Text nomeJogador1Text;
    protected Text nomeJogador2Text;
    protected Jogador jogador1;
    protected Jogador jogador2;

    public View(Stage primaryStage, String title, int width, int height, GerenteDeEventos gerenteDeEventos) {
        this.primaryStage = primaryStage;
        group = new Group();
        this.width = width;
        this.height = height;
        this.title = title;
        this.gerenteDeEventos = gerenteDeEventos;
        gerenteDeEventos.AdicionarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
    }

    public void start() {
        Scene scene = new Scene(group, width, height);

        nomeJogador1Text = new Text("");
        nomeJogador2Text = new Text("");

        comecarPartida = new Button("Começar Partida");
        group.getChildren().add(comecarPartida);

        comecarPartida.setOnAction(event -> {
            gerenteDeEventos.NotificarEvento(Configurations.EVENTO_INICIAR_PARTIDA);
        });

        nomeJogador1Text.setX(100);
        nomeJogador1Text.setY(100);
        nomeJogador2Text.setX(1000);
        nomeJogador2Text.setY(100);

        group.getChildren().add(nomeJogador1Text);
        group.getChildren().add(nomeJogador2Text);

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
