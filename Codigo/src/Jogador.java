import java.util.ArrayList;

public class Jogador {

    protected String idUsuario;
    protected int pontuacao;
    protected Grade grade;
    protected boolean ativo;

    public Jogador() {
        grade = new Grade();
        ativo = true;
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

    public boolean exiteEncaixePosivel(ArrayList<Peca> pecasDisponiveis) {

        for (Peca peca : pecasDisponiveis) {
            if (grade.pecaEncaixa(peca))
                return true;
        }

        return false;
    }

    public void encerrarParticipacao() {
        ativo = false;
    }

    public boolean ativo() {
        return ativo;
    }

    public void limpar() {
        grade.esvaziar();
        pontuacao = 0;
        ativo = true;
    }
}
