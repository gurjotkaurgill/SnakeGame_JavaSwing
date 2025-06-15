import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
	    frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image icon = new ImageIcon(App.class.getResource("./icon/snake.png")).getImage();
        frame.setIconImage(icon);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack(); //get full size apart from the title bar and borders
        snakeGame.requestFocus();
    }
}