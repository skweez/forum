/**
 * 
 */
package net.skweez.forum.server;

import java.beans.PropertyDescriptor;

import com.thoughtworks.xstream.converters.javabean.BeanProvider;

/**
 * @author elm
 * 
 */
public class AllowReadOnlyPropertiesBeanProvider extends BeanProvider {
	@Override
	protected boolean canStreamProperty(PropertyDescriptor descriptor) {
		return descriptor.getReadMethod() != null;
	}
}
