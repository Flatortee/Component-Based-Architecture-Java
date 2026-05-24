package com.flatorte.utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public final class Time {
    private static float _deltaTime;
    private static float _lastFrameTime;
    private static int _fps;
    private static int _frameCount;
    private static float _timeAccumulator;

    private Time() { }

    public static void Init() {
        _lastFrameTime = (float) glfwGetTime();
    }

    public static void Update() {
        float currentTime = (float) glfwGetTime();
        _deltaTime = currentTime - _lastFrameTime;
        _lastFrameTime = currentTime;
        _frameCount++;
        _timeAccumulator += _deltaTime;
        if (_timeAccumulator >= 1.0f) {
            _fps = _frameCount;
            _frameCount = 0;
            _timeAccumulator -= 1.0f;
        }
    }

    public static float GetDeltaTime() {
        return _deltaTime;
    }

    public static int GetFps() {
        return _fps;
    }

    public static float GetTime() {
        return (float) glfwGetTime();
    }
}