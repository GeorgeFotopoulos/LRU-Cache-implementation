public class CacheSlice<K,V> {
	
	private K key;
	private V value;
	private int index; // Holds the position of the object at the MaxHeap
	private long time; // Holds the timestamp value
	
	// timestamp: The representation of time which shows when the object was last processed.
	// The smaller the value, the more recently the object was processed.
	static long timestamp = Long.MAX_VALUE; 
	
	/**
	 *  Constructor of a cache slice, instantiates a CacheSlice object, 
	 *  setting its key, value, index and time values.
	 * @param key , the key of the object
	 * @param value , the value of this particular object
	 */
	public CacheSlice(K key, V value) {
		this.key = key;
		this.value = value;
		this.time = timestamp--;
		this.index = -1;
	}
	
	/**
	 *  Changes the time value of the object to the current timestamp
	 *  value and then subtracts one from the timestamp value.
	 */
	public void updateTime() {
		this.time = timestamp--;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public K getKey() {
		return this.key;
	}
	
}