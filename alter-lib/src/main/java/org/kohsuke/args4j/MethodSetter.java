package org.kohsuke.args4j;

import org.kohsuke.args4j.spi.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link Setter} that sets to a {@link Method}.
 *
 * @author Kohsuke Kawaguchi
 */
final class MethodSetter implements Setter {
    private final Object bean;
    private final Method m;

    public MethodSetter(Object bean, Method m) {
        this.bean = bean;
        this.m = m;
        if(m.getParameterTypes().length!=1)
            throw new IllegalAnnotationError(Messages.ILLEGAL_METHOD_SIGNATURE.format(m));
    }

    public Class getType() {
        return m.getParameterTypes()[0];
    }

    public boolean isMultiValued() {
    	return false;
    }

    public void addValue(Object value) throws CmdLineException {
        try {
            try {
                m.invoke(bean,value);
            } catch (IllegalAccessException e) {
                // try again
                m.setAccessible(true);
                try {
                    m.invoke(bean,value);
                } catch (IllegalAccessException e2) {
                    throw new IllegalAccessError(e2.getMessage());
                }
            }
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if(t instanceof RuntimeException)
                throw (RuntimeException)t;
            if(t instanceof Error)
                throw (Error)t;
            if(t instanceof CmdLineException)
                throw (CmdLineException)t;

            // otherwise wrap
            if(t!=null)
                throw new CmdLineException(t);
            else
                throw new CmdLineException(e);
        }
    }
}
