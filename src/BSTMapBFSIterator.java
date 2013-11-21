// ***********************************************************************
//
	// BSTMapInorderIterator: The inorder iterator for the BST Map
//
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************
import java.util.Iterator;
import java.util.LinkedList;


public class BSTMapBFSIterator<K extends Comparable<K>> implements Iterator<K> {

	private BSTMapNode<K, Object> curNode;
	private BSTMap<K,Object> map;
	private LinkedList<BSTMapNode<K,Object>> BFSorderList;
	private Integer version;

	//Constructor
	public BSTMapBFSIterator(BSTMap<K,?> map){
		if(map!=null){
			if(map.getRoot()!=null){
				this.map = (BSTMap<K, Object>) map;
				this.BFSorderList = map.getRoot().getBFSorderedListNode( new LinkedList<BSTMapNode<K,Object>>());
				this.version = map.version;
			}
		}
	}
	@Override
	public boolean hasNext() {
		if(!BFSorderList.isEmpty() && versionSame()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public K next() {
		if(!BFSorderList.isEmpty() && versionSame()){
			BSTMapNode<K,Object> temp = BFSorderList.remove();
			this.curNode = temp;	
			return temp.getKey();
		}else{
			//Throw exception? 
			return null;
		}
	}

	@Override
	public void remove() {
		if(versionSame()){
			if(curNode!=null){
				if(curNode.isLeaf()&&curNode.getParent()!=null){
					//case 2:
					if(curNode == curNode.getParent().getLeft()){
						curNode.getParent().setLeft(null);
					}else{
						curNode.getParent().setRight(null);
					}
				} else if(curNode.getParent()==null){
					map.setRoot(null);
				}else if(curNode.getLeft() == null){
					//case 3
					curNode.getRight().shiftNodeUp();
				} else if(curNode.getRight() == null){
					//case 3
					curNode.getLeft().shiftNodeUp();
				} else {
					//case 4
					//We know that there is some rightmost decendent
					BSTMapNode<K,Object> rightmost = curNode.getLeft().getRightMostNode();
					//updating in place makes it easier to handle the case of the root
					curNode.setValue(rightmost.getValue());
					curNode.setKey(rightmost.getKey());
					//now we need to deal with the rightmost node
					//and its children
					if(rightmost.isLeaf()){
						//case 4a
						if(rightmost == rightmost.getParent().getLeft()){
							rightmost.getParent().setLeft(null);
						}else{
							rightmost.getParent().setRight(null);
						}
					} else {
						//case 4b
						rightmost.getLeft().shiftNodeUp();
					}
				}

			}
		}else{
			throw new RuntimeException("Iterator has invalid version");
		}
	}
	private boolean versionSame(){
		if(this.version == this.map.getVersion() ){
			return true;
		}else{
			
			return false;
		}
	}


}
