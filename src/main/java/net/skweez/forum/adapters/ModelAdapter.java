/**
 * 
 */
package net.skweez.forum.adapters;

/**
 * Abstact class for all model adapters. A model adapter controls the access to
 * the members of the model by only implementing the setters and the getters
 * that should be serialized and deserialized by XStream. By only implementing a
 * setter the member is write-only and by only implementing a getter a member is
 * read-only.
 * 
 * @author elm
 * 
 */
public abstract class ModelAdapter<Model extends AdaptableModel> {
	/** the model */
	protected Model model;

	/**
	 * @return the model
	 */
	public Model model() {
		return model;
	}
}
