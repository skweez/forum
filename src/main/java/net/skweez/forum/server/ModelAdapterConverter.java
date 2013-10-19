/**
 * 
 */
package net.skweez.forum.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.skweez.forum.adapters.ModelAdapter;
import net.skweez.forum.model.AdaptableModel;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.converters.javabean.JavaBeanProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * @author elm
 * 
 */
public class ModelAdapterConverter extends JavaBeanConverter {

	/**
	 * @see JavaBeanConverter#JavaBeanConverter(Mapper)
	 * 
	 * @param mapper
	 *            a mapper
	 */
	public ModelAdapterConverter(Mapper mapper) {
		super(mapper);
	}

	/**
	 * @see JavaBeanConverter#JavaBeanConverter(Mapper, JavaBeanProvider)
	 * 
	 * @param mapper
	 *            a mapper
	 * @param beanProvider
	 *            a bean provider
	 */
	public ModelAdapterConverter(Mapper mapper, JavaBeanProvider beanProvider) {
		super(mapper, beanProvider);
	}

	/**
	 * Checks if the source object has an adapter available and uses this
	 * adapter.
	 * 
	 * @see com.thoughtworks.xstream.converters.javabean.JavaBeanConverter#marshal
	 *      (java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (source instanceof AdaptableModel
				&& ((AdaptableModel) source).adapterExists()) {
			try {
				Constructor<?> adapterConstructor = ((AdaptableModel) source)
						.adapterClass().getDeclaredConstructor(
								new Class[] { source.getClass() });

				source = adapterConstructor.newInstance(source);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO (elm): Add error handling (maybe ignore?)
				e.printStackTrace();
			}
		}

		super.marshal(source, writer, context);
	}

	/**
	 * Checks if the deserialized object fits to an adapter and extracts the
	 * actual object.
	 * 
	 * @see com.thoughtworks.xstream.converters.javabean.JavaBeanConverter#unmarshal
	 *      (com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Object object = super.unmarshal(reader, context);

		if (object instanceof ModelAdapter<?>) {
			object = ((ModelAdapter<?>) object).model();
		}

		return object;
	}

}
