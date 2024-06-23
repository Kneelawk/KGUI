package com.kneelawk.kgui.engine.api.prop;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

/**
 * Holds a reference to a listener.
 *
 * @param <T> the type the listener listens for.
 */
public sealed interface ListenerRef<T> {
    /**
     * {@return the listener if it still exists or <code>null</code> if it doesn't}
     */
    @Nullable
    Consumer<T> get();

    /**
     * A strong reference to a listener.
     *
     * @param consumer the listener.
     * @param <T>      the listener's type.
     */
    record Strong<T>(Consumer<T> consumer) implements ListenerRef<T> {
        @Override
        public @Nullable Consumer<T> get() {
            return consumer;
        }
    }

    /**
     * A weak reference to a listener.
     *
     * @param ref the weak reference.
     * @param <T> the listener's type.
     */
    record Weak<T>(WeakReference<Consumer<T>> ref) implements ListenerRef<T> {
        /**
         * Creates a new listener weak reference.
         *
         * @param consumer the listener.
         */
        public Weak(Consumer<T> consumer) {
            this(new WeakReference<>(consumer));
        }

        @Override
        public @Nullable Consumer<T> get() {
            return ref.get();
        }
    }
}
