import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int height=400;
    int width=400;
    int dots;
    int dotSize=10;
    int allDots=40*40;
    int DELAY=140;

    int x[]=new int[allDots];
    int y[]=new int[allDots];

    int apple_x;
    int apple_y;


    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;

   boolean inGame=true;
    Timer timer;

    Image head;
    Image apple;
    Image bodyDot;
    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(height,width));
        initGame();
        Loadimage();


    }

    public void initGame()
    {
        locateApple();
        dots=3;
        for(int z=0;z<dots;z++)
        {
            y[z]=150;
            x[z]=150+(z*dotSize);
           // System.out.println(x[z]+" "+y[z]);
        }

        timer=new Timer(DELAY,  this);
        timer.start();
    }
    //load images
    public void Loadimage(){
        ImageIcon a=new ImageIcon("src/Resources/head.png");
        head=a.getImage();

        ImageIcon b=new ImageIcon("src/Resources/dot.png");
        bodyDot=b.getImage();

        ImageIcon c=new ImageIcon("src/Resources/apple.png");
        apple=c.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    public void doDrawing(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple,apple_x,apple_y,this);
            g.drawImage(head,x[0],y[0],this);
            for(int z=1;z<dots;z++)
            {
                // System.out.println(x[z]+" "+y[z]);
                g.drawImage(bodyDot,x[z],y[z],this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameOver(g);
        }

    }
    public void gameOver(Graphics g){
        String msg="Game Over";
        Font font=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/2);
    }
    public void checkCollision(){
        for(int z=1;z<dots;z++)
        {
            if(z>3&&(x[0]==x[z])&&(y[0]==y[z]))
                inGame=false;
        }
        if(x[0]<0)
        {
            inGame=false;
        }
        if(x[0]>=width)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(y[0]>=height)
        {
            inGame=false;
        }
        if(!inGame)
        {
            timer.stop();
        }
    }
    public void checkApple(){
        if(x[0]==apple_x&&y[0]==apple_y)
        {
            dots++;
            locateApple();
        }
    }
    public void locateApple()
    {
        int x=(int)(Math.random()*39);
        apple_x=x*10;
         int y=(int)(Math.random()*39);
        apple_y=y*10;
    }

    public void move()
    {
        //we should move all other dots to its just before dot's position
        for(int z=dots-1;z>0;z--)
        {
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftDirection)
        {
            x[0]-=dotSize;
        }
        if(rightDirection)
        {
            x[0]+=dotSize;
        }
        if(upDirection)
        {
            y[0]-=dotSize;
        }
        if(downDirection)
        {
            y[0]+=dotSize;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightDirection)
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection)
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && !downDirection)
            {
                upDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection)
            {
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }

        }


    }
}
