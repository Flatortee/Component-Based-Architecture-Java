package com.flatorte.engine.scene;

import com.flatorte.engine.base.Scene;
import com.flatorte.engine.base.Entity;

public final class Game extends Scene {

    @Override
    public void OnLoad() {
        Entity player = new Entity("Player");
        GetEntityManager().AddEntity(player);

        Entity camera = new Entity("Camera");
        GetEntityManager().AddEntity(camera);
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