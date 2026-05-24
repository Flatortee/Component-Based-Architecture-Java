package com.flatorte.engine.base;

public abstract class Component {
    protected Entity entity;
    protected boolean IsEnabled = true;

    // Getters
    public boolean IsEnabled() { return IsEnabled; }
    public Entity GetEntity() {
        if (entity == null) {
            // ERROR
            return null;
        }
        return entity;
    }

    // Setters
    public void SetEntity(Entity e) { entity = e; }
    public void SetEnabled(boolean enabled) { IsEnabled = enabled; }
    public void ToggleEnabled() { IsEnabled = !IsEnabled; }

    // Methods
    public void Awake() { }
    public void Start() { }
    public void Update(float dt) { }
    public void Draw() { }
}
