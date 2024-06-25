package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Consumer;

/**
 * A property holds mutable data that optionally depends on other data.
 *
 * @param <T> the type of data this property holds.
 */
public interface WritableProp<T> extends Prop<T>, Consumer<T> {
    /**
     * Sets the value of this property.
     *
     * @param value the value to set.
     */
    void set(T value);

    /**
     * Alias for {@link #set(Object)}.
     *
     * @param t the input argument
     */
    @Override
    default void accept(T t) {
        set(t);
    }
}
