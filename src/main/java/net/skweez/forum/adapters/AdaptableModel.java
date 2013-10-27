/**
 * 
 */
package net.skweez.forum.adapters;

/**
 * Interface that all models must implement if there should be an adapter for
 * it.
 * 
 * @author elm
 * 
 */
public interface AdaptableModel {
	/**
	 * Returns an object which is an instance of the given class associated with
	 * this object. Returns null if no such object can be found.
	 * 
	 * @param adapterClass
	 *            - the adapter class to look up
	 * @return a object castable to the given class, or null if this object does
	 *         not have an adapter for the given class
	 */
	public Object getAdapter(Class<?> adapterClass);
}
