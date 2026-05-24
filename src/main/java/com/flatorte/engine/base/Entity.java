package com.flatorte.engine.base;

import com.flatorte.engine.manager.EntityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Entity {
    private static long _nextId = 0;
    private final long _id;
    private String _name;
    private boolean _isActive = true;
    private final ArrayList<Component> _components = new ArrayList<>();
    private final ArrayList<Entity> _children = new ArrayList<>();
    private EntityManager _manager = null;
    private Entity _parent = null;

    public Entity() {
        this("Entity");
    }

    public Entity(String name) {
        _id = _nextId++;
        _name = name;
    }

    // Getters
    public long GetId() { return _id; }
    public String GetName() { return _name; }
    public boolean IsActive() { return _isActive; }
    public List<Component> GetComponents() { return Collections.unmodifiableList(_components); }
    public List<Entity> GetChildren() { return Collections.unmodifiableList(_children); }
    public EntityManager GetManager() { return _manager; }
    public Entity GetParent() { return _parent; }

    // Setters
    public void SetName(String name) { _name = name; }
    public void SetManager(EntityManager manager) { _manager = manager; }
    public void SetActive(boolean active) { _isActive = active; }
    public void ToggleActive() { _isActive = !_isActive; }
    public void SetParent(Entity parent) {
        if (_parent != null) {
            _parent._children.remove(this);
        }
        _parent = parent;
        if (_parent != null) {
            _parent._children.add(this);
        }
    }

    // Components
    public <T extends Component>
    T AddComponent(T component) {
        component.SetEntity(this);
        _components.add(component);
        return component;
    }

    public <T extends Component>
    T GetComponent(Class<T> type) {
        for (Component c : _components) {
            if (type.isInstance(c)) {
                return type.cast(c);
            }
        }
        return null;
    }

    public <T extends Component>
    T RemoveComponent(Class<T> type) {
        for (int i = 0; i < _components.size(); i++) {
            Component c = _components.get(i);
            if (type.isInstance(c)) {
                _components.remove(i);
                return type.cast(c);
            }
        }
        return null;
    }

    // Children
    public void AddChild(Entity child) {
        _children.add(child);
        child.SetParent(this);
    }

    public void RemoveChild(Entity child) {
        if (_children.remove(child)) {
            child.SetParent(null);
        }
    }

    public void RemoveChild(String name) {
        for (int i = _children.size() - 1; i >= 0; i--) {
            Entity e = _children.get(i);
            if (e.GetName().equals(name)) {
                e.SetParent(null);
                _children.remove(i);
                return;
            }
        }
    }

    public void RemoveChild(int index) {
        if (index >= 0 && index < _children.size()) {
            Entity e = _children.get(index);
            e.SetParent(null);
        }
    }

    public void RemoveAllChildren() {
        for (Entity e : _children) {
            e.SetParent(null);
        }
        _children.clear();
    }

    // Methods
    public void Awake() {
        if (!_isActive) return;

        for (Component c : _components) {
            c.Awake();
        }
        for (Entity e : _children) {
            if (!e._isActive) continue;
            e.Awake();
        }
    }

    public void Start() {
        if (!_isActive) return;

        for (Component c : _components) {
            c.Start();
        }
        for (Entity e : _children) {
            if (!e._isActive) continue;
            e.Start();
        }
    }

    public void Update(float dt) {
        if (!_isActive) return;

        for (Component c : _components) {
            c.Update(dt);
        }
        for (Entity e : _children) {
            if (!e._isActive) continue;
            e.Update(dt);
        }
    }

    public void Draw() {
        if (!_isActive) return;

        for (Component c : _components) {
            c.Draw();
        }
        for (Entity e : _children) {
            if (!e._isActive) continue;
            e.Draw();
        }
    }
}
