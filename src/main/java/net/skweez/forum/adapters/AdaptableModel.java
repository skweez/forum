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
	 * @return true if an adapter exists. false else
	 */
	public boolean adapterExists();

	/**
	 * @return the class of the adapter.
	 */
	public Class<?> adapterClass();
}
