import javax.swing.*;     //Import Swing library to create GUI
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");  //Create a Window
        frame.setVisible(true);                  //Make the window visible
        frame.setSize(boardWidth,boardHeight);     //Set the size of the window
        frame.setLocationRelativeTo(null);       //Set the location of the window
        frame.setResizable(true);       //Set the window to not be resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Set the close operation of the window
    
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);  //Create an Object of SnakeGame
        frame.add(snakeGame);       //Add the SnakeGame object to the frame
        frame.pack();               //Pack the frame to fit the content
        snakeGame.requestFocus();   //Set the focus on the SnakeGame object
    }
}
