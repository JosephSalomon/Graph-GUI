package practice2;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GraphGUI extends JFrame {//implements ActionListener {
	static Sidebar sidebar;
	static Canvas canvas;
	
	GraphGUI() {
		super("GRAPHGUI");
		setSize(600,400);
		setLocation(200, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setup();
		
		setVisible(true);
	}
	
	private void setup() {
		sidebar = new Sidebar();
		canvas = new Canvas();
		add(sidebar, BorderLayout.WEST);
		add(canvas, BorderLayout.CENTER);
		canvas.repaint();
	

	

		

	//@Override
	//public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		//Constructor
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {	    		
				if ( sidebar.addVertex.isSelected() ) {
					Vertex i = new Vertex(evt.getX()-3, evt.getY()-5);
					canvas.vertices.add(i);
					System.out.println("button Pressed"); 
					System.out.println(canvas.vertices);
					repaint();
				}
				
				else if(sidebar.removeVertex.isSelected()) {
					System.out.println("removevertex selected");
					//int index = canvas.closestVertexClicked(evt);
					
					try {
						canvas.removeVertex(evt);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("exception: "+e);
					}
					repaint();
				}
				
				else if(sidebar.moveVertex.isSelected()) {
					for(int i = 0;i<canvas.vertices.size();++i) {
						if (canvas.vertices.get(i).isSelected) {
							canvas.moveVertex(evt, i);
							break;
						}
						else if (i==canvas.vertices.size()-1) {
							System.out.println("move Vertex Selected");
							try {
								canvas.closestVertexClicked(evt);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("exception: "+e);
							}
							repaint();
						}
					}
				}//move vertex

				else if(sidebar.addEdge.isSelected()) {
					try {
						for(int i = 0;i<canvas.vertices.size();i++) {
							if(canvas.vertices.get(i).isSelected) {
								Edge e = new Edge(canvas.vertices.get(i),canvas.closestVertexClicked(evt));
								canvas.edges.add(e);
								e.first.isSelected=false;
								e.second.isSelected=false;
								repaint();
								break;
							}
							else if(canvas.vertices.size()==i+1) {
								canvas.closestVertexClicked(evt);
								repaint();
							}//else statement

						}//for loop
					} catch (Exception e) {
						System.out.println("exception e: "+ e);
					}//catch
				} //addEdge
				
				else if(sidebar.removeEdge.isSelected()) {
					Vertex v = new Vertex(evt.getX(),evt.getY());
					for(int i=0;i<Canvas.edges.size();i++) {
						if(Math.abs(Edge.distance(Canvas.edges.get(i).first,Canvas.edges.get(i).second)-
								(Edge.distance(Canvas.edges.get(i).first, v)+Edge.distance(v,Canvas.edges.get(i).second)))<3) {
							Canvas.edges.remove(i);
							break;
						}
					}
					repaint();
				}

				
			}	
		});
		
	}
    public static void paintComponent() {
        canvas.repaint();

    }

	
	
}


