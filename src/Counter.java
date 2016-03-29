
public class Counter {
	/* How many interactions occurred this turn;
	 * How many interactions occurred through the run
	 * interactions for the last 100 ticks
	 * coopOwn + defOwn + coopSame + coopOther = 2*meet ?
	 */
	public Counter(){
		resetAllCount();
	}
	public int meet, meetAgg,//For interactions
		meetOwn, meetOwnAgg, // Meet same group
		meetOther, meetOtherAgg, // Meet other group
		coopOwn, coopOwnAgg, // Number of Cooperating with the same
		defOwn, defOwnAgg, // Number of defect the same 
		coopOther, coopOtherAgg,  //Number of cooperating with other
		defOther, defOtherAgg;//Number of defect other
	public CycleArray last100meet, last100meetOwn, 
		last100meetOther,last100coopOwn, last100defOwn, 
		last100coopOther, last100defOther, 
		last100cc, // coopOwn-coopOther 
		last100cd,//coopOwn-defOther
		last100dc,//defOwn-coopOther
		last100dd,//defOwn-defOther
		last100coop,//total cooperation in last 100 ticks
		last100consistEthno;// ?
	
	//reset some parameters in a new tick
	public void resetTick(){
		this.meet			= 0;
		this.meetOwn	= 0;
		this.meetOther = 0;
		this.coopOwn	= 0;
		this.coopOther	= 0;
		this.defOwn		= 0;
		this.defOther	= 0;
	}
	
	// reset last 100 ticks track
	public void reset100Tisks(){
		this.last100meet = new CycleArray();
		this.last100meetOwn = new CycleArray();
		this.last100meetOther = new CycleArray();
		this.last100coop = new CycleArray();
		this.last100coopOwn = new CycleArray();
		this.last100coopOther = new CycleArray();
		this.last100defOwn = new CycleArray();
		this.last100defOther = new CycleArray();
		this.last100cc = new CycleArray();
		this.last100cd = new CycleArray();
		this.last100dc = new CycleArray();
		this.last100dd = new CycleArray();
		this.last100consistEthno = new CycleArray();
	}
	
	// reset all statistic
	public void resetAllCount(){
		this.resetTick();
		this.reset100Tisks();
		this.meetAgg				= 0;
		this.meetOwnAgg	= 0;
		this.meetOtherAgg = 0;
		this.coopOwnAgg	= 0;
		this.coopOtherAgg	= 0;
		this.defOwnAgg		= 0;
		this.defOtherAgg 	= 0;
	}
	
	// Update  all figure after one tick
	public void updateTick(int tickData[]){
		this.meet			= tickData[0];
		this.meetOwn	= tickData[1];
		this.meetOther = tickData[2];
		this.coopOwn	= tickData[3];
		this.coopOther	= tickData[4];
		this.defOwn		= tickData[5];
		this.defOther	= tickData[6];
		
		//update 100 ticks data
		this.last100meet.push(meet);
		this.last100meetOwn.push(meetOwn);
		this.last100meetOther.push(meetOther);
		this.last100coop.push(coopOwn+coopOther);
		this.last100coopOwn.push(coopOwn);
		this.last100coopOther.push(coopOther);
		this.last100defOwn.push(defOwn);
		this.last100defOther.push(defOther);
		
		//update Agg data
		this.meetAgg				= meetAgg + meet;
		this.meetOwnAgg	= meetOwnAgg + meetOwn;
		this.meetOtherAgg = meetOtherAgg + meetOther;
		this.coopOwnAgg	= coopOwnAgg + coopOwn;
		this.coopOtherAgg	= coopOtherAgg + coopOther;
		this.defOwnAgg		= defOwnAgg + defOwn;
		this.defOtherAgg 	= defOtherAgg + defOther;
	}
	

	// Get the number of people cooperating with everyone
	public double getCC(){
		return 0;
	}
	
	//Get the number of people cooperating with same type
	public double getCD(){ 
		return 0;
	}
	
	//Get the number of people not cooperating with anyone
	public double getDD(){ 
		return 0;
	}
	
	//Get the number of people cooperating with different types
	public double getDC(){ 
		return 0;
	}
}

/*
 *	public int meet, meetAgg,//For interactions
	meetOwn, meetOwnAgg, // Meet same group
	meetOther, meetOtherAgg, // Meet other group
	coopOwn, coopOwnAgg, // Number of Cooperating with the same
	defOwn, defOwnAgg, // Number of defect the same 
	coopOther, coopOtherAgg,  //Number of cooperating with other
	defOther, defOtherAgg;//Number of defect other
public CycleArray last100meet, last100meetOwn, 
	last100meetOther,last100coopOwn, last100defOwn, 
	last100coopOther, last100defother, 
	last100cc, // coopOwn-coopOther 
	last100cd,//coopOwn-defOther
	last100dc,//defOwn-coopOther
	last100dd,//defOwn-defOther
	last100coop,//total cooperation in last 100 ticks
	last100consistEthno;// ?
 * */