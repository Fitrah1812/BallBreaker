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
/**
 * Write a description of class KeyMon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
class KeyMon implements KeyListener {
    public boolean w1, s, up, down;
    // Monitors keyboard events
    World wad = new World();
    public KeyMon(World w) {
	this.w = w;
    }
    public void keyPressed(KeyEvent e) {
	if(!w.isGameOver) 
	{ // If the game is running
	    if(e.getKeyCode() == KeyEvent.VK_SPACE) 
	    { // If SPACE is pressed
		if(BallBreaker.tmr.isRunning()) 
		{ 
		    BallBreaker.tmr.stop();
		} 
		else 
		{
		    BallBreaker.tmr.start(); // If not resume it
		}
		return;
	    }
	    else if(e.getKeyCode() == KeyEvent.VK_Q)
	    {
	        w.resetPlayer(); 
		w.resetBricks(); 
		w.resetStats();
		BallBreaker.tmr.start(); 
	    }
	}else{ 
	    if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		w.resetPlayer(); 
		w.resetBricks(); 
		w.resetStats();  
		BallBreaker.tmr.start(); 
	    }
	    if(e.getKeyCode() == KeyEvent.VK_R) {
		w.resetScores();
		w.repaint();
	    }
	}
	if(BallBreaker.tmr.isRunning()) { // If the game is running
	    w.handleEvent(e); // Handle the "Left" and "Right" arrow keys
	    if(e.getKeyCode() == KeyEvent.VK_A){
	       
	    }
	    else if(e.getKeyCode() == KeyEvent.VK_D){
	        
	    }
	    
	}
    }
    public void keyTyped(KeyEvent e) {
	
    }
    public void keyReleased(KeyEvent e) {
	int id = e.getKeyCode();
        if (id == KeyEvent.VK_W)
        {
            w1 = false;
        }
        else if (id == KeyEvent.VK_S)
        {
            s = false;
        }
        else if (id == KeyEvent.VK_UP)
        {
            up = false;
        }
        else if (id == KeyEvent.VK_DOWN)
        {
            down = false;
        }
    }
    public World w;
}