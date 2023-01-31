package App;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel implements Runnable{
    /*
        Game Tile Size shows the tile that each of the images on screen should be in pixels, this is not the amount that are displayed in each tile but rather the orignal one
        Scalable value shows the value that is used to upscale the original images on the screen
        Game Column and Row amounts show the amount of rows and columns that can be displayed on screen at one time
     */
    static int GameTileSize = 16;
    static int ScalableValue = 3;
    static int gameColumnAmount = 16;
    static int gameRowAmount = 12;

    /*
        ActualTileSize uses the variables from above to calculate how big each tile actually should be
        Game Width and Height takes into account the columns and rows and calculates the window heights and widths using all the factors provided above
     */
    static int ActualTileSize = GameTileSize * ScalableValue;
    static int gameWidth = gameColumnAmount*ActualTileSize;
    static int gameHeight = gameRowAmount*ActualTileSize;

    Thread gameThread;
    KeyHandler keys = new KeyHandler();

    //Game Values
    int FPS = 60;


    //Player Values
    int player_x = 400;
    int player_y = 400;
    int playerSpeed = 20;

    public GameWindow(){
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keys);
        this.setFocusable(true);
    }

    public void startWindowThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        while(gameThread != null){
            double drawInterval = 100000000/FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;

            //System.out.println("Running Lolz!");
            //Update Location
            update();
            repaint();

            double remainingTime = nextDrawTime-System.nanoTime();

            if(remainingTime<0){
                remainingTime = 0;
            }

            try {
                Thread.sleep((long) remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update(){
        if(keys.upPress == true){
            player_y -= playerSpeed;
        }
        if(keys.downPress == true){
            player_y += playerSpeed;
        }
        if(keys.leftPress == true){
            player_x -= playerSpeed;
        }
        if(keys.rightPress == true){
            player_x += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        graphics.setColor(Color.white);


        graphics.fill3DRect(player_x,player_y,ActualTileSize,ActualTileSize,true);
        graphics.dispose();
    }


}
