package com.flatorte.engine.manager;

import com.flatorte.engine.base.Component;
import com.flatorte.engine.base.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntityManager {
    private final ArrayList<Entity> _entities = new ArrayList<>();
    private final ArrayList<Entity> _toAdd = new ArrayList<>();
    private final ArrayList<Entity> _toRemove = new ArrayList<>();
    private final List<Entity> _entitiesView = Collections.unmodifiableList(_entities);

    public List<Entity> GetEntities() { return _entitiesView; }

    public <T extends Component> T FindComponentOfType(Class<T> type) {
        for (int i = 0; i < _entities.size(); i++) {
            Entity e = _entities.get(i);
            T comp = e.GetComponent(type);
            if (comp != null) return comp;
        }
        return null;
    }

    public <T extends Component> ArrayList<T> FindComponentsOfType(Class<T> type) {
        ArrayList<T> result = new ArrayList<>();

        for (int i = 0; i < _entities.size(); i++) {
            Entity e = _entities.get(i);

            T comp = e.GetComponent(type);
            if (comp != null) {
                result.add(comp);
            }
        }

        return result;
    }

    public Entity FindEntityByName(String name) {
        if (name == null) return null;

        for (int i = 0; i < _entities.size(); i++) {
            Entity e = _entities.get(i);

            if (name.equals(e.GetName())) {
                return e;
            }
        }

        return null;
    }

    public void AddEntity(Entity entity) {
        if (entity == null) return;
        _toAdd.add(entity);
    }

    public void ForceAddEntity(Entity entity) {
        if (entity == null) return;
        _entities.add(entity);
        entity.Awake();
        entity.Start();
    }

    public void RemoveEntity(Entity entity) {
        if (entity == null) return;
        _toRemove.add(entity);
    }

    public void ClearAll() {
        _entities.clear();
        _toAdd.clear();
        _toRemove.clear();
    }

    public void Awake() {
        for (int i = 0; i < _entities.size(); i++) {
            _entities.get(i).Awake();
        }
    }

    public void Start() {
        for (int i = 0; i < _entities.size(); i++) {
            _entities.get(i).Start();
        }
    }

    public void Update(float dt) {
        ProcessModifications();

        for (int i = 0; i < _entities.size(); i++) {
            _entities.get(i).Update(dt);
        }
    }

    public void Draw() {
        for (int i = 0; i < _entities.size(); i++) {
            _entities.get(i).Draw();
        }
    }

    private void ProcessModifications() {
        if (!_toAdd.isEmpty()) {
            for (int i = 0; i < _toAdd.size(); i++) {
                Entity e = _toAdd.get(i);
                _entities.add(e);
                e.Awake();
                e.Start();
            }
            _toAdd.clear();
        }

        if (!_toRemove.isEmpty()) {
            for (int i = 0; i < _toRemove.size(); i++) {
                _entities.remove(_toRemove.get(i));
            }
            _toRemove.clear();
        }
    }
}