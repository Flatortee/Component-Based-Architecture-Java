package com.flatorte;

import com.flatorte.components.Debug;

public final class Application {
    private final EntityManager _entityManager = new EntityManager();

    public Application() {
        Run();
    }

    public void Run() {
        Start();
        while (true) {
            Update();
            Draw();
        }
    }

    private void Start() {
        Entity e = new Entity("Player");
        e.AddComponent(new Debug());
        _entityManager.AddEntity(e);
        _entityManager.Awake();
        _entityManager.Start();
    }

    private void Update() {
        _entityManager.Update(1f / 60f);
    }

    private void Draw() {
        _entityManager.Draw();
    }
}
