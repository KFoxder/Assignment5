import java.util.Iterator;

// ***********************************************************************
//
// Test3 -- Checks if a put throws the right exceptions
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test19 extends TestHarness {

    public Test19(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new SkiplistMap<Integer,String>();
	
	try {
		m.put(1, null);
	    return false;
	} catch (SortedMapException e){
	
	    return true;
	}catch(Exception e){
		//Threw wrong excpetion. 
		return false;
	}
    }
}
