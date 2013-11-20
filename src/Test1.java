import java.util.Iterator;

// ***********************************************************************
//
// Test1 -- An example test : confirms that the set is initially of size 0
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test1 extends TestHarness {

    public Test1(String s) { super(s); }

    public boolean test() { 
	SortedMap<Integer, String> m = new BSTMap<Integer,String>();
	
	try {
		m.put(10, "Jason Kid");
		m.put(11, "Kevin");
		m.put(10, "New Jason Kid");
		m.put(100, "ME");
		m.put(0, "HELLO");
		m.put(1, "J");
		m.put(33, "NO");
		m.put(3, "HEY");
		//System.out.print(m.toString());
		SortedMap<Integer,Integer> map = m.calculateStats();
		Iterator<Integer> iter = map.iterator();
		while(iter.hasNext()){
			
			int level = iter.next();
			int freq = map.get(level);
			System.out.println(level+" : "+freq);
		}
		
	    return true;
	} catch (Exception e){
		System.out.println("Error Here!");
		e.printStackTrace();
	    return false;
	}
    }
}
