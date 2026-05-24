package com.flatorte.engine.components;

import com.flatorte.engine.base.Component;
import org.joml.Vector3f;

public final class Transform extends Component {
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform() {
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }
}