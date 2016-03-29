import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Shell {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean valid = false;
		double mutation_rate = 0.005;
		double death_rate = 0.1;
		double initial_PTR = 0.12;
		int immigrants_per_day = 1;
		double cost_of_giving = 0.01;
		double gain_of_receiving = 0.03;
		double immigrant_chance_coop_with_same = 0.5;
		double immigrant_chance_coop_with_diff = 0.5;
		int tick,lattice = 50;
		do{
			System.out.print("Please select use default initial parameters or use custom parameters(Enter D/C):");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//String input = br.readLine();
			if (input.equals("D")||input.equals("d")){
				System.out.println("Use default initial parameters.");
				valid = true;
			}
			else if(input.equals("C")||input.equals("c")){
				System.out.println("Please enter the mutation rate, death rate, initialPTR, immigrants per day, cost of giving, gain of receiving, chance to cooperate with same, chance to cooperate with different:");
				StringBuffer buffer = new StringBuffer();
				//String value = br.readLine();
				String value = scanner.nextLine();
				while(value != null){
					   buffer.append(" "+value);
					   value = scanner.nextLine();
					  }
				String[] temp = buffer.toString().replaceFirst(" ","").split("\\s+");
				for(int i=0;i<temp.length;i++){
					if(i!=4 && Double.valueOf(temp[i]).doubleValue()<0||Double.valueOf(temp[i]).doubleValue()>1){
						System.out.print("Invalid input value!");
						valid = false;
					}
				}
				if (valid){
					mutation_rate = Double.valueOf(temp[0]).doubleValue();
					death_rate = Double.valueOf(temp[1]).doubleValue();
					initial_PTR = Double.valueOf(temp[2]).doubleValue();
					immigrants_per_day = Integer.parseInt(temp[3]);
					cost_of_giving = Double.valueOf(temp[4]).doubleValue();
					gain_of_receiving = Double.valueOf(temp[5]).doubleValue();
					immigrant_chance_coop_with_same = Double.valueOf(temp[6]).doubleValue();
					immigrant_chance_coop_with_diff = Double.valueOf(temp[7]).doubleValue();
				}
				
			}
			System.out.print("How many ticks to run:");
			tick = scanner.nextInt();
			
		}while(!valid);
		System.out.println(tick + " tick will be run.");
		Go(mutation_rate, death_rate, initial_PTR, immigrants_per_day, cost_of_giving, gain_of_receiving, immigrant_chance_coop_with_same, immigrant_chance_coop_with_diff, tick);
		
	}

	private static void Go(double mutate, double death, double PTR, int immi, double cost, double gain, double coop_with_same, double coop_with_diff, int ticks) throws FileNotFoundException{
		
		World w1 = new World();
		
		//int[] get;
		//int CC, CD, DC, DD;
		//Counter count = new Counter();
		//count.resetAllCount();
		for(int i=0; i<ticks; i++){
			//count.resetTick();
			//System.out.println("In tick "+i);
			w1.immigrate(immi, PTR, coop_with_same, coop_with_diff);
			w1.interact(PTR, cost, gain);
			w1.reproduce(mutate, PTR, coop_with_same, coop_with_diff);
			w1.dead(death);
			
			w1.count(i);
			//CC = get[0]; CD = get[1]; DC = get[2]; DD = get[3];
			//ps.append(i+","+CC+","+CD+","+DC+","+DD+"\n");	
		}		
		//ps.flush();
		//ps.close();
	}
	
}
