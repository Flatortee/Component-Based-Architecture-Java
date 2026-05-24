package com.flatorte.engine;

import com.flatorte.engine.manager.SceneManager;
import com.flatorte.engine.scene.Game;
import com.flatorte.engine.scene.Menu;

public final class Engine {
    SceneManager _sceneManager = new SceneManager();

    public Engine() {
        Initialize();
    }

    private void Initialize() {
        //TODO: Mettre les scenes ici
        _sceneManager.RegisterScene("Menu", Menu.class);
        _sceneManager.RegisterScene("Game", Game.class);
        _sceneManager.ChangeScene("Menu", true);
    }

    public void Update(float dt) {
        _sceneManager.Update(dt, true);
    }

    public void Draw() {
        _sceneManager.Draw();
    }
}
