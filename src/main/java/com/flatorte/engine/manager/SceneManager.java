package com.flatorte.engine.manager;

import com.flatorte.engine.base.Scene;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public final class SceneManager {
    private final HashMap<String, Class<? extends Scene>> _sceneRegistry = new HashMap<>();
    private final Set<String> _availableScenesView = Collections.unmodifiableSet(_sceneRegistry.keySet());
    private Scene _currentScene;
    private volatile boolean _isLoading = false;
    private volatile float _progressionPercent = 0f;

    public Scene GetCurrentScene() {
        return _currentScene;
    }
    public Set<String> GetAvailableScenes() {
        return _availableScenesView;
    }
    public boolean IsLoading() {
        return _isLoading;
    }
    public float GetProgressionPercent() {
        return _progressionPercent;
    }

    public <T extends Scene>
    void RegisterScene(String name, Class<T> type) {
        if (name == null || type == null) {
            return;
        }

        _sceneRegistry.put(name, type);
    }

    public void ChangeScene(String sceneName, boolean autoStart) {
        if (sceneName == null) {
            return;
        }

        Class<? extends Scene> type = _sceneRegistry.get(sceneName);

        if (type == null) {
            System.err.println("[SceneManager] Scene not found: " + sceneName);
            return;
        }

        if (_isLoading) {
            return;
        }

        _isLoading = true;
        _progressionPercent = 0f;

        Thread loaderThread = new Thread(() -> {

            try {
                Scene newScene = type.getDeclaredConstructor().newInstance();

                _progressionPercent = 25f;

                newScene.SetName(sceneName);
                newScene.SetSceneManager(this);

                _progressionPercent = 50f;

                newScene.OnLoad();

                _progressionPercent = 75f;
                _currentScene = newScene;

                if (autoStart) {
                    _currentScene.Awake();
                    _currentScene.Start();
                }

                _progressionPercent = 100f;

            } catch (Exception e) {
                System.err.println("[SceneManager] Failed to load scene: " + sceneName);
                System.err.println(e.getMessage());
            } finally {

                _isLoading = false;
            }

        });

        loaderThread.setDaemon(true);
        loaderThread.start();
    }

    public void Update(float dt, boolean isPlayMode) {
        if (_isLoading) {
            return;
        }

        if (!isPlayMode) {
            return;
        }

        if (_currentScene != null) {
            _currentScene.Update(dt);
        }
    }

    public void Draw() {
        if (_isLoading) {

            DrawLoadingScreen();

            return;
        }

        if (_currentScene != null) {
            _currentScene.Draw();
        }
    }

    private void DrawLoadingScreen() {

        // TODO renderer abstraction

        System.out.println("Loading... " + _progressionPercent + "%");
    }
}