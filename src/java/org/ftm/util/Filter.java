package org.ftm.util;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @param <T> the type to filter
 * @since Sep 12, 2010
 */
public interface Filter<T> {

    boolean accept(T t);
}
