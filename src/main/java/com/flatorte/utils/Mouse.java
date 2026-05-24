package com.flatorte.utils;

import static org.lwjgl.glfw.GLFW.*;

public final class Mouse {
    private static long _window;
    private static float _lastX = 0.0f;
    private static float _lastY = 0.0f;
    private static float _deltaX = 0.0f;
    private static float _deltaY = 0.0f;
    private static boolean _firstMouse = true;

    private Mouse() {}

    public static void Init(long windowHandle) {
        _window = windowHandle;
        glfwSetInputMode(_window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public static void Update() {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(_window, xpos, ypos);

        float currentX = (float) xpos[0];
        float currentY = (float) ypos[0];

        if (_firstMouse) {
            _lastX = currentX;
            _lastY = currentY;
            _firstMouse = false;
        }

        _deltaX = currentX - _lastX;
        _deltaY = currentY - _lastY;
        _lastX = currentX;
        _lastY = currentY;
    }

    public static float GetDeltaX() { return _deltaX; }
    public static float GetDeltaY() { return _deltaY; }
}