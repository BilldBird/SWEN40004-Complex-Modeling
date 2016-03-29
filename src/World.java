import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile; 

public class World {
	public People[][] grid;
	public File result;
	public int LATTICE_SIZE = 50;
	private int length = 50, width = 50; // size of world
	double mutationRate = 0.005, //  Offspring have the same traits as their parents
			initialPTR = 0.12, //the rate of birth 
			deathRate = 0.1, //the rate of death
			immigrantsPreDay = 1, //immigrant per day 
			costGiving = 0.01, //costs of an agent to cooperate with another agent 
			gainReceiving = 0.03, //gain if another agent cooperates with them
			immigrantCopSame = 0.5, immigrantCopDiff=0.5;
	
	public World() throws FileNotFoundException{
		this.grid = new People[LATTICE_SIZE][LATTICE_SIZE];
		this.result = new File("result2.csv");
		try {  
			FileWriter writer = new FileWriter(result, false);
			writer.write("CC, CD, DC, DD"+"\n");
			writer.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
		/*
		PrintStream ps = new PrintStream(new FileOutputStream(result));
		ps.append("tick, CC, CD, DC, DD"+"\n");
		ps.flush();
		ps.close();*/
	}
	
	/*----------------------------------------------*/
	public void setFull(){
		
	}
	public void setEmpty(){
		
	}
	
	/*----------------------------------------------*/
	public void interact(double ptr, double cost, double gain){
		for(int i=0; i<LATTICE_SIZE; i++)						
		{
			for(int j=0; j<LATTICE_SIZE; j++)
			{
				//when point Point(i,j) has people on it, find its neighbors
				if(grid[i][j] != null){
					grid[i][j].PTR = ptr;
					ArrayList<Point> neighbor_pos = new ArrayList<Point>();
					neighbor_pos = findNeighbors(grid[i][j]);
					int neighbor_no = neighbor_pos.size();
					//count.meet += neighbor_no;
					while (neighbor_no > 0){
						Point n = neighbor_pos.remove(neighbor_no - 1);
						neighbor_no--;
						// if meet a neighbor with same color
						if(grid[i][j].color == grid[n.x_point][n.y_point].color){
							//count.meetOwn++;
							if(grid[i][j].copWithSame){
							//if(grid[i][j].copWithSame && grid[n.x_point][n.y_point].copWithSame){
								grid[i][j].PTR -= cost;
								grid[n.x_point][n.y_point].PTR += gain;
								//count.coopOwn++;
							}
							else{
								//count.defOwn++;
							}
						}
						 //if meet a neighbor with different color
						else{
							//count.meetOther++;
							//if(grid[i][j].copWithOther && grid[n.x_point][n.y_point].copWithOther){
							if(grid[i][j].copWithOther){
								grid[i][j].PTR -= cost;
								grid[n.x_point][n.y_point].PTR += gain;
								//count.coopOther++;
							}
							else{
								//count.defOther++;
							}
						}
					}
					//System.out.println("The PTR of ("+grid[i][j].location.x_point+", "+grid[i][j].location.y_point+") is "+grid[i][j].copWithSame+" "+grid[i][j].copWithOther+" "+grid[i][j].PTR);
				}
			}
		}
	}
	public void immigrate(int immi_per_day, double ptr, double chance_coop_same, double chance_coop_diff){
		ArrayList<Point> emptySpaces = new ArrayList<Point>();
		Random r = new Random();
		//find random empty space
		for(int i=0; i<LATTICE_SIZE; i++)						
		{
			for(int j=0; j<LATTICE_SIZE; j++)
			{
				if(grid[i][j] == null)
					emptySpaces.add(new Point(i,j));
			}
		}
		for (int k=0; k<immi_per_day; k++){
			//create new agent at that space
			int newIndex = r.nextInt(emptySpaces.size());  
			Point newSpace = new Point();
			newSpace = emptySpaces.remove(newIndex);
			People immigrate = new People(newSpace, ptr, chance_coop_same, chance_coop_diff);
			grid[newSpace.x_point][newSpace.y_point] = immigrate;
		}
	}
	public void reproduce(double mutate_rate, double ptr, double chance_coop_same, double chance_coop_diff){
		Random r = new Random();
		for(int i=0; i<LATTICE_SIZE; i++)						
		{
			for(int j=0; j<LATTICE_SIZE; j++)
			{
				// if the location has people and can reproduce
				if(grid[i][j]!=null && r.nextDouble() <= grid[i][j].PTR){
					// find a empty space to reproduce
					ArrayList<Point> empty_points = new ArrayList<Point>();
					empty_points = findEmptyPoint(grid[i][j]);
					// if there has empty point
					if(empty_points.size() > 0){
						int random_point = r.nextInt(empty_points.size());
						Point destination = empty_points.get(random_point);
						// check if the offspring is mutate
						if(r.nextDouble() <= mutate_rate){
							People mutate_offspring = new People(destination, ptr, chance_coop_same, chance_coop_diff);
							grid[destination.x_point][destination.y_point] = mutate_offspring;
							
						}
						else{
							People offspring = new People(destination, ptr, grid[i][j].color, grid[i][j].copWithSame, grid[i][j].copWithOther);
							grid[destination.x_point][destination.y_point] = offspring;
						}
					}
				}
			}
		}
		//System.out.println("The reproduce number is: " + born);
	}
	public void dead(double death_rate){
		Random r = new Random();
		for(int i=0; i<LATTICE_SIZE; i++)						
		{
			for(int j=0; j<LATTICE_SIZE; j++)
			{
				//if the location has people and has the probability to die
				if(grid[i][j]!=null && r.nextDouble() <= death_rate)
				{
					grid[i][j] = null;
				}
			}
		}
	}
 
	public static void append(File fileName, String content){
		try {
		FileWriter writer = new FileWriter(fileName, true);
		writer.write(content);
		writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------*/
	//Count the number of CC, CD, DC, DD
	public void count(int tick){
		int CC = 0, CD = 0, DC = 0, DD = 0;
		for(int i=0; i<LATTICE_SIZE; i++)						//find random empty space
		{
			for(int j=0; j<LATTICE_SIZE; j++)
			{
				if(grid[i][j] != null){
					boolean coopsame = grid[i][j].copWithSame;
					boolean coopdiff = grid[i][j].copWithOther;
					if(coopsame&&coopdiff)
						CC++;
					if(coopsame&&!coopdiff)
						CD++;
					if(!coopsame&&coopdiff)
						DC++;
					if(!coopsame&&!coopdiff)
						DD++;
				}
			}
		}
		String content = CC+","+CD+","+DC+","+DD+"\n";
		//ReadFromFile.readFileByLines(this.result);
		append(this.result, content);
		
		//System.out.println("The number of CC is " + CC);
		//System.out.println("The number of CD is " + CD);
		//System.out.println("The number of DC is " + DC);
		//System.out.println("The number of DD is " + DD);
	}
	
	// find the neighbor around a people
	private ArrayList<Point> findNeighbors(People p)
	{
		ArrayList<Point> neighbors = new ArrayList<Point>();

		int x_up = (p.location.x_point + 1) % 50; 
		int x = p.location.x_point;
		int x_down = ((p.location.x_point - 1) % 50 + 50) % 50;

		int y_up = (p.location.y_point + 1) % 50;
		int y = p.location.y_point;
		int y_down = ((p.location.y_point - 1) % 50 + 50) % 50;

		// Check the upper point whether has neighbor
		if(grid[x][y_up] != null)
			neighbors.add(new Point(x, y_up));
		
		// Check the down point whether has neighbor
		if(grid[x][y_down] != null)
			neighbors.add(new Point(x, y_down));
		
		// Check the left side whether has neighbor
		if(grid[x_down][y] != null)
			neighbors.add(new Point(x_down, y));
		
		// Check the right ride whether has neighbor
		if(grid[x_up][y] != null)
			neighbors.add(new Point(x_up, y));
		
		return neighbors;
	}
	
	// find a empty space to reproduce
	private ArrayList<Point> findEmptyPoint(People p)
	{
		ArrayList<Point> empty_point = new ArrayList<Point>();

		int x_up = (p.location.x_point + 1) % 50; 
		int x = p.location.x_point;
		int x_down = ((p.location.x_point - 1) % 50 + 50) % 50;

		int y_up = (p.location.y_point + 1) % 50;
		int y = p.location.y_point;
		int y_down = ((p.location.y_point - 1) % 50 + 50) % 50;

		// Check the upper point whether is empty
		if(grid[x][y_up] == null)
			empty_point.add(new Point(x, y_up));
		
		// Check the down point whether is empty
		if(grid[x][y_down] == null)
			empty_point.add(new Point(x, y_down));
		
		// Check the left side whether is empty
		if(grid[x_down][y] == null)
			empty_point.add(new Point(x_down, y));
		
		// Check the right ride whether is empty
		if(grid[x_up][y] == null)
			empty_point.add(new Point(x_up, y));
		
		return empty_point;
	}
	
	
}
