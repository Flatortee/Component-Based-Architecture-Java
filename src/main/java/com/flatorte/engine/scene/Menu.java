package com.flatorte.engine.scene;

import com.flatorte.engine.base.Scene;
import com.flatorte.engine.base.Entity;
import com.flatorte.engine.components.Camera;
import com.flatorte.engine.components.Cube;
import com.flatorte.engine.components.Transform;

public final class Menu extends Scene {

    @Override
    public void OnLoad() {
        Entity camera = new Entity("Camera");
        camera.AddComponent(new Transform());
        camera.AddComponent(new Camera());
        GetEntityManager().AddEntity(camera);

        Entity player = new Entity("Player");
        player.AddComponent(new Transform());
        player.AddComponent(new Cube());
        GetEntityManager().AddEntity(player);
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);
    }

    @Override
    public void Draw() {
        super.Draw();
    }
}