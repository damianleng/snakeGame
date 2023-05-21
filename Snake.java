import java.awt.Color;
import java.util.ArrayList;


public class Snake {

	private ArrayList<SnakeBody> body = new ArrayList<SnakeBody>();
	private int xPos;
	private int yPos;
	public static int foodEaten;
	private int direction = NONE;
	public boolean canSwitchDir = true;
	public int speed = 75;  // speed of the snake
	private int snakeIndex = 1;
	public static Color snakeColor1;
	public static Color snakeColor2;
	private boolean isDead = false;
	
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NONE = -1;
	public static final int INDEX_ONE = 1, INDEX_TWO = 5;

	static Sound sound = new Sound();


	
	
	public Snake(int x, int y){
		xPos = x;
		yPos = y;
		body.add(new SnakeBody(xPos, yPos));
	}
	
	public void setIndex(int index){
		snakeIndex = index;
	}
	
	public void setDirection(int dir){
		direction = dir;
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public boolean isDead(){
		return isDead;
	}
	
	public void isDead(boolean bool){
		isDead = bool;
	}
	
	public void addBody(){
		body.add(new SnakeBody(body.get(body.size()-1).getX(), body.get(body.size()-1).getY()));

	}






	public void update(){
		canSwitchDir = true;
		
		switch(direction){
		case UP:
			yPos--;
			break;
		case RIGHT:
			xPos++;
			break;
		case DOWN:
			yPos++;
			break;
		case LEFT:
			xPos--;
			break;
		}
		if(xPos > SnakePanel.grid.getWidth()-1){
			xPos = 0;
			direction = RIGHT;
		}else if(xPos < 0){
			xPos = SnakePanel.grid.getWidth()-1;
			direction = LEFT;
		}
		if(yPos > SnakePanel.grid.getHeight()-1){
			yPos = 0;
			direction = DOWN;
		}else if(yPos < 0){
			yPos = SnakePanel.grid.getHeight()-1;
			direction = UP;
		}
		
		
		if(SnakePanel.grid.getGrid(xPos, yPos) == 3){ // When Snake eats food they earn 1 point and grow 1 body
			addBody();
			foodEaten++;
			playSE(1); // Sound effect when Snakes ate food
			if(speed > 60)
				speed--;
			SnakePanel.t.setDelay(speed);
			SnakePanel.grid.spawnFood();

		}

		if(SnakePanel.grid.getGrid(xPos, yPos) == 4){ // When Snake eats trap they die
			isDead = true;
			playSE(2); // When snake eat traps, sound effect came in
		}
//
		if(direction != NONE){
			for(int x=body.size()-1; x>=1; x--){
				SnakePanel.grid.setGrid(body.get(x).getX(), body.get(x).getY(), 0);
				body.get(x).setX(body.get(x-1).getX());
				body.get(x).setY(body.get(x-1).getY());
			}
			SnakePanel.grid.setGrid(body.get(0).getX(), body.get(0).getY(), 0);
		}
		body.get(0).setX(xPos);
		body.get(0).setY(yPos);
		
		for(int x=1; x<body.size(); x++){
			if(SnakePanel.grid.getGrid(body.get(x).getX(), body.get(x).getY()) != 2)
				SnakePanel.grid.setGrid(body.get(x).getX(), body.get(x).getY(), snakeIndex);
		}
		
		if((SnakePanel.grid.getGrid(xPos, yPos) == INDEX_ONE || SnakePanel.grid.getGrid(xPos, yPos) == INDEX_TWO || SnakePanel.grid.getGrid(xPos, yPos) == 2 || SnakePanel.grid.getGrid(xPos, yPos) == 4) && direction != NONE){
			direction = NONE;
			SnakePanel.grid.setGrid(xPos, yPos, 2);
			snakeColor1 = new Color(250, 100, 100);  // color of snake 1 when collide
			snakeColor2 = new Color(250, 100, 100); // color of snake 2 when collide
			isDead = true;
			playSE(2); // When snake collide, sound effect came in
		}else if(SnakePanel.grid.getGrid(xPos, yPos) != 2) {
			SnakePanel.grid.setGrid(xPos, yPos, snakeIndex);
		}
		
	}
		public static void playMusic(int i){
		sound.setFile(i);
		sound.play();
		sound.loop();
	}



	public void stopMusic(){

		sound.stop();


	}

	public static void playSE(int i){

		sound.setFile(i);
		sound.play();


	}
}
