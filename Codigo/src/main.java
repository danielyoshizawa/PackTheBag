import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class main {

    protected static GameView gameView;

    public static void main(String [] args) {
        gameView = new GameView("Pack the bag");
        try {
            AppGameContainer gameContainer = new AppGameContainer(gameView);
            gameContainer.setDisplayMode(640, 480, false);
            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }


    }
}
