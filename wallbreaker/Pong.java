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
/**
 * Write a description of class Pong here.
 * semua instruksi didalam game ada di dalam class ini. Class ini berfungsi untuk memanggil class yang lain dan menghubungkan semua class sehingga menjadikan sebuah game pong dengan algoritma-algoritma yang membuat game ini tidak monotn tetapi bisa menghasilkan game yang mengasikkan dan bisa dimainkan secara berdua. Dalam Satu keyboard diset Player 1 pergerakan menggunakan W untuk bergerak keatas , dan S bergerak kebawah. Serta untuk Player 2 menggunakan panah Atas dan panah bawah di keyboard.
 * @author Fitrah Arie Ramadhan
 * @version Final Version, 19 Desember 2020
 */

public class Pong implements ActionListener, KeyListener
{
    public static Pong pong;
    public int width = 1080, height = 720;
    public Tgambar tgambar;
    public boolean bot = false, selectingDifficulty;
    public boolean w, s, up, down;
    public int gameStatus = 0, scoreLimit = 7, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    public int botDifficulty, botMoves, botCooldown = 0;
    public Random random;
    public JFrame jframe;
    public Pong()
    {
        Timer timer = new Timer(20, this);
        random = new Random();
        jframe = new JFrame("SELAMAT DATANG DIGAME");
        tgambar = new Tgambar();
        jframe.setSize(width + 15, height + 35);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(tgambar);
        jframe.addKeyListener(this);
        timer.start();
    }
    public void start()
    {
        gameStatus = 2;
    }
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (gameStatus == 0)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("SELAMAT DATANG DIGAME", width / 5, 50);
            if (!selectingDifficulty)
            {
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
                g.drawString("Press Shift to Play with Bot", width / 2 - 200, height / 2 + 25);
                g.drawString("<< Score Limit: " + scoreLimit + " >>", width / 2 - 150, height / 2 + 75);
                g.drawString("Keterangan : ", width / 2 - 150, height / 2 + 125);
                g.drawString("< = Mengurangi Poin", width / 2 - 150, height / 2 + 150);
                g.drawString("> = Menambah Poin", width / 2 - 150, height / 2 + 200);
                g.drawString("Space = Bermain", width / 2 - 150, height / 2 + 250);
                g.drawString("Shift = Bermain dengan bot", width / 2 - 150, height / 2 + 300);
            }
        }
        if (selectingDifficulty)
        {
            String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard");
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("<< Bot Difficulty: " + string + " >>", width / 2 - 180, height / 2 - 25);
            g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 25);
        }
        if (gameStatus == 1)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("ALGERIAN", 1, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
        }
        if (gameStatus == 1 || gameStatus == 2)
        {
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(5f));
            g.drawLine(width / 2, 0, width / 2, height);
            g.setStroke(new BasicStroke(2f));
            g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);
            g.setFont(new Font("Arial", 1, 50));
        }
        if (gameStatus == 3)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("ARIAL", 1, 50));
            g.drawString("TERIMAKASIH TELAH BERMAIN", width / 6  , 50);
            if (bot && playerWon == 2)
            {
                g.drawString("The Bot Wins!", width / 2 - 170, 200);
            }
            else
            {
                g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
            }
 
            g.setFont(new Font("Serif", 1, 30));
 
            g.drawString("Press Space to Play Again", width / 2 - 165, height / 2 - 25);
            g.drawString("Press ESC for Menu", width / 2 - 135, height / 2 + 25);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (gameStatus == 2)
        {
        }
        tgambar.repaint();
    }
    public static void main(String[] args)
    {
        pong = new Pong();
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W)
        {
            w = true;
        }
        else if (id == KeyEvent.VK_S)
        {
            s = true;
        }
        else if (id == KeyEvent.VK_UP)
        {
            up = true;
        }
        else if (id == KeyEvent.VK_DOWN)
        {
            down = true;
        }
        else if (id == KeyEvent.VK_RIGHT)
        {
            if (selectingDifficulty)
            {
                if (botDifficulty < 2)
                {
                    botDifficulty++;
                }
                else
                {
                    botDifficulty = 0;
                }
            }
            else if (gameStatus == 0)
            {
                scoreLimit++;
            }
        }
        else if (id == KeyEvent.VK_LEFT)
        {
            if (selectingDifficulty)
            {
                if (botDifficulty > 0)
                {
                    botDifficulty--;
                }
                else
                {
                    botDifficulty = 2;
                }
            }
            else if (gameStatus == 0 && scoreLimit > 1)
            {
                scoreLimit--;
            }
        }
        else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3))
        {
            gameStatus = 0;
        }
        else if (id == KeyEvent.VK_SHIFT && gameStatus == 0)
        {
            bot = true;
            selectingDifficulty = true;
        }
        else if (id == KeyEvent.VK_SPACE)
        {
            if (gameStatus == 0 || gameStatus == 3)
            {
                if (!selectingDifficulty)
                {
                    bot = false;
                }
                else
                {
                    selectingDifficulty = false;
                }
                start();
            }
            else if (gameStatus == 1)
            {
                gameStatus = 2;
            }
            else if (gameStatus == 2)
            {
                gameStatus = 1;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        int id = e.getKeyCode();
 
        if (id == KeyEvent.VK_W)
        {
            w = false;
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
    @Override
    public void keyTyped(KeyEvent e)
    {
 
    }
}
