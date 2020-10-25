public class LinearHashTable<K,V> {
	
	private int numOfKeys; // number of keys in ST
	private int capacity; // size of linear probing table
	private MaxHeap<K,V> MH;
	private CacheSlice<K,V>[] Hash;
	
	@SuppressWarnings("unchecked")
	LinearHashTable(int capacity) {
		MH = new MaxHeap<K,V>(capacity);
		this.capacity = 3*capacity;
		this.numOfKeys = 0;
		this.Hash = (CacheSlice<K,V>[]) new CacheSlice[this.capacity];
	}
	
	// Returns the current number of objects in the Hash Table
	public int size() {
		return numOfKeys;
	}
	
	// Checks if the Hash Table is empty, returns true if yes, false if not
	boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 *  This method searches the Hash Table, after hashing the key value given as parameter,
	 *  if the key is found, its value is returned, else null is returned.
	 *  If the key is found, then its time and heap get updated
	 * @param key , the key we search for
	 * @return The key's data, if the key was found, else null
	 */
	public V LookUp(K key){
		for (int i = hash(key); Hash[i] != null; i = (i + 1) % capacity){
			if (Hash[i].getKey().equals(key)){
	           	Hash[i].updateTime();
	           	MH.Heap[Hash[i].getIndex()].setTime(Hash[i].getTime());
	           	MH.sink(Hash[i].getIndex());
	           	return Hash[i].getValue();
	         }
		}
	    return null;
	}
	
	// Default hash method for generic type of keys
	private int hash(K key) {
        return (key.hashCode() & 0x7fffffff % capacity);
    }
	
	@SuppressWarnings("unchecked")
	// Calls the put() method, after creating a new CacheSlice object, using the K, V values given as parameters
	public void Store(K key, V value){
		@SuppressWarnings("rawtypes")
		CacheSlice cl = new CacheSlice(key,value);
		put(cl, true);
	}
	
	/**
	 * Inserts a CacheSlice object in the Hash Table and the MaxHeap, 
	 * while keeping track of the indexes (flag value is used as a guard value, 
	 * because put is also used in delete() method)
	 * @param element , the object to be put in the two data structures
	 * @param flag , true in every case, but the delete() one
	 */
	public void put(CacheSlice<K,V> element, boolean flag) {
		int x;
		if(this.numOfKeys != MH.getCap()) {
			++this.numOfKeys;
			int i;
			for (i = hash(element.getKey()); Hash[i] != null; i = (i + 1) % capacity) {}
			Hash[i] = element;
			if(flag) {
				x = MH.insert(element);
				Hash[i].setIndex(x);
				Hash[i].setTime(MH.Heap[x].getTime());
			}
			return;
		}
		delete(MH.Heap[1].getKey());
		put(element, true);
	}
	
	/**
	 *  Delete the node corresponding to the key value and rehashes the following keys
	 *  in the same cluster, all this while the next slot is not empty.
	 * @param key , the key to delete
	 */
	public void delete(K key) {
		int i = hash(key);
		while (!key.equals(Hash[i].getKey())) {
			i = (i + 1) % capacity;
		}
		Hash[i] = null;
		i = (i + 1) % capacity;
		while (Hash[i] != null) {
			numOfKeys--;
			put(Hash[i], false);
			Hash[i] = null;
			i = (i + 1) % capacity;
		}
		--numOfKeys;
	}

}