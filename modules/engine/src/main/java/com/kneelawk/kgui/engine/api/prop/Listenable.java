package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Consumer;

/**
 * Describes something that can have listeners attached to it.
 *
 * @param <T> the type the listeners return.
 */
public interface Listenable<T> {
    /**
     * Adds a change listener to this listenable that is called when this listenable's value changes.
     * <p>
     * The change listener is stored as a weak reference, meaning that this object will <b>not</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    void addWeakListener(Consumer<T> listener);

    /**
     * Adds a change listener to this listenable that is called when this listenable's value changes.
     * <p>
     * The change listener is stored as a direct reference, meaning that this object <b>will</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    void addStrongListener(Consumer<T> listener);

    /**
     * Removes a listener from this listenable.
     *
     * @param listener the listener to remove.
     */
    void removeListener(Object listener);
}
