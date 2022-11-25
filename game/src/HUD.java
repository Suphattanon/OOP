import java.awt.*;


public class HUD {
    public static int HEALTH=100;

    public static void render(Graphics g2d){
        g2d.setColor(Color.GRAY);
        g2d.fillRect(15,15,100,16);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(15,15,HEALTH,16);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(15,15,100,16);
        g2d.drawString("BREAKWHERE",130,25);
        if(HEALTH>100)
            HEALTH=100;
    }
}
