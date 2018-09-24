package ch.bbw.zuul.zuul.zuul;

/**
 * Gamestarter Class
 * @author godu
 * @version 1.0
 */
public class GameStarter {

	/**
	 * Creates GameObj and starts it with the method Play
	 * @param args
	 */
	public static void main(String[] args) {
		Game gameObj = new Game();
		
		gameObj.play();
	}

}