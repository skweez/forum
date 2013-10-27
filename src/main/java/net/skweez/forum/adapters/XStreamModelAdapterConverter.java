/**
 * 
 */
package net.skweez.forum.adapters;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.converters.javabean.JavaBeanProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * A converter that uses XStreamModelAdapter to transform model objects.
 * 
 * @author elm
 * 
 */
public class XStreamModelAdapterConverter extends JavaBeanConverter {

	/**
	 * @see JavaBeanConverter#JavaBeanConverter(Mapper)
	 * 
	 * @param mapper
	 *            a mapper
	 */
	public XStreamModelAdapterConverter(Mapper mapper) {
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
	public XStreamModelAdapterConverter(Mapper mapper,
			JavaBeanProvider beanProvider) {
		super(mapper, beanProvider);
	}

	/**
	 * Checks if the source object has an XStream adapter available and uses
	 * this adapter.
	 * 
	 * @see com.thoughtworks.xstream.converters.javabean.JavaBeanConverter#marshal
	 *      (java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if (source instanceof AdaptableModel) {
			Object adapter = ((AdaptableModel) source)
					.getAdapter(XStreamModelAdapter.class);
			if (adapter != null) {
				source = adapter;
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

		if (object instanceof XStreamModelAdapter<?>) {
			object = ((XStreamModelAdapter<?>) object).model();
		}

		return object;
	}

}
