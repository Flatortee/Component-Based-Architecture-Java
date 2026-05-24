package com.flatorte.utils;

import static org.lwjgl.glfw.GLFW.*;

public final class Input {
    private static long window;

    private Input() {}

    public static void Init(long windowHandle) {
        window = windowHandle;
    }

    public static boolean GetKey(Key key) {
        return glfwGetKey(window, key.getCode()) == GLFW_PRESS;
    }
}