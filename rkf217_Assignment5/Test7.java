import java.util.Iterator;

// ***********************************************************************
//
// Test7 -- Checks if a remove() throws the right exceptions
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test7 extends TestHarness {

    public Test7(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new BSTMap<Integer,String>();
	
	try {
		m.remove(null);
	    return false;
	} catch (SortedMapException e){
	    return true;
	}catch(Exception e){
		//Threw wrong excpetion. 
		return false;
	}
    }
}
