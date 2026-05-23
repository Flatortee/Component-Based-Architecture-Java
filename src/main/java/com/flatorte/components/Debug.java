package com.flatorte.components;

import com.flatorte.Component;

public class Debug extends Component {
    public void Start() {
        System.out.println(entity.GetName());
    }
}
