package com.flatorte.engine.base;

import com.flatorte.engine.manager.AssetsManager;
import com.flatorte.engine.manager.EntityManager;
import com.flatorte.engine.manager.SceneManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scene {
    protected String _name = "Unnamed Scene";
    protected SceneManager _sceneManager;
    protected final AssetsManager _assetsManager = new AssetsManager();
    protected final EntityManager _entityManager = new EntityManager();
    private final ArrayList<Scene> _registeredSubScenes = new ArrayList<>();
    private final ArrayList<Scene> _activeSubScenes = new ArrayList<>();
    private final List<Scene> _registeredSubScenesView = Collections.unmodifiableList(_registeredSubScenes);
    private final List<Scene> _activeSubScenesView = Collections.unmodifiableList(_activeSubScenes);
    private Scene _parentScene;
    private boolean _popRequested = false;

    // Getters
    public String GetName() { return _name; }
    public SceneManager GetSceneManager() { return _sceneManager; }
    public AssetsManager GetAssetsManager() { return _assetsManager; }
    public EntityManager GetEntityManager() { return _entityManager; }
    public Scene GetParentScene() { return _parentScene; }
    public List<Scene> GetRegisteredSubScenes() { return _registeredSubScenesView; }
    public List<Scene> GetActiveSubScenes() { return _activeSubScenesView; }
    public boolean HasActiveSubScenes() { return !_activeSubScenes.isEmpty(); }

    // Setters
    public void SetName(String name) {
        if (name != null) {
            _name = name;
        }
    }

    public void SetSceneManager(SceneManager manager) { _sceneManager = manager; }
    public void SetParentScene(Scene parent) { _parentScene = parent; }

    // Methods
    public void OnLoad() {
        //TODO: load resources
    }

    public void OnUnload() {
        _entityManager.ClearAll();
    }

    public void Awake() {
        _entityManager.Awake();
    }

    public void Start() {
        _entityManager.Start();
    }

    public void Update(float dt) {
        UpdateSubScenes(dt);
        _entityManager.Update(dt);
    }

    public void Draw() {
        DrawSubScenes();
        _entityManager.Draw();
    }

    public <T extends Scene> T RegisterSubScene(Class<T> type) {

        try {

            T subScene = type.getDeclaredConstructor().newInstance();

            subScene.SetSceneManager(_sceneManager);
            subScene.SetParentScene(this);

            _registeredSubScenes.add(subScene);

            return subScene;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to register sub scene: "
                            + type.getSimpleName(),
                    e
            );
        }
    }

    public void PushSubScene(String name) {
        if (name == null) return;
        if (!_activeSubScenes.isEmpty()) {
            Scene last = _activeSubScenes.getLast();
            if (name.equals(last.GetName())) {
                return;
            }
        }

        for (int i = 0; i < _registeredSubScenes.size(); i++) {

            Scene sub = _registeredSubScenes.get(i);

            if (name.equals(sub.GetName())) {

                _activeSubScenes.add(sub);

                sub.OnLoad();
                sub.Awake();
                sub.Start();

                return;
            }
        }
    }

    public void PopSubScene() {

        if (_parentScene != null) {
            _parentScene.PopSubScene();
            return;
        }

        if (!_activeSubScenes.isEmpty()) {
            _popRequested = true;
        }
    }

    private void UpdateSubScenes(float dt) {

        if (_activeSubScenes.isEmpty()) {
            return;
        }

        Scene top = _activeSubScenes.getLast();
        top.Update(dt);

        if (_popRequested) {
            top.OnUnload();
            _activeSubScenes.removeLast();
            _popRequested = false;
        }
    }

    private void DrawSubScenes() {
        for (int i = 0; i < _activeSubScenes.size(); i++) {
            _activeSubScenes.get(i).Draw();
        }
    }
}