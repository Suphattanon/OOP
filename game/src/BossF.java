import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class BossF extends JPanel {

    private Timer timer;
    private String message = "Game Over" ;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private BOSS bs1;
    private BOSS bs2;
    private heal he;
    private fast fa;

    private boolean inGame = true;

    Image Pic;

    public BossF() {

        initBoard();

    }



    private void initBoard() {

        ImageIcon obj = new ImageIcon("src/resources/bosbg.png");
        Pic=obj.getImage();


        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));



        gameInit();

    }

    private void gameInit() {


        bricks = new Brick[Commons.N_OF_BRICKS];

        ball = new Ball();
        paddle = new Paddle();
        he= new heal();

        fa= new fast();
        bs1=new BOSS();
        bs2 = new BOSS();


        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j <6; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }

        timer = new Timer(Commons.SPEED, new GameCycle());
        timer.start();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Pic,0,0,null);


        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {

            drawObjects(g2d);
            HUD.render(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }


    private void drawObjects(Graphics2D g2d) {


        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);


        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);



        if (!he.isDestroyed()) {
            g2d.drawImage(he.getImage(), he.getX(), he.getY(),
                    he.getImageWidth(), he.getImageHeight(), this);}

        if (!fa.isDestroyed()) {
            g2d.drawImage(fa.getImage(), fa.getX(), fa.getY(),
                    fa.getImageWidth(), fa.getImageHeight(), this);}


        g2d.drawImage(bs1.getImage(), bs1.getX(), bs1.getY(),
                bs1.getImageWidth(), bs1.getImageHeight(), this);

        g2d.drawImage(bs2.getImage(), bs2.getX(), bs2.getY(),
                bs2.getImageWidth(), bs2.getImageHeight(), this);





        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }


    private void gameFinished(Graphics2D g2d) {

        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.GREEN);
        g2d.setFont(font);
        g2d.drawString(message,
                (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            paddle.keyPressed(e);
        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {
        bs1.move();
        bs2.move();
        ball.move();
        paddle.move();
        he.move();

        fa.move();
        checkCollision();
        repaint();
    }

    private void stopGame() {

        inGame = false;
        timer.stop();
    }

    private void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {

            stopGame();
        }
        else if(HUD.HEALTH<=0){
            stopGame();
        }

        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {

                j++;
            }


            if (j == Commons.N_OF_BRICKS) {

                message = "YOU ARE HERO!!!";
                stopGame();

            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {

                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {

                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {

                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {

                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        if ((bs1.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=10;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=10;
            }
        }
        if ((bs2.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=10;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=10;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=10;
            }
        }
        if ((he.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;
            if (!he.isDestroyed()) {
                if (ballLPos < first) {

                    HUD.HEALTH += 50;

                }

                if (ballLPos >= first && ballLPos < second) {

                    HUD.HEALTH += 50;

                }

                if (ballLPos >= second && ballLPos < third) {

                    HUD.HEALTH += 50;

                }

                if (ballLPos >= third && ballLPos < fourth) {

                    HUD.HEALTH += 50;

                }

                if (ballLPos > fourth) {

                    HUD.HEALTH += 50;

                }
                he.setDestroyed(true);
            }

        }

        if ((fa.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;
            if (!fa.isDestroyed()) {
                if (ballLPos < first) {

                    paddle.p++;
                }

                if (ballLPos >= first && ballLPos < second) {

                    paddle.p++;
                }

                if (ballLPos >= second && ballLPos < third) {

                    paddle.p++;
                }

                if (ballLPos >= third && ballLPos < fourth) {

                    paddle.p++;
                }

                if (ballLPos > fourth) {

                    paddle.p++;
                }
                fa.setDestroyed(true);
            }
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRect().contains(pointRight)) {

                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {

                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {

                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {

                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
}
