package com.flatorte.engine.components;

import com.flatorte.engine.base.Component;
import com.flatorte.engine.components.Transform;
import com.flatorte.utils.Input;
import com.flatorte.utils.Key;
import com.flatorte.utils.Mouse;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Camera extends Component {
    private Transform transform;
    private float _aspectRatio = 16.0f / 9.0f;
    private float _moveSpeed = 5.0f;
    private float _mouseSensitivity = 0.1f;
    private float _fov = 70.0f;
    private float _near = 0.1f;
    private float _far = 100.0f;

    @Override
    public void Start() {
        this.transform = this.entity.GetComponent(Transform.class);
    }

    @Override
    public void Update(float dt) {
        float rotX = Mouse.GetDeltaX() * _mouseSensitivity;
        float rotY = Mouse.GetDeltaY() * _mouseSensitivity;

        transform.rotation.y += rotX;
        transform.rotation.x += rotY;

        if (transform.rotation.x > 89.0f) transform.rotation.x = 89.0f;
        if (transform.rotation.x < -89.0f) transform.rotation.x = -89.0f;

        float yaw = (float) Math.toRadians(transform.rotation.y);
        float pitch = (float) Math.toRadians(transform.rotation.x);

        float forwardX = (float) (Math.sin(yaw) * Math.cos(pitch));
        float forwardY = (float) -Math.sin(pitch);
        float forwardZ = (float) (-Math.cos(yaw) * Math.cos(pitch));

        float rightX = (float) Math.cos(yaw);
        float rightZ = (float) Math.sin(yaw);

        float speed = _moveSpeed * dt;

        if (Input.GetKey(Key.W)) {
            transform.position.x += forwardX * speed;
            transform.position.y += forwardY * speed;
            transform.position.z += forwardZ * speed;
        }
        if (Input.GetKey(Key.S)) {
            transform.position.x -= forwardX * speed;
            transform.position.y -= forwardY * speed;
            transform.position.z -= forwardZ * speed;
        }

        if (Input.GetKey(Key.A)) {
            transform.position.x -= rightX * speed;
            transform.position.z -= rightZ * speed;
        }
        if (Input.GetKey(Key.D)) {
            transform.position.x += rightX * speed;
            transform.position.z += rightZ * speed;
        }

        if (Input.GetKey(Key.SPACE)) {
            transform.position.y += speed;
        }
        if (Input.GetKey(Key.LEFT_SHIFT)) {
            transform.position.y -= speed;
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        Matrix4f projectionMatrix = new Matrix4f().perspective(
                (float) Math.toRadians(_fov),
                _aspectRatio,
                _near,
                _far
        );

        float[] projBuffer = new float[16];
        projectionMatrix.get(projBuffer);
        glLoadMatrixf(projBuffer);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glRotatef(transform.rotation.x, 1.0f, 0.0f, 0.0f);
        glRotatef(transform.rotation.y, 0.0f, 1.0f, 0.0f);
        glRotatef(transform.rotation.z, 0.0f, 0.0f, 1.0f);
        glTranslatef(-transform.position.x, -transform.position.y, -transform.position.z);
    }

    public float GetFov() { return _fov; }
    public void SetFov(float fov) { this._fov = fov; }
    public float GetAspectRatio() { return _aspectRatio; }
    public void SetAspectRatio(float aspectRatio) { this._aspectRatio = aspectRatio; }
    public float GetMoveSpeed() { return _moveSpeed; }
    public void SetMoveSpeed(float moveSpeed) { this._moveSpeed = moveSpeed; }
    public float GetMouseSensitivity() { return _mouseSensitivity; }
    public void SetMouseSensitivity(float mouseSensitivity) { this._mouseSensitivity = mouseSensitivity; }
    public float GetNear() { return _near; }
    public void SetNear(float near) { this._near = near; }
    public float GetFar() { return _far; }
    public void SetFar(float far) { this._far = far; }
}