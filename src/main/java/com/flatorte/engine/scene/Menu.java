package com.flatorte.engine.scene;

import com.flatorte.engine.base.Scene;
import com.flatorte.engine.base.Entity;
import com.flatorte.engine.components.Debug;

public final class Menu extends Scene {

    @Override
    public void OnLoad() {
        Entity btn = new Entity("Button");
        btn.AddComponent(new Debug());
        GetEntityManager().AddEntity(btn);
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