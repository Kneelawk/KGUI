package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Consumer;

/**
 * A write only property that can be written to directly but not have its value read.
 *
 * @param <T> the type this property accepts.
 */
public interface WriteOnlyProp<T> extends Consumer<T> {
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
