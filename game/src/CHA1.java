import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class CHA1 extends JPanel {

    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private BG enemy;
    private BG enemy2;
    private BG enemy3;
    private BG enemy4;
    private BG enemy5;
    private fast ms;
    private goc gc;
    private boolean inGame = true;

    Image Pic;

    public CHA1() {

        initBoard();

    }



    private void initBoard() {

        ImageIcon obj = new ImageIcon("src/resources/Amo.png");
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
        enemy = new BG();
        enemy2 = new BG();
        enemy3 = new BG();
        enemy4 = new BG();
        enemy5 = new BG();
        ms = new fast();

        gc=new goc();


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


        g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(),
                enemy.getImageWidth(), enemy.getImageHeight(), this);

        g2d.drawImage(enemy2.getImage(), enemy2.getX(), enemy2.getY(),
                enemy2.getImageWidth(), enemy2.getImageHeight(), this);

        g2d.drawImage(enemy3.getImage(), enemy3.getX(), enemy3.getY(),
                enemy3.getImageWidth(), enemy3.getImageHeight(), this);

        g2d.drawImage(enemy4.getImage(), enemy4.getX(), enemy4.getY(),
                enemy4.getImageWidth(), enemy4.getImageHeight(), this);

        g2d.drawImage(enemy5.getImage(), enemy5.getX(), enemy5.getY(),
                enemy5.getImageWidth(), enemy5.getImageHeight(), this);

        if (!ms.isDestroyed()) {
        g2d.drawImage(ms.getImage(), ms.getX(), ms.getY(),
                ms.getImageWidth(), ms.getImageHeight(), this);}





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

        g2d.setColor(Color.RED);
        g2d.setFont(font);
        String message = "Game Over";
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
        enemy.move();
        enemy2.move();
        enemy3.move();
        enemy4.move();
        enemy5.move();
        ms.move();
        ball.move();
        paddle.move();
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
                stopGame();
                gc.cha2();

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
        if ((enemy.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=1;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=1;
            }
        }
        if ((enemy2.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=1;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=1;
            }
        }
        if ((ms.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;
            if (!ms.isDestroyed()) {
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
                ms.setDestroyed(true);
            }
        }
        if ((enemy3.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=1;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=1;
            }
        }
        if ((enemy4.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=1;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=1;
            }
        }
        if ((enemy5.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= first && ballLPos < second) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= second && ballLPos < third) {

                HUD.HEALTH-=1;
            }

            if (ballLPos >= third && ballLPos < fourth) {

                HUD.HEALTH-=1;
            }

            if (ballLPos > fourth) {

                HUD.HEALTH-=1;
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
