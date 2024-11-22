public class HeapImpl<T extends Comparable<? super T>> implements Heap<T> {
	private static final int INITIAL_CAPACITY = 128;
	private T[] _storage;
	private int _numElements;

	@SuppressWarnings("unchecked")
	public HeapImpl() {
		_storage = (T[]) new Comparable[INITIAL_CAPACITY];
		_numElements = 0;
	}

	@SuppressWarnings("unchecked")
	public void add(T data) {
		if (_numElements == _storage.length) {
			resizeUp();
		}

		_storage[_numElements] = data;
		bubbleUp(_numElements);
		_numElements++;
	}

	public T removeFirst() {
		if (_numElements == 0) {
			return null;
		}

		T max = _storage[0];
		_storage[0] = _storage[_numElements - 1];
		_storage[_numElements - 1] = null;
		_numElements--;

		if (_numElements > 0) {
			bubbleDown(0);
		}

		//if(_storage.length >= _numElements * 2) {
			//resizeDown();
		//}
		return max;
	}

	public int size() {
		return _numElements;
	}

	private void bubbleUp(int index) {
		while(index > 0) {
			int parent = (index - 1) /2;

			if(_storage[index].compareTo(_storage[parent]) > 0) {
				swap(index, parent);
				index = parent;
			} else {
				break;
			}
		}
	}

	private void bubbleDown(int index) {
		while (index < _numElements) {
			int left = 2 * index;
			int right = 2 * index + 1;
			int largest = index;

			if (left < _numElements && _storage[left].compareTo(_storage[largest]) > 0) {
				largest = left;
			}

			if (right < _numElements && _storage[right].compareTo(_storage[largest]) > 0) {
				largest = right;
			}

			if(largest == index) {
				break;
			}

			swap(index, largest);
			index = largest;
		}
	}

	private void swap(int i, int j) {
		T temp = _storage[i];
		_storage[i] = _storage[j];
		_storage[j] = temp;
	}

	private void resizeUp() {
		T[] newStorage = (T[]) new Comparable[_storage.length * 2];
		System.arraycopy(_storage, 0, newStorage, 0, _storage.length);
		_storage = newStorage;
	}

	private void resizeDown() {
		T[] newStorage = (T[]) new Comparable[_storage.length / 2];
		System.arraycopy(_storage, 0, newStorage, 0, _storage.length);
		_storage = newStorage;
	}
}
