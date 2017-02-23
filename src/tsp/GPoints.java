package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GPoints extends JFrame {   

    public void initUI(Graph g) {
     
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(g, BorderLayout.CENTER);
        
        setTitle("TSP");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       
   

    }
    
    

   
}

