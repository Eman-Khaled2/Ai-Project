package pr1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class chopsticks{
	
	//create the sticks
	public BufferedImage createStick(){
		int width=10;
		  int height=80;
		BufferedImage chopstickImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=chopstickImage.createGraphics();
		  g2d.setColor(Color.black);
		 g2d.fillRect(0,0,width,20);
		 g2d.setColor(Color.WHITE);
		  g2d.drawLine(0,20,width,20);
		g2d.setColor(Color.yellow);
		g2d.fillRect(0,21,width,height-21);
		g2d.dispose();
		return chopstickImage;//the stick
	}}
