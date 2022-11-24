package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;
import org.fpm.di.Container;
import org.fpm.di.Environment;

import java.util.HashMap;
import java.util.Map;

public class DummyEnvironment implements Environment {//DummyEnvironment реалізує інтерфейс Environment
    @Override
    public Container configure(Configuration configuration) {//configure інтерфейсна функція інтерфейсу Environment, яка повертає сконфігурований Container.
        Map<Class<?>, Object> objInstances = new HashMap<>();//створюємо спільний словник для об'єктів
        Map<Class<?>, Class<?>> diMap = new HashMap<>();//створюємо спільний словник для класів
        //створюємо DummyContainer і DummyBinder з силками на спільні словники
        DummyContainer container = new DummyContainer(objInstances, diMap);
        DummyBinder binder = new DummyBinder(objInstances, diMap);
        configuration.configure(binder);//конфігуруємо контейнер з допомогою Binder
        return container;
    }
}
