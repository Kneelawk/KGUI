package com.kneelawk.kgui.engine.api.prop;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.UnknownNullability;

/**
 * A read only property contains data that cannot be written to directly. This could be because it is derived from
 * another property.
 *
 * @param <T> the type this property contains. Note: this type may be nullable.
 */
public interface Prop<T> extends Supplier<T>, Listenable<T> {
    /**
     * {@return the current value contained in this property}
     * <p>
     * Note: nullability of this return is based on the nullability of the contained value.
     */
    @UnknownNullability
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

    /**
     * Creates a derivative property that depends on this property.
     *
     * @param mapFunction the function to map from this property's value to the resulting property value.
     * @param <U>         the type stored in the mapped property.
     * @return the mapped property.
     */
    default <U> Prop<U> map(Function<T, U> mapFunction) {
        return MapProp.of(this, mapFunction);
    }

    /**
     * Creates a derivative property that uses a property gotten from the value of this property.
     *
     * @param flatMapFunction the function to map from this property's value to the resulting property.
     * @param <U>             the type stored in the mapped property.
     * @return the flat-mapped property.
     */
    default <U> Prop<U> flatMap(Function<T, Prop<U>> flatMapFunction) {
        return FlatMapProp.of(this, flatMapFunction);
    }
}
