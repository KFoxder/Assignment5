import java.util.Iterator;

// ***********************************************************************
//
// Test11 -- Checks the remove() and get() function on empty map
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test25 extends TestHarness {

    public Test25(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new SkiplistMap<Integer,String>();
	
	try {
		boolean test1 = m.remove(1);
		String test2 = m.get(1);
		if(test1==false &&test2==null){
			return true;
		}
		return false;
	}catch(Exception e){
		//Threw wrong exception. 
		return false;
	}
    }
}
