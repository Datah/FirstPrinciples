
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  This file can be used to create very simple animations.  Just fill in
 *  the definition of drawFrame with the code to draw one frame of the 
 *  animation, and possibly change a few of the values in the rest of
 *  the program as noted below. Note that if you change the name of the
 *  class, you must also change the name in the main() routine!
 */
public class GraphicsTest extends JPanel implements ActionListener {

    /**
     * Draws one frame of an animation. This subroutine is called re
     * second and is responsible for redrawing the entire drawing area.  The
     * parameter g is used for drawing. The frameNumber starts at zero and
     * increases by 1 each time this subroutine is called.  The parameters width
     * and height give the size of the drawing area, in pixels.  
     * The sizes and positions of the rectangles that are drawn depend
     * on the frame number, giving the illusion of motion.
     */
    public void drawFrame(Graphics g, int frameNumber, int width, int height) {
        
        /* NOTE:  To get a different animation, just erase the contents of this 
         * subroutine and substitute your own.  If you don't fill the picture
         * with some other color, the background color will be white.  The sample
         * code here just shows the frame number.
         */
        
       int x1, y1, xd, yd;
       int color;
       for (y1 = 10, x1=10; y1 <=(height/2 - 10) && x1 <=(width/2 - 10); y1+=15, x1+=15){
           xd = x1+frameNumber%15;
           yd = y1 + frameNumber%15;
           color = (int)(3*Math.random()+1);
           switch (color){
               case 1: g.setColor(Color.RED);break;
               case 2: g.setColor(Color.GREEN);break;
               case 3: g.setColor(Color.BLUE);break;
           }
           g.drawRect(xd,yd,width-2*xd,height-2*yd);
       }
        
        g.drawString( "Frame number " + frameNumber, 40, 50 );

    }
    
    
    public void drawFrame2(Graphics g, int frameNumber, int width, int height) {
        
        /* NOTE:  To get a different animation, just erase the contents of this 
         * subroutine and substitute your own.  If you don't fill the picture
         * with some other color, the background color will be white.  The sample
         * code here just shows the frame number.
         */
        
       int x1, y1, w, h, c;
       c=0;
       x1=0;y1=0;
       for (int i=0;i<8;i++){
           x1+=25;
           y1=0;
           c++;
           for (int j=0;j<8;j++){
               y1+=25;
               c+=1;
               switch (c%2){
                   case 1: g.setColor(Color.BLACK);break;
                   case 0: g.setColor(Color.RED);break;
               }
               g.fillRect(x1, y1, 25, 25);   
           }
       }
        
        g.drawString( "Frame number " + frameNumber, 250, 250 );

    }
    
    public void drawFrame3(Graphics g, int frameNumber, int width, int height) {
        
        /* NOTE:  To get a different animation, just erase the contents of this 
         * subroutine and substitute your own.  If you don't fill the picture
         * with some other color, the background color will be white.  The sample
         * code here just shows the frame number.
         */
        
       g.drawRect(50, 50, width-100, 50);
       g.drawRect(50,100, width-100, 25);
       g.drawRect(100, 125, width-200, 75);
       
       int x1c,x2c,x3c;
       int animator1, animator2;
       animator1 = (frameNumber)%(2*(width-125));
       animator2 = (int)((Math.log(frameNumber)*frameNumber)%(2*(width-150)));
       x3c = (int)(((width-200)/2.0)*Math.sin(frameNumber/30.0)+width/2.0);
       
       if(animator1 < width-125){
           g.fillRect(50+animator1,50,25,50);
       }
       else{
           g.fillRect((width-75)-animator1%(width-125),50,25,50);
       }
       
       if(animator2 < width-150){
           g.fillRect(50+animator2,100,50,25);
       }
       else{
           
           g.fillRect((width-100)-animator2%(width-150),100,50,25);
       }
       
       g.drawLine(x3c,125, x3c, 200);

    }
    
    //------ Implementation details: DO NOT EXPECT TO UNDERSTAND THIS ------
    
    
    public static void main(String[] args) {
        
        /* NOTE:  The string in the following statement goes in the title bar
         * of the window.
         */
        JFrame window = new JFrame("Simple Animation");
        
        /*
         * NOTE: If you change the name of this class, you must change
         * the name of the class in the next line to match!
         */
        GraphicsTest drawingArea = new GraphicsTest();
        
        drawingArea.setBackground(Color.WHITE);
        window.setContentPane(drawingArea);

        /* NOTE:  In the next line, the numbers 600 and 450 give the
         * initial width and height of the drawing array.  You can change
         * these numbers to get a different size.
         */
        drawingArea.setPreferredSize(new Dimension(600,450));

        window.pack();
        window.setLocation(100,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        /*
         * Note:  In the following line, you can change true to
         * false.  This will prevent the user from resizing the window,
         * so you can be sure that the size of the drawing area will
         * not change.  It can be easier to draw the frames if you know
         * the size.
         */
        window.setResizable(true);
        
        /* NOTE:  In the next line, the number 20 gives the time between
         * calls to drawFrame().  The time is given in milliseconds, where
         * one second equals 1000 milliseconds.  You can increase this number
         * to get a slower animation.  You can decrease it somewhat to get a
         * faster animation, but the speed is limited by the time it takes
         * for the computer to draw each frame. 
         */
        Timer frameTimer = new Timer(10,drawingArea);

        window.setVisible(true);
        frameTimer.start();

    } // end main

    private int frameNum;
    
    public void actionPerformed(ActionEvent evt) {
        frameNum++;
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFrame3(g, frameNum, getWidth(), getHeight());
    }

}
