package com.kneelawk.kgui.engine.api.prop;

/**
 * A property holds mutable data that optionally depends on other data.
 *
 * @param <T> the type of data this property holds.
 */
public interface Prop<T> extends ReadOnlyProp<T>, WriteOnlyProp<T> {
}
