package interfaces;

public interface Iterator<E> {
	public boolean hasNext();
	public boolean hasPrevious();
	public E next();
	public E previous();
}
