package Server;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RewardSystem implements Runnable {
	private ArrayList<ArrayList<Integer>> sections;
	public static final Double REWARDS_PER_UNIT = 0.05;
	public static final Integer WORLD_SIZE = 20;
	public static final Integer SECTION_SIZE = 5;
	
	ArrayList<Scooter> scooterList;
	ScooterManager scooterManager;
	Integer coordinateSectionNumber;
	Condition condition;
	Lock lock;
	
	public RewardSystem(ScooterManager scooterManager, Condition condition, Lock lock) {
		this.scooterManager = scooterManager;
		this.lock = lock;
		this.condition = condition;
		coordinateSectionNumber = WORLD_SIZE / SECTION_SIZE;
		
		sections = new ArrayList<>();
		ArrayList<Integer> section;
		
		
		for(int i = 0; i<coordinateSectionNumber; i++) {
			section = new ArrayList<>();
			for(int j = 0; j<coordinateSectionNumber; j++) {
				section.add(0);
			}
			sections.add(section);
		}
		
	}
	

	public void calculateScootersPerSection() {
		ArrayList<Integer> location;
		double a,b;
		int x,y;
		System.out.println("Calculating");
		
		for(Scooter scooter: scooterList) {
			location = scooter.getLocation();
			a = location.get(0) / (double) coordinateSectionNumber;
			b = location.get(1) / (double) coordinateSectionNumber;
			
			// Roll To The Lower Value
			if(a != (int)a) {
				x = (int)a - 1;
			}
			else {
				x = (int) a;
			}
			
			if(b != (int)b) {
				y = (int)b - 1;
			}
			else {
				y = (int) b;
			}
			
			if(x < coordinateSectionNumber && y < coordinateSectionNumber) {				
				sections.get(x).set(y,sections.get(x).get(y)+1) ;
			}
			else {
				System.out.println("Out X: "+a+" Y: "+b);
			}
			
		}
	}
	
	
	

	public ArrayList<ArrayList<Integer>> getSections() {
		return sections;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {			
			
			// Get Current Scooter List
			this.scooterList = scooterManager.getScooterList();
			
			// Calculate Rewards
			calculateScootersPerSection();
			
			lock.lock();
			//Wait for change
			try {
				condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}


	public Double calculateReward(ArrayList<Integer> oldLocation, ArrayList<Integer> newLocation) {
		// TODO Auto-generated method stub
		Integer x,y, scooterNumberOld, scooterNumberNew;
		double reward; 
		
		x = oldLocation.get(0) / coordinateSectionNumber;
		y = oldLocation.get(1) / coordinateSectionNumber;
		scooterNumberOld = sections.get(x).get(y);
		
		x = newLocation.get(0) / coordinateSectionNumber;
		y = newLocation.get(1) / coordinateSectionNumber;
		scooterNumberNew = sections.get(x).get(y);
		
		reward = (scooterNumberOld - scooterNumberNew) * REWARDS_PER_UNIT;
		
		if (reward > 0) {
			return reward;
		}
		return 0.;

	}
	
	
	
	
	
}
