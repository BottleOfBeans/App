package App;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

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
    int playerSpeed = 1;
    double playerHeading = 0;


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

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while(gameThread != null){

            System.out.println(Math.toDegrees(playerHeading));
            if(Math.toDegrees(playerHeading) > 360){
                playerHeading = 0;
            }
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        if(keys.upPress){
            if(playerHeading>=0){
                player_x += (Math.sin(Math.toDegrees(playerHeading)))*playerSpeed;
                player_y += (Math.cos(Math.toDegrees(playerHeading)))*playerSpeed;
            }
        }
        if(keys.downPress){
            player_y += (Math.cos(playerHeading))*playerSpeed;
            player_x -= (Math.sin(playerHeading))*playerSpeed;
        }
        if(keys.leftPress){
            playerHeading += 0.1;


        }
        if(keys.rightPress){
            playerHeading -= 0.1;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        graphics.setColor(Color.white);


        AffineTransform backup = graphics.getTransform();
        AffineTransform transformation = new AffineTransform();
        transformation.rotate(playerHeading, player_x, player_y);
        graphics.transform(transformation);
        graphics.fillPolygon(new int[]{player_x, player_x - 10, player_x + 10}, new int[]{player_y, player_y + 50, player_y + 50},3);
        graphics.setTransform(backup);

    }


}
