import java.util.Iterator;

// ***********************************************************************
//
// Test11 -- Checks the remove() function
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test11 extends TestHarness {

    public Test11(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new BSTMap<Integer,String>();
	
	try {
		m.put(10, "Kevin");
		m.put(1, "Mark");
		m.put(2, "Noah");
		m.put(12, "Got");
		m.remove(4);
		if(!m.toString().equals("1 Mark\n2 Noah\n10 Kevin\n12 Got\n")){
			System.out.println("False here -1");
			return false;
		}
		System.out.println(m);
		boolean test1 = m.remove(10);
		String test2 = m.get(10);
		if(test1!=true || !m.toString().equals("1 Mark\n2 Noah\n12 Got\n") || test2!=null){
			System.out.println(m);
			System.out.println("False here -2");
			System.out.println(test1+" "+test2);
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
