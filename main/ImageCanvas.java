package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageCanvas extends Canvas {
    
	private static final long serialVersionUID = 1L;
	private static URL[] u;
	private static int[] x, y;
	
	public void paint(Graphics g) {  

        Image img = null;
		if(u != null) {
			try {
				for(int i = 0; i < u.length; i++) {
					img = ImageIO.read(u[i]);
			        g.drawImage(img, x[i], y[i], this); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
    }
	
	public void add(URL u_, int x_, int y_) {
		int len = 1;
		if(u != null) len = u.length + 1; //calculate new length
		
		URL[] un = new URL[len]; //new url array
		int[] xn = new int[len]; //new x array
		int[] yn = new int[len]; //new y array
		
		for(int i = 0; i < len-1; i++) {
			un[i] = u[i]; //takes old url array
			xn[i] = x[i]; //takes old x array
			yn[i] = y[i]; //takes old y array
		}
		
		un[len-1] = u_; //adds new url element
		xn[len-1] = x_; //adds new x element
		yn[len-1] = y_; //adds new y element
		
		u = un; //replace old url array
		x = xn; //replace old x array
		y = yn; //replace old y array
	}
	
	public void reset() {
		u = null;
		x = null;
		y = null;
	}
	
}
