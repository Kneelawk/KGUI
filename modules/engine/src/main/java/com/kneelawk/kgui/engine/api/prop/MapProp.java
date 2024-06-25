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
public class MapProp<T> implements Prop<T> {
    private boolean initialized = false;
    private @Nullable T value;
    private final Supplier<T> valueSupplier;
    private final Runnable updater;
    private final ListenerSet<T> listeners = new ListenerSet<>();
    private final Supplier<String> toStringSupplier;

    /**
     * Creates a new {@link MapProp} with a single dependency.
     *
     * @param dependency the single dependency.
     * @param converter  the dependency converter.
     * @param <S>        the type the dependency provides.
     */
    private <S> MapProp(Prop<S> dependency, Function<S, T> converter) {
        Objects.requireNonNull(dependency, "dependency cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

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

        toStringSupplier = () -> "dependency=" + dependency + ", converter=" + converter;
    }

    /**
     * Creates a new {@link MapProp} with multiple dependencies.
     *
     * @param dependencies  the list of dependencies.
     * @param valueSupplier the function that uses the dependencies to get the new value. Note: this should hold strong
     *                      references to all dependencies.
     */
    private MapProp(List<?> dependencies, Supplier<T> valueSupplier) {
        Objects.requireNonNull(dependencies, "dependencies cannot be null");
        Objects.requireNonNull(valueSupplier, "valueSupplier cannot be null");

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

        toStringSupplier = () -> "dependencies=" + dependencies + ", valueSupplier=" + valueSupplier;
    }

    @Override
    public T get() {
        if (!initialized) value = valueSupplier.get();
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
        return "MapProp{" + toStringSupplier.get() + "}";
    }

    /**
     * Creates a new {@link MapProp} with a single dependency.
     *
     * @param dependency the single dependency.
     * @param converter  the dependency converter.
     * @param <S>        the type the dependency provides.
     * @param <T>        the type the resulting property holds.
     * @return the created property.
     */
    public static <S, T> MapProp<T> of(Prop<S> dependency, Function<S, T> converter) {
        return new MapProp<>(dependency, converter);
    }

    /**
     * Creates a new {@link MapProp} with multiple dependencies.
     *
     * @param dependencies  the list of dependencies.
     * @param valueSupplier the function that uses the dependencies to get the new value.
     * @param <T>           the type the resulting property holds.
     * @return the created property.
     */
    public static <T> MapProp<T> of(List<?> dependencies, Supplier<T> valueSupplier) {
        return new MapProp<>(dependencies, valueSupplier);
    }
}
