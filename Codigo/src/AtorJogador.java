import java.util.ArrayList;

public class AtorJogador {

    protected String idUsuario;
    // TODO : Aqui será criada uma dependencia ciclica, rever isso
    protected AtorNetGames rede;
    protected Jogo jogo;
    // TODO : Criar a view
    // protected View view;

    public AtorJogador() {

    }

    public boolean conectar() {
        return false;
    }

    public String obterDadosConexao() {
        return "";
    }

    public int iniciarPartida() {
        return 0;
    }

    public boolean avaliarInterrupcao() {
        return false;
    }

    public int desconectar() {
        return 0;
    }

    public void tratarInciarPartida() {

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
}
