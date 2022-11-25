import javax.swing.*;

public class heal extends Sprite  {

    private int xdir;
    private int ydir;
    private boolean destroyed;

    public heal() {

        initEny();
    }

    private void initEny() {

        xdir = 1;
        ydir = -1;
        destroyed = false;
        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/heal2.png");
        image = ii.getImage();
    }

    void move() {

        x += xdir;
        y += ydir;


        if(x<=0||x>=Commons.WIDTH) xdir *=-1;
    }

    private void resetState() {


        x = Commons.INIT_BALL_Xhea;
        y = Commons.INIT_BALL_Yhea;
    }
    boolean isDestroyed() {

        return destroyed;
    }

    void setDestroyed(boolean val) {

        destroyed = val;
    }



}

