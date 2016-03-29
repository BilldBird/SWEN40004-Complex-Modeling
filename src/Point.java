
public class Point {
	public int x_point;
	public int y_point;
	boolean occupied;
	public People p;
	
	//construction
	public Point(){}
	public Point(int x, int y) throws NullPointerException{
		if(x < -1 || y < -1 ) throw new NullPointerException();
		this.x_point = x;
		this.y_point = y;
		this.occupied = false;
	}
	
	public enum direction {UP, DOWN, RIGHT, LEFT}
	
	public Point getRandomPositionNearby(Point currentPoint){
		Point des = new Point (-1, -1) ;
		//enum valiableDirc implements direction {UP, DOWN}	
		/*.....*/
		
		return des;
	}
}
