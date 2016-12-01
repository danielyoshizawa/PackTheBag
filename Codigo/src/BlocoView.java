import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BlocoView extends ComponentesGraficos {

    protected Rectangle rect;

    public BlocoView(Group grupo) {
        super(grupo);
        rect = new Rectangle();
        super.grupo.getChildren().add(rect);
    }

    @Override
    public ComponentesGraficos cor(String cor) {
        rect.setFill(Paint.valueOf(cor));
        return this;
    }

    @Override
    public void desenhar() {
        rect.setHeight(Configurations.UNIT);
        rect.setWidth(Configurations.UNIT);

        rect.setX(posicaoX);
        rect.setY(posicaoY);

        rect.setFill(Paint.valueOf(cor));
        rect.setStroke(Paint.valueOf("black"));

    }

    @Override
    public boolean pontoPertenceAoComponente(int x, int y) {
        if (x > posicaoX && x < (posicaoX + Configurations.UNIT))
            if (y > posicaoY && y < (posicaoY + Configurations.UNIT))
                return true;

        return false;
    }
}
