package com.kneelawk.kgui.engine.api.prop;

import org.jetbrains.annotations.Nullable;

/**
 * A socket that holds a property, but that can have its property changed.
 *
 * @param <T> the type the property holds.
 */
public interface Socket<T> extends WritableProp<T> {
    /**
     * Forces the stored property to recalculate its value.
     *
     * @see Prop#update()
     */
    void updateValue();

    /**
     * {@return the current property in this socket}
     */
    Prop<T> getProp();

    /**
     * Gets the current property in this socket as a {@link WritableProp} if the stored property is of a compatible type.
     *
     * @return the current property in this socket as a {@link WritableProp}.
     */
    @Nullable
    WritableProp<T> getWritableProp();

    /**
     * Sets the property held in this socket.
     *
     * @param prop the new property to hold in this socket.
     */
    void setProp(Prop<T> prop);
}
