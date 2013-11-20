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

public class Test15 extends TestHarness {

    public Test15(String s) { super(s); }

    public boolean test() { 
	SortedMap<Character, String> m = new BSTMap<Character,String>();
	
	try {
		m.put('F',"1");
		m.put('B',"1");
		m.put('A',"1");
		m.put('D',"1");
		m.put('E',"1");
		Iterator<Character> iter = m.iterator();
		m.remove('A');
		if(iter.hasNext()!=false || iter.next()!=null){
			return false;
		}
		Iterator<Character> iter2 = m.iterator();
		m.put('A',"2");
		if(iter2.hasNext()!=false || iter2.next()!=null){
			return false;
		}
		Iterator<Character> iter3 = m.iterator();
		m.get('A');
		if(iter3.hasNext()!=true){
			return false;
		}
		Iterator<Character> iter4 = m.iterator();
		String test1 ="";
		while(iter4.hasNext()){
			Character c = iter4.next();
			System.out.println(c);
			if(c.equals('D')){
				m.remove(c);
			}
			test1+=c;
		}

		if(!test1.equals("ABD")){
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
