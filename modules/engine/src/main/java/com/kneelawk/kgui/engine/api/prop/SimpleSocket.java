package com.kneelawk.kgui.engine.api.prop;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

/**
 * Simple {@link Socket} implementation.
 *
 * @param <T> the type held in the properties in this socket.
 */
public class SimpleSocket<T> implements Socket<T> {
    private Prop<T> prop;
    private final ListenerSet<T> listeners = new ListenerSet<>();
    private final Consumer<T> internalListener = listeners::sendUpdate;

    private SimpleSocket(Prop<T> prop) {
        Objects.requireNonNull(prop, "prop cannot be null");
        this.prop = prop;
        prop.addWeakListener(internalListener);
    }

    @Override
    public void set(@UnknownNullability T value) {
        // event is propagated through the property
        if (prop instanceof WritableProp<T> writableProp) writableProp.set(value);
    }

    @Override
    public @UnknownNullability T get() {
        return prop.get();
    }

    @Override
    public void updateValue() {
        prop.update();
    }

    @Override
    public Prop<T> getProp() {
        return prop;
    }

    @Override
    public @Nullable WritableProp<T> getWritableProp() {
        if (prop instanceof WritableProp<T> writableProp) return writableProp;
        return null;
    }

    @Override
    public void setProp(Prop<T> prop) {
        Objects.requireNonNull(prop, "prop cannot be null");
        Prop<T> oldProp = this.prop;
        this.prop = prop;

        oldProp.removeListener(internalListener);
        prop.addWeakListener(internalListener);
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
        return "SimpleSocket{" + prop + "}";
    }

    /**
     * Creates a new {@link SimpleSocket} with the given property.
     *
     * @param prop the property contained within the new socket.
     * @param <T>  the type held within the new socket's property.
     * @return the created socket.
     */
    public static <T> SimpleSocket<T> of(Prop<T> prop) {
        return new SimpleSocket<>(prop);
    }

    /**
     * Creates a new {@link SimpleSocket} with a writable property containing the given initial value.
     * 
     * Note: th
     *
     * @param initialValue the initial value of the property.
     * @param <T>          the type held within the new socket's property.
     * @return the created socket.
     */
    public static <T> SimpleSocket<T> ofWritable(@UnknownNullability T initialValue) {
        return new SimpleSocket<>(SimpleWritableProp.of(initialValue));
    }
}
