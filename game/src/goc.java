import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class goc extends JFrame {
    public void gui(){
        add(new CHA1());
        setTitle("CHAPTER 1");


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        pack();
    }
    public void cha2(){
        add(new CHA2());
        setTitle("CHAPTER 2");


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        pack();
    }
    public void cha4(){
        add(new Cha3());
        setTitle("CHAPTER 3");


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        pack();
    }
    public void cha3(){
        add(new BossF());
        setTitle("CHAPTER 3");


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        pack();
    }
}
