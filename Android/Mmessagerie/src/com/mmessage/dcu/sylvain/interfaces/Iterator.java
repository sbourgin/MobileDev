package com.mmessage.dcu.sylvain.interfaces;

public interface Iterator<E> {
	public boolean hasNext();
	public boolean hasPrevious();
	public E next();
	public E previous();
}
