package tsp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Graph extends JPanel implements ActionListener {
	ArrayList<Integer> xl;
	ArrayList<Integer> yl;
	
	public Graph(){	
		this.xl = new ArrayList<Integer>();
		this.yl = new ArrayList<Integer>();
	}
	
	public void addPoint(int x, int y){
		xl.add(x);
		yl.add(y);
	}
    public void doDrawing(Graphics g) {
  
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.blue);
       int h = getHeight();
       int r = (5/2);
       
        for (int i = 0; i < xl.size(); i++) {	
        	int nyY = h-(yl.get(i)/2);
        	
        	g2d.fillOval((xl.get(i)/2)-r, nyY-r, 5,5);
        	
  
        	if(i+1 <xl.size()){
        		int nyY1 = h-(yl.get(i+1)/2);
        		g2d.drawLine((xl.get(i)/2), nyY, (xl.get(i+1)/2), nyY1);
        	
        	}
        
        	else{
        		g2d.drawLine((xl.get(i)/2), nyY, (xl.get(0)/2), h-(yl.get(0)/2));
        	}
        	
		}
        
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
       
      
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

