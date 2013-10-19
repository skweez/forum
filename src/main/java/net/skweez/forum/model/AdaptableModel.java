/**
 * 
 */
package net.skweez.forum.model;

/**
 * @author elm
 * 
 */
public interface AdaptableModel {
	/**
	 * @return true if an adapter exists. false else
	 */
	public boolean adapterExists();

	/**
	 * @return the class of the adapter.
	 */
	public Class<?> adapterClass();
}
