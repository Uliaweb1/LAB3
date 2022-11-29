package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;
import org.fpm.di.Container;
import org.fpm.di.Environment;

import java.util.HashMap;
import java.util.Map;

public class DummyEnvironment implements Environment {
    @Override
    public Container configure(Configuration configuration) {
        Map<Class<?>, Object> objInstances = new HashMap<>();
        Map<Class<?>, Class<?>> diMap = new HashMap<>();
        DummyContainer container = new DummyContainer(objInstances, diMap);
        DummyBinder binder = new DummyBinder(objInstances, diMap);
        configuration.configure(binder);
        return container;
    }
}
