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

public class BallBreaker {
    private int height, width;
    private static File soundFile = new File("Gud.wav");
    public static boolean isSoundOn = true;
    public static void main(String[] args) {
        JFrame F = new JFrame("Penghancur Semesta");
        //JFrame F2 = new JFrame("Penghancur Semesta");
        final World w = new World();
        F.add(w);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        F.setSize(800,600);
        F.setResizable(false); // Frame not resizable
        F.setLocationByPlatform(true); // Allow the platform to position the frame
        KeyMon k = new KeyMon(w);
        F.addKeyListener(k);
        F.setVisible(true);
        w.initscene();
        Action playAndUpdateAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                w.play();
                }catch(Exception ex) {
                ex.printStackTrace();
                }
                w.repaint();        
            }
        };
        tmr = new Timer(20, playAndUpdateAction); 
        tmr.start(); 
        }
        public static Timer tmr; 
        public static void playClip() //Plays the sound clip
        throws IOException, UnsupportedAudioFileException,
               LineUnavailableException {
                   AudioInputStream auIn = null;
                   Clip clip = null;
        try {
            auIn = AudioSystem.getAudioInputStream(soundFile); 
            clip = AudioSystem.getClip();                      
            clip.open(auIn);
            clip.start();   
        } finally {
            if(auIn != null) {
            auIn.close();
            }
        }
    }
}