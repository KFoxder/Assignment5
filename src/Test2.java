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

public class Test2 extends TestHarness {

    public Test2(String s) { super(s); }

    public boolean test() { 
    	BSTMap<Character,Integer> m = new BSTMap<Character,Integer>();
	
	try {
		System.out.println("");
		m.put('F', 1);
		m.put('B', 1);
		m.put('A', 1);
		m.put('D', 1);
		m.put('C', 1);
		m.put('E', 1);
		m.put('G', 1);
		m.put('I', 1);
		m.put('H', 1);
		m.setIteratorDefault(BSTMap.VisitorOrder.BFS);

		Iterator<Character> iter = m.iterator();
		while(iter.hasNext()){
			Character c = iter.next();
			if(c.equals('F')){
				iter.remove();
			}
			System.out.println(c);

		}
		System.out.println(m);
		
		
	
	    return  false;
	} catch (Exception e){
		e.printStackTrace();
	    //if we catch an exception, something went wrong
	    return false;
	}
    }
}
