package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Consumer;

import org.jetbrains.annotations.UnknownNullability;

/**
 * A property holds mutable data that optionally depends on other data.
 *
 * @param <T> the type of data this property holds.
 */
public interface WritableProp<T> extends Prop<T>, Consumer<T> {
    /**
     * Sets the value of this property.
     *
     * @param value the value to set. Note: whether this value is nullable depends on the nullability of the contained
     *              type.
     */
    void set(@UnknownNullability T value);

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
