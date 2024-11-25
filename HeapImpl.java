public class HeapImpl<T extends Comparable<? super T>> implements Heap<T> {
	private static final int INITIAL_CAPACITY = 127;
	private T[] _values;
	private int _numElements;

	@SuppressWarnings("unchecked")
	public HeapImpl() {
		_values = (T[]) new Comparable[INITIAL_CAPACITY];
		_numElements = 0;
	}

	@SuppressWarnings("unchecked")
	public void add(T data) {
		if (_numElements == _values.length -1) resizeUp();
		_values[_numElements] = data;
		int curIndex = _numElements;
		while (_values[curIndex].compareTo(_values[(curIndex - 1) / 2]) > 0) {
			swap(curIndex, (curIndex - 1)/2);
			curIndex = (curIndex - 1)/2;
		}
		_numElements++;
	}

	public T removeFirst() {
		T removed = _values[0];
		_values[0] = _values[--_numElements];
		int curIndex = 0;
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
	private boolean isLeaf (int index) {
		return (index >= _numElements/2);
	}



	public int size() {
		return _numElements;
	}
	private void resizeUp () {
		T[] _valuesTemp = (T[]) new Comparable[_numElements*2];
		for (int i = 0; i < _values.length; i++) {
			_valuesTemp[i] = _values[i];
		}
		_values = _valuesTemp;
	}
	private void swap (int i, int j) {
		T temp = _values[i];
		_values[i] = _values[j];
		_values[j] = temp;
	}
}