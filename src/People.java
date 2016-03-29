import java.util.*;
import java.io.*;

public class People {

	Point location; // Location of certain people
	int color; // color of this man
	double PTR;
	boolean copWithSame, copWithOther;
	Random r = new Random();

	//Construction random a man only with location 
	public People(Point loc, double initial_PTR, double coop_same, double coop_diff) {
		this.location = loc;
		this.PTR = initial_PTR;
		double d1 = r.nextDouble();
		double d2 = r.nextDouble();
		if(d1<coop_same){
			this.copWithSame = true;
		}else{
			this.copWithSame = false;
		}
		if(d2<coop_diff){
			this.copWithOther = true;
		}else{
			this.copWithOther = false;
		}
		this.color = r.nextInt(4);
	}
	//Create man with attribute 
	public People(Point loc, double initial_PTR, int ethn, boolean copSame, boolean copDif){
		this.location = loc;
		this.color = ethn;
		this.PTR = initial_PTR;
		this.copWithSame = copSame;
		this.copWithOther = copDif;
	}
	
	//A man move to a destination
	public boolean Move(Point dest) throws Exception{
		// destination is occupied by other 
		if(dest.occupied == true){
			return false;
		}
		
		this.location.occupied = false;
		dest.occupied = true;
		this.location = dest;
		return true;
	}
	
	//if there is no assigned destination, move 1 grid in random direction
	public boolean Move() throws Exception{
		Point currentPoint = this.location;
		Point dest = this.location.getRandomPositionNearby(currentPoint);
		if(dest.x_point<0 || dest.y_point<0)
			return false;
		
		this.location.occupied = false;
		dest.occupied = true;
		this.location = dest;
		return true;
	}	

}