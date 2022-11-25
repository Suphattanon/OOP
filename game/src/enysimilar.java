import javax.swing.*;
import java.util.Random;

public class enysimilar extends Sprite  {

    private int xdir;
    private int ydir;
    private Random r;

    public enysimilar() {

        initEny();
    }

    private void initEny() {

        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/ball1.png");
        image = ii.getImage();
    }

    void move() {

        x += xdir;
        y += ydir;

        if(y<=0||y>=Commons.HEIGHT) ydir *=-1;
        if(x<=0||x>=Commons.WIDTH) xdir *=-1;
    }

    private void resetState() {
        r= new Random();

        x = r.nextInt(Commons.INIT_BALL_Xr);
        y = r.nextInt(Commons.INIT_BALL_Yr);
    }


}


