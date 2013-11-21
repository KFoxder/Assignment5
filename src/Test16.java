import java.util.Iterator;

// ***********************************************************************
//
// Test15 -- Checks the iterator() doesn't iterate on a changed map
// 			 
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test16 extends TestHarness {

    public Test16(String s) { super(s); }

    public boolean test() { 
    	SortedMap<Character, String> m = new BSTMap<Character,String>();
	
	try {
		m.put('F',"1");
		m.put('B',"1");
		m.put('A',"1");
		m.put('C',"1");
		m.put('D',"1");
		m.put('X',"1");
		m.put('E',"1");
		
		System.out.println(m);
		Iterator<Character> iter = m.iterator();
		iter.remove();
		while(iter.hasNext()){
			iter.next();
			iter.remove();
		}
		System.out.println("");
		if(!m.toString().equals("")){
			System.out.println(m);
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
