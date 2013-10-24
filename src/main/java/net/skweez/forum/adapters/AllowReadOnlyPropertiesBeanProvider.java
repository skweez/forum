/**
 * 
 */
package net.skweez.forum.adapters;

import java.beans.PropertyDescriptor;

import com.thoughtworks.xstream.converters.javabean.BeanProvider;

/**
 * A custom bean provider that allows the serialization of members where only a
 * getter exists.
 * 
 * @author elm
 * 
 */
public class AllowReadOnlyPropertiesBeanProvider extends BeanProvider {
	@Override
	protected boolean canStreamProperty(PropertyDescriptor descriptor) {
		return descriptor.getReadMethod() != null;
	}
}
