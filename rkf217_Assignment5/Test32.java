import java.util.Iterator;

// ***********************************************************************
//
// Test17 -- Checks the iterator() 
// 			 
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test32 extends TestHarness {

    public Test32(String s) { super(s); }

    public boolean test() { 
    	SortedMap<Character, String> m = new SkiplistMap<Character,String>();
	
	try {
		m.put('F',"F");
		m.put('B',"B");
		m.put('A',"A");
		m.put('C',"C");
		m.put('D',"D");
		m.put('X',"X");
		m.put('E',"E");
		
		Iterator<Character> iter = m.iterator();
		iter.next();
		iter.remove();
		if(!m.toString().equals("B B\nC C\nD D\nE E\nF F\nX X\n")){
			return false;
		}
		iter.next();
		iter.remove();
		if(!m.toString().equals("C C\nD D\nE E\nF F\nX X\n")){
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
