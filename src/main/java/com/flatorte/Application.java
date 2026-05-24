package com.flatorte;

import com.flatorte.engine.Engine;
import com.flatorte.render.Window;
import com.flatorte.utils.Input;
import com.flatorte.utils.Mouse;
import com.flatorte.utils.Time;

public final class Application {
    private final Window _window = new Window("Engine", 800, 600);
    private final Engine _engine = new Engine();

    public Application() {
        Time.Init();
        Input.Init(_window.GetHandle());
        Mouse.Init(_window.GetHandle());
    }

    public void Run() {
        while (_window.IsOpen()) {
            Time.Update();
            Mouse.Update();
            Events();
            Update(Time.GetDeltaTime());
            Draw();
        }
        Destroy();
    }

    private void Events() {
        _window.PollEvents();
    }

    private void Update(float dt) {
        _engine.Update(dt);
    }

    private void Draw() {
        _window.Clear();
        _engine.Draw();
        _window.Display();
    }

    private void Destroy() {
        _window.Destroy();
    }
}
