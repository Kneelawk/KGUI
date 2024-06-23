package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A read only property contains data that cannot be written to directly. This could be because it is derived from
 * another property.
 *
 * @param <T> the type this property contains.
 */
public interface ReadOnlyProp<T> extends Supplier<T> {
    /**
     * {@return the current value contained in this property}
     */
    T get();

    /**
     * Force this property to re-calculate its value.
     * <p>
     * For properties that do not depend on other properties, this does nothing.
     */
    default void update() {}

    /**
     * Adds a change listener to this property that is called when this property's value changes.
     * <p>
     * The change listener is stored as a weak reference, meaning that this object will <b>not</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    void addWeakListener(Consumer<T> listener);

    /**
     * Adds a change listener to this property that is called when this property's value changes.
     * <p>
     * The change listener is stored as a direct reference, meaning that this object <b>will</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    void addStrongListener(Consumer<T> listener);

    /**
     * Removes a listener from this property.
     *
     * @param listener the listener to remove.
     */
    void removeListener(Object listener);
}
