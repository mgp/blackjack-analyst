/*
 * Copyright Michael Parker (michael.g.parker@gmail.com).
 * 
 * This file is part of Blackjack Analyst.
 * 
 * Blackjack Analyst is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * Blackjack Analyst is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Blackjack Analyst; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package blackjackanalyst;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A list of observers.
 * 
 * @author Michael Parker
 * 
 * @param <T> the type of observer in the list
 */
public class ObserverList<T> implements Iterable<T> {
	protected List<T> observers;

	protected ObserverList() {
		observers = new LinkedList<T>();
	}

	/**
	 * Returns the number of observers in the list.
	 * 
	 * @return the size of the observer list
	 */
	public int size() {
		return observers.size();
	}

	/**
	 * Returns whether the observer list is empty, meaning {@link #size()} returns
	 * {@code 0}.
	 * 
	 * @return {@code true} if the observer list is empty, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return observers.isEmpty();
	}

	/**
	 * Returns whether the list contains the given observer. If the parameter is
	 * {@code null}, this method returns {@code false}.
	 * 
	 * @param observer the observer to query for membership in the list
	 * @return {@code true} if the list contains the observer, {@code false}
	 *         otherwise
	 */
	public boolean contains(T observer) {
		return (observer != null) ? observers.contains(observer) : false;
	}

	/**
	 * Returns an iterator over the list of observers.
	 * 
	 * @return an iterator over the observer list
	 */
	public Iterator<T> iterator() {
		return observers.iterator();
	}

	/**
	 * Adds the given observer to the list of observers. If the parameter is
	 * {@code null}, this method returns {@code false} and the underlying list
	 * remains unchanged.
	 * 
	 * @param observer the observer to add to the list
	 * @return {@code true} if the observer is added to the list, {@code false}
	 *         otherwise
	 */
	public boolean add(T observer) {
		return (observer != null) ? observers.add(observer) : false;
	}

	/**
	 * Removes the given observer from the list of observers. If the parameter is
	 * {@code null} or the list does not contain the observer, this method returns
	 * {@code false} and the underlying list remains unchanged.
	 * 
	 * @param observer the observer to remove from the list
	 * @return {@code true} if the observer is removed from the list,
	 *         {@code false} otherwise
	 */
	public boolean remove(T observer) {
		return (observer != null) ? observers.remove(observer) : true;
	}

	/**
	 * Removes all the observers from the list.
	 */
	public void clear() {
		observers.clear();
	}
}
