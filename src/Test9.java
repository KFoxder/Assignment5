import java.util.Iterator;

// ***********************************************************************
//
// Test9 -- Checks the get() function and makes sure it just gets a value without mutating the structure
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test9 extends TestHarness {

    public Test9(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new BSTMap<Integer,String>();
	
	try {
		m.put(10, "Kevin");
		m.put(1, "Mark");
		m.put(2, "Noah");
		String test1 = m.get(10);
		String test2 = m.toString();
		System.out.println(test2);
		if(test1.equals("Kevin")&&test2.equals("1 Mark\n2 Noah\n10 Kevin\n")){
			return true;
		}
		else{
			return false;
		}
	
	}catch(Exception e){
		//Threw wrong exception. 
		return false;
	}
    }
}
