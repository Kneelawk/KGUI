package com.kneelawk.kgui.engine.api.prop;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

/**
 * A simple property that depends on other properties.
 *
 * @param <T> the value this property contains.
 */
public class SimpleDependentProp<T> implements Prop<T> {
    private boolean initialized = false;
    private @Nullable T value;
    private final Supplier<T> valueSupplier;
    private final Runnable updater;
    private final ListenerSet<T> listeners = new ListenerSet<>();

    /**
     * Creates a new {@link SimpleDependentProp} with a single dependency.
     *
     * @param dependency the single dependency.
     * @param converter  the dependency converter.
     * @param <S>        the type the dependency provides.
     */
    public <S> SimpleDependentProp(Prop<S> dependency, Function<S, T> converter) {
        valueSupplier = () -> converter.apply(dependency.get());

        Consumer<S> listener = s -> {
            final T oldValue = value;
            initialized = true;
            value = converter.apply(s);

            if (!Objects.equals(oldValue, value)) {
                listeners.sendUpdate(value);
            }
        };
        updater = () -> listener.accept(dependency.get());

        dependency.addWeakListener(listener);
    }

    /**
     * Creates a new {@link SimpleDependentProp} with multiple dependencies.
     *
     * @param dependencies  the list of dependencies.
     * @param valueSupplier the function that uses the dependencies to get the new value. Note: this should hold strong
     *                      references to all dependencies.
     */
    public SimpleDependentProp(List<Object> dependencies, Supplier<T> valueSupplier) {
        this.valueSupplier = valueSupplier;

        updater = () -> {
            final T oldValue = value;
            initialized = true;
            value = valueSupplier.get();

            if (!Objects.equals(oldValue, value)) {
                listeners.sendUpdate(value);
            }
        };

        for (Object dep : dependencies) {
            if (dep instanceof Listenable<?> prop) prop.addWeakListener(o -> updater.run());
        }
    }

    @Override
    public T get() {
        if (!initialized) value = valueSupplier.get();
        return value;
    }

    @Override
    public void update() {
        updater.run();
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
}
