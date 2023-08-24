import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Gamepanel extends JPanel implements Runnable, KeyListener, ActionListener {
    private  static final long serialVersionUID =1L;
    public static final int WIDTH =500, HEIGHT = 500;
    private Thread thread;
    private Timer timer;
    private boolean running;
    private boolean right =true, left =false, up =false, down=false;
    private bodypart b;
    private ArrayList<bodypart> snake;
    private fruit f;
    private ArrayList<fruit> fruits;
    private Random r;
    private int xCoor =  10, yCoor =10 ,size =5;
    private int ticks =0;
    private int count =0;
    int lane;

    public Gamepanel(){
        timer= new Timer(10,this);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        snake = new ArrayList<bodypart>();//array holding body part

        fruits = new ArrayList<fruit>();//array holding fruit
        r = new Random();
        start();
    }/**
     loadpic method would be created to load background information has
     animation then movieloop would also be created
     movieloop and loadpic method would be put
     start method
     */
    public void start(){
        running = true;
       thread = new Thread(this);
       thread.start();
        //timer.start();

    }
    public void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //timer.stop();
    }
    public void tick(){
        /*
         * movement was setup then fruit then what happens at collision*/
            if (snake.size() == 0) {//setting up body coordinate
                b = new bodypart(xCoor, yCoor, 5);
                snake.add(b);//adds body part to arraylist
            }
            ticks++;//direction of movement increasing

            if (ticks > 250000) {//direction of movement setup
                /*whenever there is a change in direction, the direction resets to defaults*/
                if (right) xCoor++;
                if (left) xCoor--;
                if (up) yCoor--;
                if (down) yCoor++;

                ticks = 0;//default direction
                b = new bodypart(xCoor, yCoor, 5);
                snake.add(b);//adds new direction bodypart to the arraylist
                if (snake.size() > size) {
                    /* while movement is increasing the first value of the arraylist is removed that is line 50*/
                    snake.remove(0);
                }
            }
            //setting up fruit coordinate
            if (fruits.size() == 0) {
                int xCoor = r.nextInt(49);
                int yCoor = r.nextInt(49);
                f = new fruit(xCoor, yCoor, 5);
                fruits.add(f);
            }
            //action to determine what happens when the snake comes in contact with the fruit
            for (int i = 0; i < fruits.size(); i++) {
                /*if the position of the snake and the fruits are true the fruit removes*/
                if (xCoor == fruits.get(i).getxCoor() && yCoor == fruits.get(i).getyCoor()) {
                    fruits.remove(i);
                    i++;
                    size++;//size of snake increasing
                    count++;//counting number of fruits eaten
                    System.out.println(count * 2);
                    //d.label.setText("" +count*2);
                }
            }
            //bodypart collision
            for (int i = 0; i < snake.size(); i++) {
                if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
                    if (i != snake.size() - 1) {
                        // System.out.println("\nGame over");
                        // System.out.print("\n your score is : " + count*2);
                        JOptionPane.showConfirmDialog(null, "Game Over Your Score is: " + count * 2, "Snake Mania", JOptionPane.DEFAULT_OPTION);
                        int lane = JOptionPane.showConfirmDialog(null, "Do you want to retry", "Snake Mania", JOptionPane.YES_NO_OPTION);
                        if (lane == JOptionPane.YES_OPTION){
                            ticks=0;
                            start();
                        }else {
                            stop();
                        }
                    }
                }
            }
            // border collision
            if (xCoor < 0 || xCoor > 49 && yCoor < 0 || yCoor > 49) {
                //System.out.println("\nGame Over");
                //System.out.print("\n your score is : " + count*2);
                JOptionPane.showConfirmDialog(null, "Game Over Your Score is: " + count * 2, "Snake Mania", JOptionPane.DEFAULT_OPTION);
                int lane =JOptionPane.showConfirmDialog(null, "Do you want to retry", "Snake Mania", JOptionPane.YES_NO_OPTION);
                if (lane == JOptionPane.YES_OPTION){
                    ticks=0;
                    start();
                }else {
                    stop();
                }
            }
    }

    public  void paint (Graphics g){
        g.clearRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.yellow);
        g.fillRect(0,0,WIDTH,HEIGHT);

        for (int i =0; i<WIDTH/10; i++){
            /*sets the horizontal border*/
            g.drawLine(i*10,0,i*10,HEIGHT);
        }
        for (int i = 0; i<HEIGHT/10; i++){
            /*sets the vertical border*/
            g.drawLine(0,i*10,HEIGHT,i*10);
        }
        for (int i = 0; i<snake.size(); i++){
            /*draws the image for snake in the array*/
            snake.get(i).draw(g);
        }
        for (int i =0; i<fruits.size(); i++){
            /*draws image for fruit in the array*/
            fruits.get(i).draw(g);
        }

    }
    @Override
    public void run() {
        while (running){
            repaint();
            tick();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key== KeyEvent.VK_RIGHT&&!left){
            right = true;
            up = false;
            down = false;
        }
        if (key== KeyEvent.VK_LEFT&&!right){
            left = true;
            up = false;
            down = false;
        }
        if (key== KeyEvent.VK_UP&&!down){
            up = true;
            left = false;
            right = false;
        }
        if (key== KeyEvent.VK_DOWN&&!up){
            down = true;
            left = false;
            right = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        while (true) {
            repaint();
            tick();
        }
    }
}
