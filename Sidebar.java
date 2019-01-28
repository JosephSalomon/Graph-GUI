package practice2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Sidebar extends JPanel {
	JRadioButton addVertex, addEdge, removeVertex, removeEdge, moveVertex;
	JButton addAllEdgesBtn, connectedComponentsBtn, showCutVerticiesBtn, helpBtn;
	static boolean connectedComponents, cutVerticies;
	static int numConnectedComponents;
	

	
	Sidebar(){
		setup();
		setVisible(true);
	}
	private void setup() {
		setLayout(new GridLayout(9,1));
		
		// Create JRadio Buttons
		addVertex = new JRadioButton("Add Vertex");
		addEdge = new JRadioButton("Add Edge");
		removeVertex = new JRadioButton("Remove Vertex");
		removeEdge = new JRadioButton("Remove Edge");
		moveVertex = new JRadioButton("Move Vertex");

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(addVertex);
		group.add(addEdge);
		group.add(removeVertex);
		group.add(removeEdge);
		group.add(moveVertex);	
		
		//make Regular Buttons 
		addAllEdgesBtn = new JButton("Add All Edges");
		connectedComponentsBtn = new JButton("Connected Components");
		showCutVerticiesBtn = new JButton("Show Cut Verticies");
		helpBtn = new JButton("Help");
		
		connectedComponents=false;
		cutVerticies=false;
		
		//Add Action Listeners to buttons 
		addAllEdgesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group.clearSelection();
				for(int i=0;i<Canvas.vertices.size();i++) {
					for(int j=i+1;j<Canvas.vertices.size();j++) {
						if(i!=j) {
							//TODO check if edge already exsists first
							Edge edge = new Edge(Canvas.vertices.get(i),Canvas.vertices.get(j));
							Canvas.edges.add(edge);
						}
					}
				}
				System.out.println(Canvas.edges);
				GraphGUI.paintComponent();	
			}//action Performed
		});// add action listener

		
		connectedComponentsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group.clearSelection();
				connectedComponents=true;
				GraphGUI.paintComponent();
			}//action Performed
		});// add action listener

		
		
		showCutVerticiesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group.clearSelection();
				cutVerticies=true;
				for(Vertex v: Canvas.vertices) {
					if(!findNeighbors(v).isEmpty()) {
						numComponents(Canvas.vertices, Canvas.edges);
						int numComponents = numConnectedComponents;
						//copy vertex list without current vertex
						ArrayList<Vertex> copyV = new ArrayList<>();
						for(int i = 0;i<Canvas.vertices.size();i++)
							if(!v.equals(Canvas.vertices.get(i)))
								copyV.add(Canvas.vertices.get(i));
						//copy edge list without edges that contain current vertex
						ArrayList<Edge> copyE = new ArrayList<>();
						for(int i=0;i<Canvas.edges.size();i++) {
							if(!v.equals(Canvas.edges.get(i).first)  && !v.equals(Canvas.edges.get(i).second))
								copyE.add(Canvas.edges.get(i));
						}
						numComponents(copyV,copyE);
						int newComponents= numConnectedComponents;
						if(!(newComponents == numComponents))
							v.cutVertex=true;

					}
					//TODO remove vertex and see if connected components are same
					//if not same then this is a cut vertex and i should paint it
					
				}
				GraphGUI.paintComponent();
			}//action Performed
		});// add action listener


		
		helpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group.clearSelection();
				JOptionPane.showMessageDialog(null, "click a button to add or" 
						+" remove or move verticies", "Help", 1 );
			}//action Performed
		});// add action listener
		
		
		this.add(addVertex);
		this.add(addEdge);
		this.add(removeVertex);
		this.add(removeEdge);
		this.add( moveVertex);
		this.add( addAllEdgesBtn);
		this.add(connectedComponentsBtn);
		this.add(showCutVerticiesBtn);
		this.add(helpBtn);	
	}


	//for Connected Components
	public static ArrayList findNeighbors(Vertex v) {
		ArrayList<Vertex> neighborList = new ArrayList<Vertex>();
		for(int i= 0;i<Canvas.edges.size();i++) {
			Edge neighbor =Canvas.edges.get(i);
			if (neighbor.first.equals(v) && (!neighbor.second.visited)) {
				//TODO Paint vertecies and edge
				neighborList.add(neighbor.second);
			}

			else if(neighbor.second.equals(v) && (!neighbor.first.visited)) {
				//TODO Paint verticies and edge
				neighborList.add(neighbor.first);
			}

		}
		return neighborList;

	}
	//this is used for connected components so the painting is in here
	public static void dfs(Vertex v, Color color, Graphics g) {
		ArrayList<Vertex> neighborList = findNeighbors(v);
		g.setColor(color);
		g.fillOval(v.x,v.y,7,7);
		for(Vertex neighbor:neighborList) {
			neighbor.visited=true;
			g.drawLine(v.x+3, v.y+3, neighbor.x+3, neighbor.y+3);
			g.fillOval(neighbor.x, neighbor.y, 7, 7);

			if(!findNeighbors(neighbor).isEmpty()) {
				dfs(neighbor,color,g);
			}
		}//for loop

	}

	//this is used for cut verticies
	public static void dfs(Vertex v, List<Edge> edges) {
		ArrayList<Vertex> neighborList = findNeighbors(v, edges);
		for(Vertex neighbor:neighborList) {
			neighbor.visited=true;
			if(!findNeighbors(neighbor, edges).isEmpty()) {
				dfs(neighbor, edges);
			}
		}//for loop
	}
	//cut verticies
	public static void numComponents(List<Vertex> vertices, List<Edge> edges) {
		numConnectedComponents=0;
		for(Vertex v: vertices) {
			if(!v.visited) {
				v.visited=true;
				numConnectedComponents+=1;
				dfs(v,edges);
			}
		}
		for(Vertex v: vertices) {
			v.visited=false;
		}

	}
	/***
	 * 
	 * @param v vertex in the arraylist of vertices
	 * @param edge is a copy of all the edges in the static Edges class
	 * @return an array list of all of the vertex's neighbors
	 */
	public static ArrayList findNeighbors(Vertex v, List<Edge> edge) {
		ArrayList<Vertex> neighborList = new ArrayList<Vertex>();
		for(int i= 0;i<edge.size();i++) {
			Edge neighbor = edge.get(i);
			if (neighbor.first.equals(v) && (!neighbor.second.visited)) {
				neighborList.add(neighbor.second);
			}

			else if(neighbor.second.equals(v) && (!neighbor.first.visited)) {
				neighborList.add(neighbor.first);
			}

		}
		return neighborList;

	}
}

