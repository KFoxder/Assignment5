import java.util.Iterator;

// ***********************************************************************
//
// Test13 -- Checks the calculateStats() function
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test28 extends TestHarness {

    public Test28(String s) { super(s); }

    public boolean test() { 
	SortedMap<Character, String> m = new SkiplistMap<Character,String>();
	
	try {
		SortedMap<Integer,Integer> test1 = m.calculateStats();
		m.put('F',"1");
		m.put('B',"1");
		m.put('A',"1");
		m.put('D',"1");
		m.put('C',"1");
		m.put('E',"1");
		m.put('G',"1");
		m.put('I',"1");
		m.put('H',"1");
		SortedMap<Integer,Integer> test2 = m.calculateStats();
		return true;

	}catch(Exception e){
		//Threw wrong exception.
		e.printStackTrace();
		return false;
	}
    }
}
