package practice2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	static List<Vertex> vertices = new ArrayList<>();
	static List<Edge> edges = new ArrayList<>();
	Canvas(){
		setup();
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Vertex v: vertices) {
			//System.out.println(v.x + " " + v.y);
			if(v.isSelected) {
				g.setColor(Color.green);
				g.fillOval(v.x,v.y,7,7);
			}
			else if(v.cutVertex) {
				g.setColor(Color.yellow);
				g.fillOval(v.x, v.y, 10, 10);
				v.cutVertex=false;
			}
			else{
				g.setColor(Color.red);
				g.fillOval(v.x, v.y, 7, 7);
			}
		}//for loop
		for (Edge e: edges) {
			g.setColor(Color.blue);
			g.drawLine(e.first.x+3, e.first.y+3, e.second.x+3, e.second.y+3);
		}
		if(Sidebar.connectedComponents) {
			for(Vertex v: Canvas.vertices) {
				if(!v.visited) {
					v.visited=true;
					Random rand = new Random();
					Color color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
					Sidebar.dfs(v,color,g);
				}
			}
			Sidebar.connectedComponents=false;
			for(Vertex v: Canvas.vertices) {
				v.visited=false;
			}
		}
	
			
	}//paint
	private void setup(){
		this.setBackground(Color.lightGray);
	}


	Vertex closestVertexClicked(MouseEvent click) throws Exception{

		for(int i=0;i<vertices.size();i++) {
			if(Math.sqrt(Math.pow(click.getX()-vertices.get(i).x,2) + (Math.pow(click.getY()-vertices.get(i).y,2))) < 10) {
				//System.out.println("if statement worked \n distance is:"+ Math.sqrt(Math.pow(click.getX()-verticies.get(i).x,2) + 
				//(Math.pow(click.getY()-verticies.get(i).y,2)))+"\n"+"index is: "+i);
				vertices.get(i).isSelected=true;
				return vertices.get(i);
			}
		}
		throw new Exception("didnt click Close enough");
	} 

	
	void removeVertex(MouseEvent click) throws Exception {
		Vertex v = closestVertexClicked(click);
		int i = 0;
		for(Vertex x: vertices) {
			if(x.x==v.x && x.y==v.y) {
				for(int j=edges.size()-1;j>=0;j--) {
					if((v.x==edges.get(j).first.x && v.y==edges.get(j).first.y)||
							(v.x==edges.get(j).second.x && v.y==edges.get(j).second.y) )
						edges.remove(j);	
				}
				vertices.remove(i);
				break;
			}
			i++;
		}
		
		
	}
	void moveVertex(MouseEvent click, int index) {
		vertices.get(index).x = click.getX()-3;
		vertices.get(index).y = click.getY()-5;
		vertices.get(index).isSelected = false;
		System.out.println(vertices.get(index).isSelected);
		repaint();
	}

}
