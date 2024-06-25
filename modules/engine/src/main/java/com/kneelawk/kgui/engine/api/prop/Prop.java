package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Supplier;

/**
 * A read only property contains data that cannot be written to directly. This could be because it is derived from
 * another property.
 *
 * @param <T> the type this property contains.
 */
public interface Prop<T> extends Supplier<T>, Listenable<T> {
    /**
     * {@return the current value contained in this property}
     */
    T get();

    /**
     * Force this property to re-calculate its value.
     * <p>
     * This is really only useful if this property depends on values that are not from other properties. This only
     * updates this property, not its dependencies.
     * <p>
     * For properties that do not depend on other properties, this does nothing.
     */
    default void update() {}
}
