import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class PecaView extends ComponentesGraficos {

    public PecaView() {

    }

    public void desenhar(Group group) {
        Rectangle rect = new Rectangle();
        rect.setWidth(Configurations.UNIT);
        rect.setHeight(Configurations.UNIT);
        group.getChildren().add(rect);
    }
}
