import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class main {

    public static void main(String [] args) {
        ConectarViewState conectarViewState = new ConectarViewState();
        GameState gameState = new GameState("Pack the bag");
        gameState.addState(conectarViewState);

        try {
            AppGameContainer gameContainer = new AppGameContainer(gameState, 1200, 800, false);
            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }


    }
}
