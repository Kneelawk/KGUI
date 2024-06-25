package com.kneelawk.kgui.engine.api.prop;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Simple property that holds a value that can be changed.
 *
 * @param <T> the type of value.
 */
public class SimpleWritableProp<T> implements WritableProp<T> {
    private T value;
    private final ListenerSet<T> listeners = new ListenerSet<>();

    /**
     * Creates a new {@link SimpleWritableProp} with the given initial value.
     *
     * @param value this property's initial value.
     */
    public SimpleWritableProp(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        T oldValue = this.value;
        this.value = value;

        if (!Objects.equals(oldValue, value)) {
            listeners.sendUpdate(value);
        }
    }

    @Override
    public void addWeakListener(Consumer<T> listener) {
        listeners.addWeak(listener);
    }

    @Override
    public void addStrongListener(Consumer<T> listener) {
        listeners.addStrong(listener);
    }

    @Override
    public void removeListener(Object listener) {
        listeners.remove(listener);
    }

    @Override
    public String toString() {
        return "SimpleWritableProp{" + value + "}";
    }
}
