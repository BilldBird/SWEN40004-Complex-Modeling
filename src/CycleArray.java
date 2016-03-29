
public class CycleArray{
	int length, count, index;
	int arr[];
	
	//construction
	public CycleArray(){
		this.length = 100;
		this.arr = new int[length];
		this.count = 0;
		this.index = 0;
	}
	
	//construction
	public CycleArray(int _length){
		this.length = _length;
		this.arr = new int[length];
		this.count = 0;
		this.index = 0;
	}
	
	// update the figure in the array
	public CycleArray push(int element){
		arr[count] = element;
		count++ ;
		if(index < length-1)
			index ++;
		else
			index = 0;
		return this;
	}
	
	public int getTotalLast100(){
		int total = 0;
		if(count<length){
			for(int i = 0; i <= this.count; i++)
				total = total + arr[i];			
		}else{
			for(int i = 0; i <= length; i++)
				total = total + arr[i];
		}
		return total;
	}
	public void resetArray(){
		this.arr = new int[arr.length];
	}
}

