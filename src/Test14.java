import java.util.Iterator;

// ***********************************************************************
//
// Test14 -- Checks the iterator() return on empty and that it iterators correctly
// 			 inorder.
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class Test14 extends TestHarness {

    public Test14(String s) { super(s); }

    public boolean test() { 
	SortedMap<Character, String> m = new BSTMap<Character,String>();
	
	try {
		Iterator<Character> iter = m.iterator();
		iter.remove();
		if(iter.hasNext()!=false || iter.next()!=null){
			return false;
		}
		m.put('F',"1");
		m.put('B',"1");
		m.put('A',"1");
		m.put('D',"1");
		m.put('E',"1");
		
		Iterator<Character> iter2 = m.iterator();
		if(iter2.hasNext()!=true || iter.hasNext()!=false){
			return false;
		}
		String test1 = "";
		while(iter2.hasNext()){
			Character c = iter2.next();
			System.out.print(c);
			test1+=c;
		}
		if(!test1.equals("ABDEF")){
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
