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

public class Test29 extends TestHarness {

    public Test29(String s) { super(s); }

    public boolean test() { 
	SortedMap<Character, String> m = new SkiplistMap<Character,String>();
	
	try {
		boolean test2 = false;
		boolean test3 = false;
		boolean test4 = false;
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
		
	
		try{
			iter.hasNext();
		}catch(RuntimeException e){
			test2 = true;
		}
		if(!test2){
			return false;
		}
		try{
			iter.next();
		}catch(RuntimeException e){
			test3 = true;
		}
		if(!test3){
			return false;
		}
		try{
			iter.remove();
		}catch(RuntimeException e){
			test4 = true;
		}
		if(!test4){
			return false;
		}
		Iterator<Character> iter2 = m.iterator();
		if(iter2.hasNext()!=true){
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
