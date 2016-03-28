/*
Brandon Arnold
Max Conroy
CET 350
Group 1
arn4181@calu.edu
con3644@calu.edu
*/

//Bouncing ball applet: rectangle is created and moves around the window, bouncing off of the edges. 
//User can change the ball's speed and size, as well as convert it to an oval. 

import java.applet.*;                                                                   
import java.awt.*;                                                                     
import java.awt.event.*;                                                                 
import java.io.*;                                                                       

public class Bounce extends Applet implements ActionListener, AdjustmentListener
{
   private static final long versionUID = 10L;
   
   private final int BUTTONHEIGHT = 18; 
   private final int BUTTONWIDTH = 48; 
   private static int WIDTH = 650;
   private static int HEIGHT = 440;
   private static int WidthObj = 20;    
   private static int HeightObj = 20;  
   private static int NWidthObj=WidthObj;
   private static int NHeightObj=HeightObj;
   private final int SLIDERHEIGHT = 18;
   private final int LABELWIDTH = 45;  
   private final int SLIDERWIDTH = 90;  
   private final int LABELHEIGHT = 14;  
   private final int SPEEDMAX=35;       
   private final int SPEEDMIN=1;
   private final int MAXObj = 225;      
   private final int MINObj = 5;        
   private final int SPEED = 1;
   private final int INCREMENT = 20;
   Scrollbar SpeedSlider, SizeSlider;
   
   private Ball b;
   private Graphics page;
   int INITWH = 45;  
   int BALLH = 45;   
   int BALLW= 45;       
   int PAUSE = 14;    
   boolean shap = false;
   boolean paus = false;
   int temp;
   int delay;
   
   Button start, shape, clear, tail, quit;
   Label speedLabel = new Label("Speed");
   Label sizeLabel = new Label("Size");
   int bx, by, bw, bh;
   boolean tl = false;
   boolean more, done;

   //main
   public void init()
   {
        //set page size
       setSize(WIDTH + 1, HEIGHT + 1 + BUTTONHEIGHT + 10);
       page = getGraphics();                                                                                            //create page
       setVisible(true);                                                        
       setBackground(Color.white);                                              
       setLayout(null);

//all button.addActionListener(this) are added to wait for event                                             

        //create shape button
       shape = new Button("Oval");
       shape.setSize(BUTTONWIDTH + 30, BUTTONHEIGHT);
       shape.setLocation( 275, HEIGHT + 1);
       add("Center", shape);
       shape.addActionListener(this);                                              

        //create clear button
       clear = new Button("Clear");
       clear.setSize(BUTTONWIDTH, BUTTONHEIGHT);
       clear.setLocation( 357, HEIGHT + 1);
       add("Center", clear);
       clear.addActionListener(this);                                              
       
        //create tail button
       tail = new Button("Tail");
       tail.setSize(BUTTONWIDTH, BUTTONHEIGHT);
       tail.setLocation( 223, HEIGHT + 1);
       add("Center", tail);
       tail.addActionListener(this);      

        //create quit button
       quit = new Button("Quit");
       quit.setSize(BUTTONWIDTH, BUTTONHEIGHT);
       quit.setLocation( 409, HEIGHT + 1);
       add("Center", quit);
       quit.addActionListener(this);    
       
       //create stop button
       start = new Button("Stop");                                                   
       start.setSize(BUTTONWIDTH, BUTTONHEIGHT);
       start.setLocation( 171, HEIGHT + 1);
       add("Center", start);
       start.addActionListener(this);                                            

        //create scrollbar for object speed
       SpeedSlider = new Scrollbar(Scrollbar.HORIZONTAL);
       SpeedSlider.setMaximum(SPEEDMAX);
       SpeedSlider.setMinimum(SPEEDMIN);
       SpeedSlider.setUnitIncrement(SPEED);
       SpeedSlider.setBlockIncrement(SPEED*2);
       SpeedSlider.setValue(PAUSE);
       SpeedSlider.setBackground(Color.gray);
       SpeedSlider.setLocation(56,HEIGHT + 1 );
       SpeedSlider.setSize(SLIDERWIDTH+10,SLIDERHEIGHT);
       this.add(SpeedSlider);
       SpeedSlider.addAdjustmentListener(this);                                      

       //create speedlabel
       speedLabel.setSize(LABELWIDTH, LABELHEIGHT);
       speedLabel.setLocation(89 , HEIGHT+21);
       add("Center", speedLabel);
       this.add(speedLabel);

       //creating the scrollbar for size
       SizeSlider = new Scrollbar(Scrollbar.HORIZONTAL);
       SizeSlider.setMaximum(MAXObj);
       SizeSlider.setMinimum(MINObj);
       SizeSlider.setUnitIncrement(INCREMENT);
       SizeSlider.setBlockIncrement(INCREMENT*2);
       SizeSlider.setValue(INITWH);
       SizeSlider.setBackground(Color.gray);
       SizeSlider.setLocation(464,HEIGHT + 1 );
       SizeSlider.setSize(SLIDERWIDTH+10,SLIDERHEIGHT);
       this.add(SizeSlider);
       SizeSlider.addAdjustmentListener(this);                                           

       //create sizeLabel
       sizeLabel.setSize(LABELWIDTH, LABELHEIGHT);
       sizeLabel.setLocation(510 , HEIGHT+21);
       add("Center", sizeLabel);
       this.add(sizeLabel);

       paint(page);

    //create ball object
       b= new Ball(page, BALLW, BALLH, WIDTH, HEIGHT, SPEEDMAX, SPEEDMIN, PAUSE, SPEED); 

   }  //end main

   //drawing the page
   public void paint(Graphics page)
   {
       page.drawRect(1, 1, BALLW, BALLH);
       page.setColor(Color.black);
       page.fillRect(1, 1, BALLW, BALLH);
   }

   //action performed
   public void actionPerformed(ActionEvent e)
   {
       //Quit button: if quit button is pressed ball is destroyed
       Object s= e.getSource();
       if (s == quit) {
           stop();
           destroy();
       }
       //shape button: if pressed shape will change to oval/rectangle
       if (s == shape) {
           if (shap == false) {
               shap = true;
               shape.setLabel("Rectangle");
               b.rectangle(shap);
           }
           else {
               shap = false;
               shape.setLabel("Oval");
               b.rectangle(shap);
           }
       }
       
             //Start button: if pressed the ball will pause/start
       if (s == start) {
           if (paus == false) {
               paus = true;
               start.setLabel("Start");
               b.stopit(paus);
           }
           else {
               paus = false;
               start.setLabel("Stop");
               b.startit(paus);
           }
       }


       if (s == quit) {
           b.quitit();
       }

       //tail button: if pressed the a tail of ball will be created/stop
       if(s == tail) {
           if (tl == false) {
               tl = true;
               tail.setLabel("No Tail");
               b.Tail(tl);
           }
           else {
               tl = false;
               tail.setLabel("Tail");
               b.Tail(tl);
           }
       }

       //clear button: if pressed the tails of ball will clear
       if (s == clear) {
           b.ClearObj();
       }

   }

   public void destroy(){}
   public void start() {
       b.run();
   }
   public void stop() {
   }

   //determine which scrollbar is used and adjust accordingly
   public void adjustmentValueChanged(AdjustmentEvent e) {
       if (e.getSource() == SpeedSlider)                                                   
           b.speed(e.getValue());
       else {                                                                         
           temp = b.shape_size(e.getValue());
           SizeSlider.setValue(temp);
       }
   }

   //ball class
   public class Ball extends Thread
   {

       //initialization of flags and several variables used for ball object
       private static final long versionUID = 11L;
       Graphics p;
       int WB, HB, W, H, MS, SP, P, S;
       int PWB, PHB;
       int BX = 1;
       int BY = 1;
       int dx = -1;
       int dy = -1;
       int ballH;
       int screenW, screenH;
       long SPEEDMAX, SPEEDMIN;
       int pause;
       boolean paus;
       boolean shap;
       long currentspeed;
       boolean done = false;
       int time_delay=10;
       boolean tail;

       //places ball at location
       public Ball(Graphics p, int WB, int HB, int W, int H, int MS, int SP, int P, int S)
       {
           this.p = p;
           this.WB = WB;
           this.HB = HB;
           this.W = W;
           this.H =H;
           this.MS = MS;
           this.SP = SP;
           this.P = P;
           this.S = S;
       }

       public void stopit(boolean paus)
       {
           this.paus = paus;
       }

       public void startit(boolean paus)
       {
           this.paus = paus;
       }

       //speed adjustment
       public void speed(int delay){
           this.time_delay = delay;
       }

       //quit adjustment
       public void quitit()
       {
           done = true;
       }

       //rectangle value changed
       public void rectangle(boolean r)
       {
           this.shap = r;
       }

       //size adjustment
       public int shape_size(int r)
       {
           this.PWB= WB;
           this.PHB = HB;
           this.WB = r;
           this.HB = r;
          
       //verify size fit screen
           if( BY+HB >= H)    {
               HB=(H-BY);
               WB=HB;
           }
      //verify size fit screen
           if( BX+WB >= W)    {
               WB=(W-BX);
               HB=WB;
           }
           return WB;
       }

       //tail value changed if utilized
       public void Tail(boolean t)
       {
           this.tail = t;
       }
       public void run()
       {
           drawObj();
           while (!done)
           {
               drawObj();
               try
               {
                   Thread.sleep(time_delay);
               }
               catch(InterruptedException ex) {}
               if (tail ==false) {
                   eraseObj();
               }
               if (paus == false) {
                   move();
               }
           }
       }

       //create ball
       public void drawObj()
       {
           if (shap == false) {                                                            
               //create a rectangle
               p.drawRect(BX, BY,WB,HB);
               p.setColor(Color.black);
               p.fillRect(BX, BY,WB,HB);
           }
           else {                                                                      
               //create an oval
               p.drawOval(BX, BY,WB,HB);
               p.setColor(Color.black);
               p.fillOval(BX, BY,WB,HB);
           }
       }

       //clears ball object
       public void ClearObj()
       {
           p.drawRect(0, 0,W,H);                                                          
           p.setColor(Color.white);  
            //overwrites screen tail
           p.fillRect(0, 0,W+10,H+10);                                                         
       }

       //erase object
       public void eraseObj()
       {
           p.drawRect(BX, BY,PWB,PHB);                                                          //creates a rectangle at the current location
           p.setColor(Color.white);                                                         
           p.fillRect(BX, BY,PWB+10,PHB+10);                                                  //Sets rectangle to white
           PWB = WB;
           PHB = HB;
       }

       
       public void move()
       {
           //increment X and Y values
           BX += dx;
           BY += dy;
           //if a side border is hit, change direction
           if ( (BX <= 0) || (BX+dx+ WB >= W) ) {
               dx= -dx;
           }
           //if a top or bot border is hit, change direction
           if ( (BY <= 0) || (BY+dy+ HB >= H) ) {
               dy = -dy;
           }
       }
   }  
}    

