import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BlocoView extends ComponentesGraficos {

    protected Rectangle retangulo;

    public BlocoView() {
        retangulo = new Rectangle();
    }

    @Override
    public ComponentesGraficos cor(String cor) {
        retangulo.setFill(Paint.valueOf(cor));
        return this;
    }

    @Override
    public void desenhar(Group grupo) {
        retangulo.setHeight(Configuracoes.UNIT);
        retangulo.setWidth(Configuracoes.UNIT);

        retangulo.setX(posicaoX);
        retangulo.setY(posicaoY);

        retangulo.setFill(Paint.valueOf(cor));
        retangulo.setStroke(Paint.valueOf("black"));

        Platform.runLater(() -> {
            grupo.getChildren().add(retangulo);
        });
    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {
        if (x > posicaoX && x < (posicaoX + Configuracoes.UNIT))
            if (y > posicaoY && y < (posicaoY + Configuracoes.UNIT))
                return true;

        return false;
    }

    @Override
    public void remover(Group grupo) {
        Platform.runLater(() -> {
            grupo.getChildren().remove(retangulo);
        });
    }
}
