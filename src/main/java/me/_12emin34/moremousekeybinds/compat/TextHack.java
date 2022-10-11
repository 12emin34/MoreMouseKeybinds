package me._12emin34.moremousekeybinds.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TextHack {
    private TextHack() {
        throw new IllegalStateException("This class is not intended to be instantiated");
    }

    public static Object literal(String content) {
        try {
            Constructor<?> constructor = Class.forName("net.minecraft.class_2585").getConstructor(String.class);
            return constructor != null ? constructor.newInstance(content) : null;
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            // do nothing
        }
        return null;
    }
}
