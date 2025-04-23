import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	private class Bloco {
		int x;
		int y;

		Bloco(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	int LarguraJ;
	int AlturaJ;
	int TamanhoB = 25;

	// Cobra
	Bloco Cabeca;
	ArrayList<Bloco> Corpo;

	// Comida
	Bloco Comida;
	Random random;

	// Logica do jogo
	Timer loop;
	int velocidadeX;
	int velocidadeY;
	boolean gameOver = false;

	SnakeGame(int LarguraJ, int AlturaJ) {
		this.LarguraJ = LarguraJ;
		this.AlturaJ = AlturaJ;
		setPreferredSize(new Dimension(this.LarguraJ, this.AlturaJ));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);

		Cabeca = new Bloco(5, 5);
		Corpo = new ArrayList<Bloco>();

		Comida = new Bloco(10, 10);
		random = new Random();
		localC();

		velocidadeX = 0;
		velocidadeY = 1;

		loop = new Timer(100, this);
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
//		//Grade
//		for (int i = 0; i < LarguraJ; i++) {
//			//(x1, y1, x2, y2)
//			g.drawLine(i*TamanhoB, 0, i*TamanhoB, AlturaJ);
//			g.drawLine(0, i*TamanhoB, LarguraJ, i*TamanhoB);
//		}

		// Comida
		g.setColor(Color.red);
//		g.fillRect(Comida.x * TamanhoB, Comida.y * TamanhoB, TamanhoB, TamanhoB);
		g.fill3DRect(Comida.x * TamanhoB, Comida.y * TamanhoB, TamanhoB, TamanhoB, true);

		// Cabeca da Cobra
		g.setColor(Color.green);
//		g.fillRect(Cabeca.x * TamanhoB, Cabeca.y * TamanhoB, TamanhoB, TamanhoB);
		g.fill3DRect(Cabeca.x * TamanhoB, Cabeca.y * TamanhoB, TamanhoB, TamanhoB, true);

		// Corpo da Cobra
		for (int i = 0; i < Corpo.size(); i++) {
			Bloco parteCorpo = Corpo.get(i);
//			g.fillRect(parteCorpo.x * TamanhoB, parteCorpo.y * TamanhoB, TamanhoB, TamanhoB);
			g.fill3DRect(parteCorpo.x * TamanhoB, parteCorpo.y * TamanhoB, TamanhoB, TamanhoB, true);
		}

		// Pontos
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if (gameOver) {
			g.setColor(Color.red);
			g.drawString("Fim de Jogo: " + String.valueOf(Corpo.size()), TamanhoB - 16, TamanhoB);
		} else {
			g.drawString("Pontuação :" + String.valueOf(Corpo.size()), TamanhoB - 16, TamanhoB);
		}
	}

	public void localC() {
		Comida.x = random.nextInt(LarguraJ / TamanhoB); // 600/25 = 24
		Comida.y = random.nextInt(AlturaJ / TamanhoB);
	}

	public boolean colisao(Bloco bloco1, Bloco bloco2) {
		return bloco1.x == bloco2.x && bloco1.y == bloco2.y;
	}

	public void andar() {
		// Comer
		if (colisao(Cabeca, Comida)) {
			Corpo.add(new Bloco(Comida.x, Comida.y));
			localC();
		}
		// Corpo
		for (int i = Corpo.size() - 1; i >= 0; i--) {
			Bloco parteCorpo = Corpo.get(i);
			if (i == 0) {
				parteCorpo.x = Cabeca.x;
				parteCorpo.y = Cabeca.y;
			} else {
				Bloco parteAnt = Corpo.get(i - 1);
				parteCorpo.x = parteAnt.x;
				parteCorpo.y = parteAnt.y;
			}
		}

		// Player
		Cabeca.x += velocidadeX;
		Cabeca.y += velocidadeY;

		// Game Over
		for (int i = 0; i < Corpo.size(); i++) {
			Bloco parteCorpo = Corpo.get(i);
			if (colisao(Cabeca, parteCorpo)) {
				gameOver = true;
			}
		}

		if (Cabeca.x * TamanhoB < 0 || Cabeca.x * TamanhoB > LarguraJ || Cabeca.y * TamanhoB < 0
				|| Cabeca.y * TamanhoB > AlturaJ) {
			gameOver = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		andar();
		repaint();
		if (gameOver) {
			loop.stop();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && velocidadeY != 1 && gameOver == false) {
			velocidadeX = 0;
			velocidadeY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadeY != -1 && gameOver == false) {
			velocidadeX = 0;
			velocidadeY = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadeX != 1 && gameOver == false) {
			velocidadeX = -1;
			velocidadeY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadeX != -1 && gameOver == false) {
			velocidadeX = 1;
			velocidadeY = 0;
		}
		loop.start();
	}
	// Nao preciso

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
