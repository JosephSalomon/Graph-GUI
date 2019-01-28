package practice2;

public class Edge {
	Vertex first, second;
	boolean visited;
	
	Edge(Vertex one, Vertex two){
		first=one;
		second=two;
	}//Constructor 

	@Override
	public String toString() {
		return "Edge [first=" + first + ", second=" + second + "]";
	}
	
	public static int distance(Vertex a, Vertex b) {
		return (int) Math.sqrt(Math.pow(a.x-b.x, 2)+Math.pow(a.y-b.y, 2));
	}
	

}
