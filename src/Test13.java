import java.util.Iterator;

// ***********************************************************************
//
// Test13 -- Checks the remove() function
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test13 extends TestHarness {

    public Test13(String s) { super(s); }

    public boolean test() { 
	SortedMap<Character, String> m = new BSTMap<Character,String>();
	
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
		if(!test1.toString().equals("")||!test2.toString().equals("1 1\n2 2\n3 3\n4 3\n")){
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
