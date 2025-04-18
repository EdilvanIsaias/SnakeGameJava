import javax.swing.*;

public class App {

	public static void main(String[] args) {

		int LarguraJ = 600;
		int AlturaJ = LarguraJ;

		JFrame frame = new JFrame("Snake");
		frame.setVisible(true);
		frame.setSize(LarguraJ, AlturaJ);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnakeGame snakeGame = new SnakeGame(LarguraJ, AlturaJ);
		frame.add(snakeGame);
		frame.pack();
		snakeGame.requestFocus();

	}
}
