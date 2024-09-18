import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener , KeyListener{
    private class Tile{          //Create a Tile class
        int x;                   //Attributes of the class
        int y;                   //Attributes of the class
        Tile(int x, int y){      //Constructor of the class
            this.x = x;          //Initialize the attributes of the class
            this.y = y;          //Initialize the attributes of the class
        }
    }

    int boardWidth;           
    int boardHeight;
    int tileSize = 25;      //Set the tile size

    //Snake
    Tile snakeHead;         //Initialize the snake head
    ArrayList<Tile> snakeBody;

    //Food
    Tile Food;              //Initialize the food
    Random random;          //Initialize the random class

    //Game Logic
    Timer gameLoop;         
    int velocityX;       
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){      //Constructor of the class
        this.boardWidth = boardWidth;      //Initialize the attributes of the class
        this.boardHeight = boardHeight;    //Initialize the attributes of the class
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));   //Set the preferred size of the panel
        setBackground(Color.pink);        //Set the background color of the panel
        addKeyListener(this);              //Add the key listener to the panel to change the direction of the snake.
        setFocusable(true);               //Set the focusable of the panel
        
        snakeHead =new Tile(10,10);    //Initialize the snake head
        snakeBody = new ArrayList<>();     


        Food = new Tile(20,20);        //Initialize the food
        random = new Random();             //Initialize the random class
        placeFood();                       //Call the placeFood method

        velocityX = 0;      //Initialize the velocity of the snake
        velocityY = 0;      //Initialize the velocity of the snake

        

        gameLoop = new Timer(200,this);    //Initialize the game loop
        gameLoop.start();                   //Start the game loop
    }

    public void paintComponent(Graphics g){    //Override the paintComponent method
        super.paintComponent(g);               //Call the super class's paintComponent method
        draw(g);                               //Call the draw method
    }
    public void draw(Graphics g){          //Create a draw method

        // //For grawing grid lines
        // g.setColor(Color.white);    //Set the color of the grid lines
        // for (int i = 0; i < boardWidth/tileSize; i++) {
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);     //draw Vertical lines
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);      //draw Horizontal lines
        // }

        //For drawing the snakeHead
        g.setColor(Color.black);    //Set the color of the snake
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);     //Draw the snake

        //For drawing the snakeBody
        g.setColor(Color.yellow);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart= snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
        }

        //Score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.blue);
        if(gameOver){
            g.drawString("Game Over", 240, 280);
            g.drawString("Score: " + snakeBody.size(), 250, 300);
        }
        else{
            g.drawString("Score: " + snakeBody.size(), 10, 20);
        }
        
        
        //For drawing the food
        g.setColor(Color.red);     //Set the color of the food
        g.fill3DRect(Food.x * tileSize, Food.y * tileSize, tileSize, tileSize,true);     //Draw the food
    }
    public void placeFood(){     //Create a placeFood method
        Food.x = random.nextInt(boardWidth/tileSize);     //Set the x coordinate of the food
        Food.y = random.nextInt(boardHeight/tileSize);    //Set the y coordinate of the food
    }

    public boolean checkCollision(Tile tile1, Tile tile2){   //Create a checkCollision method
        return tile1.x==tile2.x && tile1.y==tile2.y;            //Return true if the snake head and food are on the same tile
    }
    public void move(){                //Create a move method

        if(checkCollision(snakeHead, Food)){
            snakeBody.add(new Tile(Food.x, Food.y));
            placeFood();
        }

        for (int i = snakeBody.size()-1; i >=0; i--) {
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                snakePart.x = snakeBody.get(i-1).x;
                snakePart.y = snakeBody.get(i-1).y;
            }
        }

        snakeHead.x += velocityX;      //Move the snake horizontally
        snakeHead.y += velocityY;      //Move the snake vertically

        //Game Over condition
        for (int i = 1; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if(checkCollision(snakeHead, snakePart)){
                gameOver = true;

            }
        }


        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardWidth|| snakeHead.y*tileSize<0 || snakeHead.y*tileSize>boardHeight){
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {    //Override the actionPerformed method
        move();         //Call the move method
        repaint();      //Call draw method over and over again

        if(gameOver){
            gameLoop.stop();    //Stop the game loop
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {      //Override the keyPressed method
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){          //If the key pressed is up and the snake is not moving down
            velocityX = 0;       //Set x velocity to 0
            velocityY = -1;      //
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    //Do not need these methods
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
