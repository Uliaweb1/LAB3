package org.fpm.di.example;

import org.fpm.di.Binder;

import java.util.Map;

public class DummyBinder implements Binder {
    private final Map<Class<?>, Object> objInstances;
    private final Map<Class<?>, Class<?>> diMap;
    public DummyBinder(Map<Class<?>, Object> objInstances, Map<Class<?>, Class<?>> diMap) {
        this.objInstances = objInstances;
        this.diMap = diMap;
    }
    @Override
    public <T> void bind(Class<T> clazz) {
        this.diMap.put(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        this.diMap.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        this.objInstances.put(clazz, instance);
    }
}
