/**
 * 
 */
package net.skweez.forum.adapters;

import net.skweez.forum.model.AdaptableModel;

/**
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
