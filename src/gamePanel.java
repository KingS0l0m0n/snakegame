import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH= 600;
    static final int SCREEN_Height= 600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_Height )/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] =new int [GAME_UNITS];
    final int y[] =new int [GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running =false;
    Timer timer;
    Random random;


    gamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_Height));
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        StartGame();
    }
    public void StartGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if (running) {
            for (int i = 0; i < SCREEN_Height / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_Height);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.pink);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(252, 0, 218));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.ITALIC, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, SCREEN_Height/2);



        }else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int) SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt((int) SCREEN_Height/UNIT_SIZE)*UNIT_SIZE;

    }
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0]== appleX)&& y[0] == appleY) {
        bodyParts++;
        applesEaten++;
        newApple();
        }
    }
    public void checkCollision(){
        //this checks if head touches  body
        for(int i = bodyParts;i>0;i--){
            if((x[0] == x[i]) &&(y[0]== y[i])){
                running =false;
            }
        }
        //checks if head touches the west border
        if(x[0] < 0){
            running =false;
        }
        //checks if head touches the east border
        if(x[0] > SCREEN_WIDTH){
            running =false;
        }
        //checks if head touches north border
        if(y[0] < 0){
            running =false;
        }
        //checks if head touches south border
        if(y[0] > SCREEN_Height){
            running =false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.ITALIC, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, SCREEN_Height/2);
        //gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.ITALIC, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over Noob",(SCREEN_WIDTH - metrics2.stringWidth("Game Over Noob"))/2, SCREEN_Height/2);
    }

                               @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public  void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!= 'D'){
                        direction ='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!= 'U'){
                        direction ='D';
                    }
                    break;
            }


            }
        }
    }

