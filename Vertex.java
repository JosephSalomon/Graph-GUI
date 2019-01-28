package practice2;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


class Vertex extends Point{
	int x;
	int y;
	boolean isSelected;
	boolean visited;
	boolean cutVertex;

	Vertex(int xCoordinate, int yCoordinate){
		x = xCoordinate;
		y = yCoordinate;
		isSelected = false;
		visited = false;
		cutVertex=false;
	}//constructor 

	public boolean equals(Vertex v) {
		if (this.x==v.x && this.y == v.y)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Vertex [x=" + x + ", y=" + y + "]";
	}//toString




	

}//Vertex

