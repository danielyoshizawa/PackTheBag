import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BlocoView extends ComponentesGraficos {

    protected Rectangle rect;

    public BlocoView() {
        rect = new Rectangle();
    }

    @Override
    public ComponentesGraficos cor(String cor) {
        rect.setFill(Paint.valueOf(cor));
        return super.cor(cor);
    }

    @Override
    public void desenhar(Group group) {
        rect.setHeight(Configurations.UNIT);
        rect.setWidth(Configurations.UNIT);

        rect.setX(posicaoX);
        rect.setY(posicaoY);

        rect.setFill(Paint.valueOf(cor));
        rect.setStroke(Paint.valueOf("black"));

        group.getChildren().add(rect);
    }
}
