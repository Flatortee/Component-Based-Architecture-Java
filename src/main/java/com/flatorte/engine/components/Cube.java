package com.flatorte.engine.components;

import com.flatorte.engine.base.Component;

import static org.lwjgl.opengl.GL11.*;

public final class Cube extends Component {
    private Transform transform;

    public Cube() { }

    @Override
    public void Start() {
        this.transform = this.entity.GetComponent(Transform.class);
    }

    @Override
    public void Draw() {
        glPushMatrix();

        glTranslatef(transform.position.x, transform.position.y, transform.position.z);

        glRotatef(transform.rotation.x, 1.0f, 0.0f, 0.0f);
        glRotatef(transform.rotation.y, 0.0f, 1.0f, 0.0f);
        glRotatef(transform.rotation.z, 0.0f, 0.0f, 1.0f);

        glScalef(transform.scale.x, transform.scale.y, transform.scale.z);

        glBegin(GL_QUADS);

        glColor3f(0.3f, 0.3f, 0.3f);

        glVertex3f(-0.5f, -0.5f,  0.5f);
        glVertex3f( 0.5f, -0.5f,  0.5f);
        glVertex3f( 0.5f,  0.5f,  0.5f);
        glVertex3f(-0.5f,  0.5f,  0.5f);

        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f,  0.5f, -0.5f);
        glVertex3f( 0.5f,  0.5f, -0.5f);
        glVertex3f( 0.5f, -0.5f, -0.5f);

        glVertex3f(-0.5f,  0.5f, -0.5f);
        glVertex3f(-0.5f,  0.5f,  0.5f);
        glVertex3f( 0.5f,  0.5f,  0.5f);
        glVertex3f( 0.5f,  0.5f, -0.5f);

        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f( 0.5f, -0.5f, -0.5f);
        glVertex3f( 0.5f, -0.5f,  0.5f);
        glVertex3f(-0.5f, -0.5f,  0.5f);

        glVertex3f( 0.5f, -0.5f, -0.5f);
        glVertex3f( 0.5f,  0.5f, -0.5f);
        glVertex3f( 0.5f,  0.5f,  0.5f);
        glVertex3f( 0.5f, -0.5f,  0.5f);

        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, -0.5f,  0.5f);
        glVertex3f(-0.5f,  0.5f,  0.5f);
        glVertex3f(-0.5f,  0.5f, -0.5f);

        glEnd();
        glPopMatrix();
    }
}