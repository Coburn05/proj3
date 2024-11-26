public class HeapImpl<T extends Comparable<? super T>> implements Heap<T> {
	private static final int INITIAL_CAPACITY = 127;
	private T[] _values;
	private int _numElements;

	@SuppressWarnings("unchecked")
	/**
	 * constructor for the heap, starts with an initial length of INITIAL_CAPACITY
	 * @return heap
	 */
	public HeapImpl() {
		_values = (T[]) new Comparable[INITIAL_CAPACITY];
		_numElements = 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * adds a new value to the heap
	 * @param data, this is the data that the node should store
	 * @return void
	 */
	public void add(T data) {
		if (_numElements == _values.length -1) resizeUp();
		_values[_numElements] = data;
		int curIndex = _numElements;
		//bubbles up while the heap is invalid
		while (_values[curIndex].compareTo(_values[(curIndex - 1) / 2]) > 0) {
			swap(curIndex, (curIndex - 1)/2);
			curIndex = (curIndex - 1)/2;
		}
		_numElements++;
	}

	/**
	 * removes the root value of the entire heap
	 * @return root value
	 */
	public T removeFirst() {
        if (_numElements == 0) return null;
		T removed = _values[0];
		_values[0] = _values[--_numElements];
		int curIndex = 0;
		//while element is not a leaf node or is outside of array bounds bubbles down
		while (!isLeaf(curIndex)) {
			T at = _values[curIndex];
			T left = _values[2*curIndex + 1];
			T right = _values[2*curIndex + 2];
			if ((left != null && at.compareTo(left) < 0) || (right != null && at.compareTo(right) < 0)) {
				if (left.compareTo(right) < 0) {
					swap (curIndex, 2*curIndex + 2);
					curIndex = 2*curIndex + 2;
				} else {
					swap (curIndex, 2*curIndex + 1);
					curIndex = 2*curIndex + 1;
				}
			} else {
				break;
			}
		}
		return removed;
	}

	/**
	 * checks if the value at the given index a leaf
	 * @param index, index to check for being a leaf
	 * @return boolean, is the value at the given index a leaf
	 */
	private boolean isLeaf (int index) {
		return (index >= _numElements/2);
	}

	/**
	 * gets the size of the heap
	 * @return int, number of elements in the heap
	 */
	public int size() {
		return _numElements;
	}

	/**
	 * doubles the size of the heap in memeory
	 * @return void
	 */
	private void resizeUp () {
		T[] _valuesTemp = (T[]) new Comparable[_numElements*2];
		for (int i = 0; i < _values.length; i++) {
			_valuesTemp[i] = _values[i];
		}
		_values = _valuesTemp;
	}

	/**
	 * swaps i and j in the heap
	 * @param i
	 * @param j
	 * @return void
	 */
	private void swap (int i, int j) {
		T temp = _values[i];
		_values[i] = _values[j];
		_values[j] = temp;
	}
}
