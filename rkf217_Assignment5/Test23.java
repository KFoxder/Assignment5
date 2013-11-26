import java.util.Iterator;

// ***********************************************************************
//
// Test8 -- Checks the put() function
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test23 extends TestHarness {

    public Test23(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new SkiplistMap<Integer,String>();
	
	try {
		m.put(1, "Kevin");
		String test1 = m.get(1);
		m.put(1, "Mark");
		String test2 = m.get(1);
		m.put(3, "Joanna");
		String test3 = m.get(3);
		m.put(10, "Kevin");
		String test4 = m.get(10);
		if(test1.equals("Kevin")&&test2.equals("Mark")&&test3.equals("Joanna")&&test4.equals("Kevin")&&m.toString().equals("1 Mark\n3 Joanna\n10 Kevin\n")){
			return true;
		}else{
			return false;
		}
		
	}catch(Exception e){
		//Threw wrong exception. 
		return false;
	}
    }
}
