import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    final String HIGH_SCORE_FILE = "HighScore.txt";

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }  

    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;
    int highScore = 0;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;

        loadHighScore();
        
		//game timer
		gameLoop = new Timer(100, this); //how long it takes to start timer, milliseconds gone between frames 
        gameLoop.start();
	}	
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
        }

        //Food
        g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        /*
            fillRect(int x, int y, int width, int height)
                Purpose: Draws a flat, filled rectangle.
                Appearance: No 3D effect; just a plain rectangle filled with the current color.
                Use case: Standard shapes, backgrounds, and UI elements where no depth or visual illusion is needed.
        */
        /*
            fill3DRect(int x, int y, int width, int height, boolean raised)
                Purpose: Draws a filled rectangle with a simulated 3D effect (using highlights and shadows).
                Appearance: Appears either raised or sunken, depending on the raised boolean.
                    true → Raised (like a button).
                    false → Sunken (like a pressed button).
                Use case: UI components, visual feedback (like buttons), or whenever a 3D look is desired.
         */
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        
        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
		}

        // Scores
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.white);
        g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
        g.drawString("High Score: " + highScore, tileSize - 16, tileSize * 2);

        // Game Over Messages
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over!", tileSize - 16, tileSize * 4);
            g.drawString("Press SPACE to restart", tileSize - 16, tileSize * 5);
        }
	}

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
		food.y = random.nextInt(boardHeight/tileSize);
	}

    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //right before the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            //collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize >= boardWidth || //passed left border or right border
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize >= boardHeight ) { //passed top border or bottom border
            gameOver = true;
        }

        if (gameOver && snakeBody.size() > highScore) {
            highScore = snakeBody.size();
            saveHighScore();
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    private void loadHighScore() {
        try {
            java.io.File file = new java.io.File(HIGH_SCORE_FILE);
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(HIGH_SCORE_FILE);
            writer.println(highScore);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }

        // Restart the game when space is pressed after game over
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            // Reset game state
            snakeHead = new Tile(5, 5);
            snakeBody.clear();
            velocityX = 1;
            velocityY = 0;
            placeFood();
            gameOver = false;
            gameLoop.start();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}