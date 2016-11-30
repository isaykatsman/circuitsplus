package com.appsforgood.isayandjack.circuitsplus.components;

import java.util.List;

/**
 * Model class for a container of electrical components
 */
public abstract class GenericContainerComponent {
    private String name;
    private String desc;

    //WILL be public for now -> future uses may require this
    public List<GenericContainerComponent> components;

    //accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
