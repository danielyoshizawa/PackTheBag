
public class Jogador {

    protected String idUsuario;
    protected int pontuacao;
    protected Grade grade;

    public Jogador() {
        grade = new Grade();
    }

    public void assumirNome(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void iniciarGrade(int numLinhas, int numColunas) {
        grade.iniciar(numLinhas, numColunas);
    }

    public boolean aplicarJogada(JogadaPack jogada) {
        return grade.encaixa(jogada);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public int calcularPontuacao() {
        pontuacao = grade.pontuacao();
        return pontuacao;
    }
}
