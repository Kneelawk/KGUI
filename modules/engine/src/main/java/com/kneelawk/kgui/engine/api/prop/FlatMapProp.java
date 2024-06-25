package com.kneelawk.kgui.engine.api.prop;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

/**
 * A simple property that depends on other properties' properties.
 *
 * @param <T> the value this property contains.
 */
public class FlatMapProp<T> implements Prop<T> {
    private @Nullable Prop<T> prop = null;
    private @Nullable T value = null;
    private final Supplier<Prop<T>> propSupplier;
    private final Runnable updater;
    private final ListenerSet<T> listeners = new ListenerSet<>();
    private final Supplier<String> toStringSupplier;

    /**
     * Creates a new {@link FlatMapProp} with a single dependency.
     *
     * @param dependency the single dependency.
     * @param converter  the dependency converter.
     * @param <S>        the type the dependency provides.
     */
    public <S> FlatMapProp(Prop<S> dependency, Function<S, Prop<T>> converter) {
        Objects.requireNonNull(dependency, "dependency cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        propSupplier = () -> Objects.requireNonNull(converter.apply(dependency.get()),
            "the converter function cannot return a null property");

        Consumer<T> tListener = t -> {
            final T oldValue = value;
            value = t;

            if (!Objects.equals(oldValue, value)) {
                listeners.sendUpdate(value);
            }
        };

        Consumer<S> sListener = s -> {
            final Prop<T> oldProp = prop;
            prop = Objects.requireNonNull(converter.apply(s), "the converter function cannot return a null property");

            if (!prop.equals(oldProp)) {
                final T oldValue = value;
                value = prop.get();

                if (oldProp != null) oldProp.removeListener(tListener);
                prop.addWeakListener(tListener);

                if (!Objects.equals(oldValue, value)) {
                    listeners.sendUpdate(value);
                }
            }
        };
        updater = () -> sListener.accept(dependency.get());

        dependency.addWeakListener(sListener);

        toStringSupplier = () -> "dependency=" + dependency + ", converter=" + converter;
    }

    /**
     * Creates a new {@link FlatMapProp} with multiple dependencies.
     *
     * @param dependencies the list of dependencies.
     * @param propSupplier the function that uses the dependencies to get the new value.
     */
    public FlatMapProp(List<Object> dependencies, Supplier<Prop<T>> propSupplier) {
        Objects.requireNonNull(dependencies, "dependencies cannot be null");
        Objects.requireNonNull(propSupplier, "propSupplier cannot be null");

        this.propSupplier =
            () -> Objects.requireNonNull(propSupplier.get(), "the propSupplier function cannot return a null property");

        Consumer<T> tListener = t -> {
            final T oldValue = value;
            value = t;

            if (!Objects.equals(oldValue, value)) {
                listeners.sendUpdate(value);
            }
        };

        updater = () -> {
            final Prop<T> oldProp = prop;
            prop =
                Objects.requireNonNull(propSupplier.get(), "the propSupplier function cannot return a null property");

            if (!prop.equals(oldProp)) {
                final T oldValue = value;
                value = prop.get();

                if (oldProp != null) oldProp.removeListener(tListener);
                prop.addWeakListener(tListener);

                if (!Objects.equals(oldValue, value)) {
                    listeners.sendUpdate(value);
                }
            }
        };

        for (Object dep : dependencies) {
            if (dep instanceof Listenable<?> prop) prop.addWeakListener(o -> updater.run());
        }

        toStringSupplier = () -> "dependencies=" + dependencies + ", propSupplier=" + propSupplier;
    }

    @Override
    public T get() {
        if (prop == null) {
            prop = propSupplier.get();
            value = prop.get();
        }

        assert value != null;
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

    @Override
    public String toString() {
        return "FlatMapProp{" + toStringSupplier + "}";
    }
}
