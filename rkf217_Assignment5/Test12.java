import java.util.Iterator;

// ***********************************************************************
//
// Test12 -- Checks the remove() function
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test12 extends TestHarness {

    public Test12(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new BSTMap<Integer,String>();
	
	try {
		m.put(10, "Kevin");
		m.put(1, "Mark");
		m.put(2, "Noah");
		m.put(12, "Got");
		m.remove(1);
		m.remove(2);
		m.remove(12);
		m.remove(10);
		if(!m.toString().equals("")||m.get(1)!=null||m.get(2)!=null||m.get(10)!=null||m.get(12)!=null){
			return false;
		}
		return true;
	}catch(Exception e){
		//Threw wrong exception.
		e.printStackTrace();
		return false;
	}
    }
}
