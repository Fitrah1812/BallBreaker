import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 * Write a description of class Tgambar here.
 * Class ini berfungsi untuk rendering gambar game
 * @author Fitrah Arie Ramadhan
 * @version Final Version, 19 Desember 2020
 */
public class Tgambar extends JPanel
{
    private static final long serialVersionUID = 1L;
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Pong.pong.render((Graphics2D) g);
    }
}
