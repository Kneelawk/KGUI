package com.kneelawk.kgui.engine.api.prop;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/**
 * Holds an array of listeners.
 *
 * @param <T> the type the listeners listen for.
 */
public class ListenerSet<T> {
    private static final Object VALUE = new Object();

    private final List<ListenerRef<T>> listeners = new ArrayList<>();
    private final WeakHashMap<Object, Object> duplicateDetection = new WeakHashMap<>();

    /**
     * Creates a new listener reference array.
     */
    public ListenerSet() {}

    /**
     * Sends an updated value to all valid listeners.
     * <p>
     * This makes sure to remove any listeners that have been garbage-collected.
     *
     * @param value the value to send.
     */
    public void sendUpdate(T value) {
        for (var iter = listeners.iterator(); iter.hasNext(); ) {
            ListenerRef<T> ref = iter.next();
            Consumer<T> consumer = ref.get();
            if (consumer != null) {
                consumer.accept(value);
            } else {
                iter.remove();
            }
        }
    }

    /**
     * Removes a listener from the list of listeners.
     *
     * @param listener the listener to remove.
     */
    public void remove(Object listener) {
        for (var iter = listeners.iterator(); iter.hasNext(); ) {
            ListenerRef<T> ref = iter.next();
            Consumer<T> consumer = ref.get();
            if (consumer == null || consumer == listener) iter.remove();
        }
    }

    /**
     * Adds a change listener.
     * <p>
     * The change listener is stored as a weak reference, meaning that this object will <b>not</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    public void addWeak(Consumer<T> listener) {
        if (duplicateDetection.put(listener, VALUE) == null)
            listeners.add(new ListenerRef.Weak<>(listener));
    }

    /**
     * Adds a change listener.
     * <p>
     * The change listener is stored as a direct reference, meaning that this object <b>will</b> prevent the given
     * listener from being garbage-collected.
     *
     * @param listener the change listener.
     */
    public void addStrong(Consumer<T> listener) {
        if (duplicateDetection.put(listener, VALUE) == null)
            listeners.add(new ListenerRef.Strong<>(listener));
    }
}
