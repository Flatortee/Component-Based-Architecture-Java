package com.flatorte.render;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window {
    private final long _window;
    private boolean _isFullscreen = false;

    public Window(String title, int width, int height) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Impossible d'initialiser GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        _window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (_window == NULL) {
            throw new RuntimeException("Échec de la création de la fenêtre GLFW");
        }

        glfwMakeContextCurrent(_window);
        glfwSwapInterval(1);
        glfwShowWindow(_window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
    }

    public void Destroy() {
        glfwFreeCallbacks(_window);
        glfwDestroyWindow(_window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void ToggleFullscreen() {
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);
        if (!_isFullscreen) {
            glfwSetWindowMonitor(_window, monitor, 0, 0, vidMode.width(), vidMode.height(), vidMode.refreshRate());
            _isFullscreen = true;
        } else {
            glfwSetWindowMonitor(_window, NULL, 100, 100, 800, 600, GLFW_DONT_CARE);
            _isFullscreen = false;
        }
    }

    public long GetHandle() {
        return _window;
    }

    public boolean IsOpen() {
        return !glfwWindowShouldClose(_window);
    }

    public void PollEvents() {
        glfwPollEvents();
    }

    public void Clear(float r, float g, float b, float a) {
        glClearColor(0.1f, 0.2f, 0.4f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void Clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void Display() {
        glfwSwapBuffers(_window);
    }
}
