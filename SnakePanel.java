import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;




public class SnakePanel extends JPanel{

	public static final int X_WIDTH = 400;
	public static final int Y_WIDTH = 400;
	public static final Color BACKGROUND_COLOR = Color.BLACK;

	private BufferedImage image;
	private Graphics2D g;
	public static Timer t;
	
	public static Grid grid;
	public Snake snake;
	public Snake snake2;

	public boolean gameOver;
	public boolean Win;

	Sound sound = new Sound();


	
	public SnakePanel() {
		image = new BufferedImage(X_WIDTH, Y_WIDTH, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		t = new Timer(5, new TimerListener());
		t.start();




		resetGame();


		

		addMouseListener(new Click());
		addKeyListener(new Key());
		setFocusable(true);

		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, X_WIDTH, Y_WIDTH);
	}


	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.white);
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		FontMetrics metrics;
		metrics = getFontMetrics(g.getFont());
		g.drawString("SCORE: " + Snake.foodEaten, (getWidth() - metrics.stringWidth("Score: " + Snake.foodEaten))/2, g.getFont().getSize());

		if(gameOver){   // Game Over Text
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", Font.BOLD, 28));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Game Over", 325,400);
		}

		if(Win){  // You Win Text
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", Font.BOLD, 28));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("You Win", 350,400);

		}



	}

	private class TimerListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			g.setColor(BACKGROUND_COLOR);
			g.fillRect(0, 0, X_WIDTH, Y_WIDTH);

			snake.update();
			snake2.update();
			if(snake.isDead() && !snake2.isDead()){
				snake2.setDirection(Snake.NONE);
				snake2.isDead(true);

			}
			if(snake2.isDead() && !snake.isDead()){
				snake.setDirection(Snake.NONE);
				snake.isDead(true);


			}


			grid.draw(g);
			
			repaint();
		}



	}

	private class Click extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			
		}
		
		public void mouseReleased(MouseEvent e){
			
		}
	}
	
	public void resetGame(){

		grid = new Grid(X_WIDTH, Y_WIDTH);


		snake = new Snake(X_WIDTH/20 + 10, Y_WIDTH/20);
		snake.setIndex(Snake.INDEX_ONE);
		snake.update();
		snake2 = new Snake(X_WIDTH/20 - 10, Y_WIDTH/20);
		snake2.setIndex(Snake.INDEX_TWO);
		//snake2.update();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnFood();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		grid.spawnTrap();
		Snake.snakeColor1 = new Color(180, 250, 180);
		Snake.snakeColor2 = new Color(180, 180, 250);
		t.setDelay(snake.speed);
		Snake.foodEaten = 0;

	}




	private class Key extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if(snake.canSwitchDir){
				gameOver = false;  // When Click on Keyboard Game Over goes away
				Win = false; // When Click on Keyboard Win goes away
				if(!snake.isDead()){
					switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						if(snake.getDirection() != Snake.DOWN)
							snake.setDirection(Snake.UP);
						snake.canSwitchDir = false;
						break;
					case KeyEvent.VK_RIGHT:
						if(snake.getDirection() != Snake.LEFT)
							snake.setDirection(Snake.RIGHT);
						snake.canSwitchDir = false;
						break;
					case KeyEvent.VK_DOWN:
						if(snake.getDirection() != Snake.UP)
							snake.setDirection(Snake.DOWN);
						snake.canSwitchDir = false;
						break;
					case KeyEvent.VK_LEFT:
						if(snake.getDirection() != Snake.RIGHT)
							snake.setDirection(Snake.LEFT);
						snake.canSwitchDir = false;
						break;
					case KeyEvent.VK_ENTER:
						for(int x=0; x<1000; x++){
							snake.addBody();
						}
						break;
					}
				}
			}
			
			if(snake2.canSwitchDir){
				if(!snake2.isDead()){
					switch(e.getKeyCode()){
					case KeyEvent.VK_W:
						if(snake2.getDirection() != Snake.DOWN)
							snake2.setDirection(Snake.UP);
						snake2.canSwitchDir = false;
						break;
					case KeyEvent.VK_D:
						if(snake2.getDirection() != Snake.LEFT)
							snake2.setDirection(Snake.RIGHT);
						snake2.canSwitchDir = false;
						break;
					case KeyEvent.VK_S:
						if(snake2.getDirection() != Snake.UP)
							snake2.setDirection(Snake.DOWN);
						snake2.canSwitchDir = false;
						break;
					case KeyEvent.VK_A:
						if(snake2.getDirection() != Snake.RIGHT)
							snake2.setDirection(Snake.LEFT);
						snake2.canSwitchDir = false;
						break;
					case KeyEvent.VK_ENTER:
						for(int x=0; x<1000; x++){
							snake2.addBody();
						}
						break;
					}
				}
			}
			
			if(snake.isDead() || snake2.isDead()){
				gameOver = true; // When one of the snakes died or ate a trap, game over text pop up
				resetGame();







			}

			if (snake.foodEaten == 100){  // Set the winning point here
				Win = true; // When both players reached 100, You win text pop up
				Snake.playSE(3);  // Sound effect when players win
				resetGame();

			}




		}

		public void keyReleased(KeyEvent e) {

		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snakie");
		frame.setSize(X_WIDTH*2 + 12, Y_WIDTH*2 + 28);
		frame.setLocation(200, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setContentPane(new SnakePanel());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		ImageIcon image = new ImageIcon("SnakieIcon.jpg");
		frame.setIconImage(image.getImage());

		Snake.playMusic(0);   // Background Music












	}
}

