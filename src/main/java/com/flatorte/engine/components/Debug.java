package com.flatorte.engine.components;

import com.flatorte.engine.base.Component;

public class Debug extends Component {
    public void Start() {
        System.out.println(entity.GetName());
    }
}
