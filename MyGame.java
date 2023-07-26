// Ganpati Bappa Morya !!

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Image;
import java.awt.Panel;
import java.applet.Applet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



public class MyGame extends Applet implements  KeyListener, Runnable
{
	int ballposX = 200;
	int ballposY = 500;
	int X[] = new int[4];
	int Y[] = new int[4];
  	long start = 0, end = 0, elapsedTime = 0;
  	long timeRemain = 0;
  	int totalTime = 31; 

	
	int plateposX = 200-20;
	int plateposY = 500+30;
	int plateposXt = plateposX;
	int plateposYt = plateposY;
	int cf = 0;
	int bc = 0;
	int score1 = 0;
	int score2 = 0;
	int d = 0;
	int pass = 1;
	int ploose = 0;
	int pwin = 0;
	
	int platewidth = 70;
	int plateheight = 5;
	boolean play = false;
	boolean flagup = false;
	int flag = 0;
	int height = 0;
	int ballYdir = -5;
	int platemov[] = new int[4];
	Image Bg;
	boolean permision = true;
	boolean bonus = false;
	Thread t = null;
	
	
	Rectangle l1;
	Rectangle l2;
	Rectangle l3;
	Rectangle l4;
	Rectangle l5;
	
	public void init()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		Bg = getImage(getCodeBase(), "Picture4.jpg");
		Bg = Bg.getScaledInstance(700, 600, Image.SCALE_SMOOTH);

		for(int i=0;i<4;i++)
		{
			platemov[i] = 0;
		}
	
		
	}

	public void run()
	{
		for( ; ; )
		{
			try
			{
				paint(getGraphics());
				Thread.sleep(14);
				action();
				if(play==false)
				{
					break;
				}
			}
			catch(InterruptedException e)
			{
				System.out.println("e = "+e);
			}

		}
		paint(getGraphics());
	}
	
	public void paint(Graphics g)
	{

		g.drawImage(Bg, 0, 0, this);

		g.setColor(Color.red);
		g.setFont(new Font("serf", Font.BOLD, 20));
		g.drawString("Score = "+score1 , 100, 70); //str score1
	
		g.setColor(Color.red);
		g.setFont(new Font("serf", Font.BOLD, 20));
		g.drawString("Score = "+score2 , 500, 70); //str score2

		if(pass == 1)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 20));
			g.drawString("Turn = Player1", 100, 30); //str player1			
		}
		else if(pass == 2)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 20));
			g.drawString("Turn = Player2", 500, 30); //str player2			
		}

		end = System.nanoTime();
 		elapsedTime = end - start;
		elapsedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);   //timer counter
 		timeRemain = totalTime - elapsedTime;

 		g.setColor(Color.red);
 		if(play == true)
 		g.drawString("Time Remaining: "+ timeRemain, 470, 500); //str timer
		
		g.setColor(Color.white);
		g.fillRect(200-20, 500+30, platewidth, plateheight); //plate1

		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 30, 30); //ball


		g.setColor(Color.red);
		g.drawLine(0, 560, 700, 560);       //game over line
		
		if(play)
		{
			for(int i=0;i<4;i++)
			{
				g.setColor(Color.white);
				g.fillRect(X[i], Y[i], platewidth, plateheight); //platen
			}
			
		}
	
		for(int i=0;i<4;i++)
		{
			g.setColor(Color.black);
			g.setFont(new Font("serf", Font.BOLD, 20));	
			g.drawString(X[i] + " and "+ Y[i], 200, 70+(i*10)); //str
		}
	
	
		
		if(score1 == 4)
		{
			
			g.setColor(Color.red);
			g.setFont(new Font("serf", Font.BOLD, 50));
			g.drawString("Player1 Won" , 200, 300);
			cf = 1;
			play = false;
			
		}
		else if(score2 == 4)
		{
			
			g.setColor(Color.red);
			g.setFont(new Font("serf", Font.BOLD, 50));
			g.drawString("Player2 Won" , 200, 300);
			play = false;
			
		}
		
		if(ploose == 1)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 16));
			
			g.drawString("Player1's turn is lost", 250, 30); //str ploose1
			try{
			Thread.sleep(5000);
				
			}
			catch(InterruptedException e)
			{
				System.out.println("e = "+e);
			}
			ploose = 0;
			generate();
			start = System.nanoTime();
			ballposX = 200;
			ballposY = 500;
			pass = 2;

		}
		else if(ploose == 2)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 16));
			for(int i=1;i<70;i++)
			g.drawString("Player2's turn is lost", 250, 30); //str ploose2
			try{
			Thread.sleep(5000);
				
			}
			catch(InterruptedException e)
			{
				System.out.println("e = "+e);
			}
			ploose = 0;
			
			generate();
			start = System.nanoTime();
			ballposX = 200;
			ballposY = 500;
			pass = 1;
		}

		if(pwin == 1)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 16));
			
			g.drawString("Player1's turn is over", 250, 30); //str ploose1
			try{
			Thread.sleep(5000);
				
			}
			catch(InterruptedException e)
			{
				System.out.println("e = "+e);
			}
			pwin = 0;
			generate();
			ballposX = 200;
			ballposY = 500;
			pass = 2;

			start = System.nanoTime();

		}
		else if(pwin == 2)
		{
			g.setColor(Color.white);
			g.setFont(new Font("serf", Font.BOLD, 16));
			for(int i=1;i<70;i++)
			g.drawString("Player2' turn is over", 250, 30); //str ploose2
			try{
			Thread.sleep(5000);
				
			}
			catch(InterruptedException e)
			{
				System.out.println("e = "+e);
			}
			pwin = 0;
			generate();
			start = System.nanoTime();
			ballposX = 200;
			ballposY = 500;
			pass = 1;
		}
	

 	

 		if(timeRemain == 0)   //timer over check
 		{
 			if(pass == 1)
 			{
 				
 				ploose = 1;
			}
			else if(pass == 2)
			{
			
 				
				ploose =2;
			}

 				elapsedTime = end = start = 0;
 				timeRemain = 1;
 		}
		
 

		g.dispose();
	} 

	
	public void stop()
	{
		t.stop();
		t = null;

		play = false;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(play)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(ballposX == 650)
				{
					ballposX = 650;
				}
				else
				{
					moveRight();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(ballposX == 5)
				{
					ballposX = 5;
				}
				else
				{
					moveLeft();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				flagup = true;
				flag = 0;			
			}
			
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(!play)
			{

				play = true;

				generate();
				t = new Thread(this);
				t.start();
				start = System.nanoTime();
				

			}
		}
		
	}


	void moveRight()
	{
		ballposX += 5;
	}
	
	
	void moveLeft()
	{
		ballposX -= 5;
	}


	void upcode()
	{
		if(flag == 0 && permision == true)
		{
			goup();
		}
		else
		{
			godown();
		}

		if(height >= 140)
		{

			ballYdir = -ballYdir;
			flag = 1;
		}
		
		if(ballposY >= 530)
		{
			ballposY = 530;
			flagup = false;
			
		}
	}
	
	void intersection(int y, int z)
	{
		if(flag == 0 && flagup == true) 
		{

			ballposY = ballposY+10;
			flag = 1;
		}
		else
		{
			ballposY = y-30;
			flagup = false;
			height = 0;
			
			if(z!=-1)
			{
				if(platemov[z] == 0)
				ballposX++;
				else
				ballposX--;
			}
			
		}
	}
	
	void checkint()
	{
		if(new Rectangle(ballposX, ballposY, 30, 30).intersects(l1))
		{
			intersection(plateposYt,-1);
			permision = true;

		}
		else if(new Rectangle(ballposX, ballposY, 30, 30).intersects(l2))
		{
			
				intersection(Y[0],0);
			permision = true;

		}
		else if(new Rectangle(ballposX, ballposY, 30, 30).intersects(l3))
		{
			
			intersection(Y[1], 1);
		permision = true;

		}
		else if(new Rectangle(ballposX, ballposY, 30, 30).intersects(l4))
		{
			intersection(Y[2],2);
	permision = true;
		}
		else if(new Rectangle(ballposX, ballposY, 30, 30).intersects(l5))
		{
			
			intersection(Y[3],3);
	permision = true;
		}
			
	}
	
	void goup()
	{
		ballposY -= 2;
		if(height<=150)   //jump
			height += 2;
	}

	void godown()
	{
		ballposY += 2;
		if(height >= 0) //go down
		height -= 2;
		
	}






	void generate()
	{
		plateposX = 180;
		plateposY = 530;
		for(int i=0;i<4;i++)
		{
			X[i] = getRandomNumber(200, 450);
			Y[i] = getRandomNumber(plateposY-90, plateposY-120);     //plate generation
			
			plateposX = X[i];
			plateposY = Y[i];
		}
		
		l1 = new Rectangle(180, 530, platewidth, plateheight);
		l2 = new Rectangle(X[0], Y[0], platewidth, plateheight);
		l3 = new Rectangle(X[1], Y[1], platewidth, plateheight);
		l4 = new Rectangle(X[2], Y[2], platewidth, plateheight);
		l5 = new Rectangle(X[3], Y[3], platewidth, plateheight);
		
		
	}

	public void action() {

		if(play)
		{

			if(flag == 1)
				permision=false;
			
			if(flagup)
			{				
				upcode();
				checkint();	
			}
			else
			{
				ballposY+=3;
				
				if(ballposY>530)        //gravity effect
					ballposY = 530;
			}
			
			checkint();
			
			if(ballposY <=0 && play)//top 
			{
				ballposY=0;
				flag = 1;   //godown
			}
			
			if(ballposY >=530 && play)  //ground 
			{
				
				if(pass == 1)
				{
					ploose = 1;
				}
				else
					ploose = 2;

			}


			if(ballposX<=5)  //left screen
				ballposX = 5;


			if(ballposX>=680)  //right screen
 				ballposX = 680;
			
			

			for(int i = 0; i<=3; i++)
			{

				if(platemov[i] == 1)       //left go
				platemov_left(i);
				else
				platemov_right(i);				//right go
			}



			if(flag == 1 && flagup == true) //basket ball fall player1
			{

				if(ballposY >= 110 && ballposY <=130)
				{
					if(ballposX >= 80 && ballposX <= 110)
					{
						ballposY = 200;
						if(pass == 2)
						{
							ploose = 2;

						}
						else
						{
							score1+=2;
							pwin = 1;
						}
					}
				}

				if(ballposY >= 110 && ballposY <=130) //basket fall player2
				{
					if(ballposX >= 560 && ballposX <= 590)
					{
						ballposY = 200;
						if(pass == 1)
						{
							ploose = 1;
							pass = 2;
						}
						else
						{
							score2+=2;
							pwin = 2;
						}
					}
				}

			}

			if(ballposY >= 0 && ballposY <=110) //intersection to basket board
			{
				if(ballposX >= 590)
				{
					ballposX = 590;
				}

				if(ballposX <=80)
				{
					ballposX = 80;
				}
			}

			if(ballposY >= 110 && ballposY <=200)//intersection to basket 
			{
				if(ballposX >= 560)
				{
					ballposX = 560;
				}

				if(ballposX <=110)
				{
					ballposX = 110;
				}
			}

			if(flag == 0 && flagup == true)      //player1's down basket
			{
				if(ballposX>77 && ballposX <127)
				{
			
					if(ballposY < 190)
					{
						flag = 1;
					}
				}
			}	

			if(flag == 0 && flagup == true)  //player2's down basket
			{
				if(ballposX>578 && ballposX <611)
				{
			
					if(ballposY < 190)
					{
						flag = 1;
					}		
				}
			}


			// l1 = new Rectangle(180, 530, platewidth, plateheight);
		l2 = new Rectangle(X[0], Y[0], platewidth, plateheight);
		l3 = new Rectangle(X[1], Y[1], platewidth, plateheight);
		l4 = new Rectangle(X[2], Y[2], platewidth, plateheight);
		l5 = new Rectangle(X[3], Y[3], platewidth, plateheight);

			
		}
		
		
		
	}

	void platemov_right(int i)				//right = 0, left = 1;
	{
		X[i]++;								
		if(X[i]>=500)
		{
			platemov[i] = 1;     //opposite direction store in platemov array
		}
	}
	

	void platemov_left(int i)
	{
		X[i]--;
		if(X[i]<=120)
		{
			platemov[i] = 0;
		}
	}
	
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
/*
<Applet code="MyGame.class" width="700" height="600"></Applet>
*/