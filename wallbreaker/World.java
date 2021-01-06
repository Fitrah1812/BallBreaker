import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.Random;

/* Class to denote 'World" aka the "The space in which the game occurs" */
public class World extends JComponent
{
    private Ball ball;  // A Object of type ball
    private Player player; // Object denoting player
    private Brick[][] bricks; // A 2d array of bricks
    private Ellipse2D e; // graphics object for Ball
    private Rectangle2D c; // Graphics object for Player
    private int score = 0; // Game score
    public boolean isGameOver = false; // Denotes whether the game is over or not
    private int lives = 3; // Number of lives
    private double impact_distance; /* distance from point of impact 
                       to the center of the Player block */
    private Font f1, f2;  /* Font objects for changing font styles */
    private TextLayout t1, t2, t3; /* Text layouts */
    private FontRenderContext frc;
    private Preferences prefs; // Top score preferences
    public static Pong pong;
    public int width = 1080, height = 720;
    public Tgambar tgambar;
    public boolean bot = false, selectingDifficulty;
    public boolean w, s, up, down;
    public int gameStatus = 0, scoreLimit = 7, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    public int botDifficulty, botMoves, botCooldown = 0;
    public Random random;
    public JFrame jframe;
    public World() {
        //Timer timer = new Timer(20, this);
        //random = new Random();
        jframe = new JFrame("SELAMAT DATANG DIGAME");
        //tgambar = new Tgambar();
        jframe.setSize(width + 15, height + 35);
        //jframe.setVisible(true);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.add(tgambar);
        //jframe.addKeyListener(this);
        //timer.start();
    e = new Ellipse2D.Double();
    c = new Rectangle2D.Double();
    prefs = Preferences.userNodeForPackage(this.getClass());
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        frc = g2.getFontRenderContext();
        if(isGameOver) {        
            t1 = new TextLayout("Game Over", f1, frc);
            t2 = new TextLayout("Keren!", f2, frc);
            t3 = new TextLayout("Coba Lagi", f2, frc);
            t1.draw(g2, 300, 270);
            if(score == 15) {
                t2.draw(g2, 350, 290);  // Means the player has cleared all the bricks
            }
            else{
            t3.draw(g2, 350, 290); // Game ended but some bricks left
            }
            g2.drawString("Top Scores", 360, 350); // Display top scores of all time
            g2.drawString("First:  "+Integer.toString(prefs.getInt("#1", 0)), 360, 370);
            g2.drawString("Second: "+Integer.toString(prefs.getInt("#2", 0)), 360, 390);
            g2.drawString("Third:  "+Integer.toString(prefs.getInt("#3", 0)), 360, 410);
            g2.drawString("Fourth: "+Integer.toString(prefs.getInt("#4", 0)), 360, 430);
            g2.drawString("Fifth: "+Integer.toString(prefs.getInt("#5", 0)), 360, 450);
            
        }
        //else if(!isGameOver){
        //    if(gameStatus==1){
        //        g.drawString("PAUSED", width/2 -103, height/2 -25);
        //    }
        //}
        e.setFrame(ball.x, ball.y, ball.radius, ball.radius); // Set ball position
        c.setFrame(player.x, player.y, player.width, player.height); // Set player position
        g2.fill(e);
        g2.draw(e);
        g2.fill(c);
        g2.draw(c);
        g2.drawString("Score: "+Integer.toString(score), 20, 560);
        g2.drawString("Game By Fitrah And Ivan", 250, 720);
        g2.drawString("Lives: "+Integer.toString(lives)+" Remaining", 650, 560);
        // Draw the Bricks
        for(Brick[] Row : bricks) {
            for(Brick brick : Row) {
                if(brick.isRendered) {          
                    g2.setColor(Color.blue);           
                    g2.fill(brick.br);
                    g2.setColor(Color.green);
                    g2.draw(brick.br);          
                }
            }
        }
    }
    
    public void resetPlayer() {
        // Resets the player position
        Dimension d = getSize();
        ball.x = d.width/2;
        ball.y = d.height - 65;
        ball.dx = 4.0;
        ball.dy = -4.0;
        player.x = d.width/2 - 50;
    }

    public void resetBricks() {
        for(Brick[] Row : bricks) {
            for(Brick brick : Row) {
                brick.isRendered = true;
            }   
        }
    }
    
    public void resetStats() {
    // Resets the stats
    lives = 3;
    score = 0;
    isGameOver = false;
    }
    
    public void handleEvent(KeyEvent e) {
        // Handles the keyboard events
        double shift = 15;
        Dimension d = getSize();
        int k = e.getKeyCode();
        if((k == KeyEvent.VK_LEFT) || (k == KeyEvent.VK_KP_LEFT) || k == KeyEvent.VK_A ) {
            // If a left arrow key
            if(player.x > 0) {
            player.x -= shift; // Move left
            }
        }
        if((k == KeyEvent.VK_RIGHT) || (k == KeyEvent.VK_KP_RIGHT)|| k== KeyEvent.VK_D){
            if(player.x < (d.width - player.width - shift)){
                if(player.x < (d.width - player.width)) {
                    player.x += shift;  // Move right
                }
            }
        }
    }

    public void initscene() {
        double x0 = 0, y0 = 0;
        Dimension d = getSize();
        ball  = new Ball(d.width/2, d.height - 60, 4.0, -4.0, 10.0);
        player = new Player(d.width/2 - 50, d.height - 50, 100, 10);    
        bricks = new Brick[7][];
        bricks[0] = new Brick[2]; // First row
        bricks[1] = new Brick[4]; // Second row
        bricks[2] = new Brick[6]; // Third row
        bricks[3] = new Brick[8];
        bricks[4] = new Brick[6];
        bricks[5] = new Brick[4];
        bricks[6] = new Brick[2];
        double xs = x0 + (3.0 * Brick.width);
        double ys = y0;
        for(int i=0; i<2; ++i) {  // Layout first row
            bricks[0][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0 + (2.0 * Brick.width);
        ys = y0 + Brick.height;
        for(int i=0; i<4; ++i) { // Layout second row
            bricks[1][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0 + Brick.width;
        ys = y0 + (2.0 * Brick.height);
        for(int i=0; i<6; ++i) { // Layout third row
            bricks[2][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0;
        ys = y0 + (3.0 * Brick.height);
        for(int i=0; i<8; ++i) { // Layout third row
            bricks[3][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0 + Brick.width;
        ys = y0 + (4.0 * Brick.height);
        for(int i=0; i<6; ++i) { // Layout third row
            bricks[4][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0 + (2.0 * Brick.width);
        ys = y0 + (5.0 * Brick.height);
        for(int i=0; i<4; ++i) {  // Layout first row
            bricks[5][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        xs = x0 + (3.0 * Brick.width);
        ys = y0 + (6.0 * Brick.height);
        for(int i=0; i<2; ++i) {  // Layout first row
            bricks[6][i] = new Brick(xs, ys);
            xs += Brick.width;
        }
        
        f1 = new Font("Algerian", Font.BOLD, 32);
        f2 = new Font("Algerian", Font.BOLD, 20);
    }

    private void manageScores() { // Manages the top scores
        int[] a  = new int[3];
        a[0] = prefs.getInt("#1", 0);
        a[1] = prefs.getInt("#2", 0);
        a[2] = prefs.getInt("#3", 0);
        a[2] = prefs.getInt("#3", 0);
        if(score > a[0]) { // Sort them
            prefs.putInt("#1", score);
            prefs.putInt("#2", a[0]);
            prefs.putInt("#3", a[1]);
        }else if(score > a[1]) {
            prefs.putInt("#2", score);
            prefs.putInt("#3", a[1]);
        }else if(score > a[2]) {
            prefs.putInt("#3", score);
        }
    }
    
    public void resetScores() { // Resets the top scores
        prefs.putInt("#1", 0);
        prefs.putInt("#2", 0);
        prefs.putInt("#3", 0);
        prefs.putInt("#4", 0);
        prefs.putInt("#5", 0);
    }

    public void play() throws Exception{
        // The game play
        Dimension d = getSize();
        if(ball.x < 0) {
            // Ball hits the left wall
            ball.x = 0;
            ball.dx = -ball.dx;
        }
        if(ball.y < 0) {
            // Ball hits the upper wall
            ball.y = 0;
            ball.dy = -ball.dy;
        }
        if((ball.x + ball.radius) >= d.width) {
            // Ball hits the right wall
            ball.x = d.width - ball.radius;
            ball.dx = -ball.dx;
        }
        if((ball.y + ball.radius) >= d.height) {
            // Ball hits the bottom wall
            lives -= 1; // decrement the number of lives
            if(lives > 0) {
            resetPlayer(); // Reset and resume if the player still has lives
            }else {
            // Declare the game to be over
            isGameOver = true;
            BallBreaker.tmr.stop();
            manageScores();
            }       
        }
        checkCollisionWithPlayer(); // Check collision with the player
        if(ball.y < 200) { // If the ball is near the bricks
            for(Brick[] Row : bricks) {
                for(Brick brick : Row) {
                    checkCollisionWithBrick(brick); // Check for collison with each of the bricks
                }
            }
        }
        // Move the ball
        ball.x += ball.dx;
        ball.y += ball.dy;
    }
    private boolean checkCollisionWithPlayer() { // checking and handling collision of ball with Player
        if(e.intersects(c)) { // If the ball collides
            ball.dy = - Math.abs(ball.dy); // Bounce it
            // Calculate impact distance (The distance from the point of impact to the center of the player block )
            impact_distance = Point2D.distance(e.getCenterX(), e.getCenterY(), c.getCenterX(), c.getCenterY());
            if(impact_distance >= 30.0) { // If Impact distance is > 30 that is
            ball.dx = -ball.dx;       // if the ball has hit the player at the edges reverse the direction  
            }
            ball.x += ball.dx;
            ball.y += ball.dy;
            return true;
        }
        return false;
    }
    private void checkCollisionWithBrick(Brick b) throws Exception { // Checks collisons with brick
        if(b.isRendered) { // If the brick was rendered on the screen
            if(e.intersects(b.br)) { // And if collided
                try {
                    if(BallBreaker.isSoundOn) {
                        BallBreaker.playClip();
                    }
                }  
                catch(Exception ex) {
                    BallBreaker.isSoundOn = false;
                }
                ++score; // Increment score
                if(score == 32) { // If score is 50 end the game
                    isGameOver = true;
                    BallBreaker.tmr.stop();
                    manageScores();
                }
                b.isRendered = false; // The brick should disappear
                // Determine on which of the brick has the impact occured
                int k = b.br.outcode(e.getCenterX(), e.getCenterY());
                if((k == Rectangle2D.OUT_LEFT) || (k == Rectangle2D.OUT_RIGHT)) { // Left or right side
                    ball.dx = -ball.dx;
                }else { // Top of bottom side of the brick
                    ball.dy = -ball.dy;
                }
            }
        }
    }
}
