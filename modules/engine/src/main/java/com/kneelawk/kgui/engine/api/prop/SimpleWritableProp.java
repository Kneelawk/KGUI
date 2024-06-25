package com.kneelawk.kgui.engine.api.prop;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.UnknownNullability;

/**
 * Simple property that holds a value that can be changed.
 *
 * @param <T> the type of value.
 */
public class SimpleWritableProp<T> implements WritableProp<T> {
    private @UnknownNullability T value;
    private final ListenerSet<T> listeners = new ListenerSet<>();

    /**
     * Creates a new {@link SimpleWritableProp} with the given initial value.
     *
     * @param value this property's initial value.
     */
    private SimpleWritableProp(@UnknownNullability T value) {
        this.value = value;
    }

    @Override
    public @UnknownNullability T get() {
        return value;
    }

    @Override
    public void set(@UnknownNullability T value) {
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

    /**
     * Creates a new {@link SimpleWritableProp} with an initial value.
     *
     * @param initialValue the initial value for the new property.
     * @param <T>          type the property holds.
     * @return the created property.
     */
    public static <T> SimpleWritableProp<T> of(@UnknownNullability T initialValue) {
        return new SimpleWritableProp<>(initialValue);
    }
}
