package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.Setter;

import java.lang.reflect.Field;

/**
 * {@link Setter} that sets to a {@link Field}.
 *
 * @author Kohsuke Kawaguchi
 */
final class FieldSetter implements Setter {
    private final Field f;
    private final Object bean;

    public FieldSetter(Object bean, Field f) {
        this.bean = bean;
        this.f = f;
    }

    public Class getType() {
        return f.getType();
    }
    
    public boolean isMultiValued() {
    	return false;
    }

    public void addValue(Object value) {
        try {
            f.set(bean,value);
        } catch (IllegalAccessException e) {
            // try again
            f.setAccessible(true);
            try {
                f.set(bean,value);
            } catch (IllegalAccessException e2) {
                throw new IllegalAccessError(e2.getMessage());
            }
        }
    }
}
