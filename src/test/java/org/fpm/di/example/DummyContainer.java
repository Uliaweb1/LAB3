package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DummyContainer implements Container {
    private final Map<Class<?>, Object> objInstances;
    private final Map<Class<?>, Class<?>> diMap;
    public DummyContainer(Map<Class<?>, Object> objInstances, Map<Class<?>, Class<?>> diMap) {
        this.objInstances = objInstances;
        this.diMap = diMap;
    }
    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (diMap.containsKey(clazz)) {
            if (diMap.get(clazz).equals(clazz)) {
                final List<Constructor<?>> injectedConstructors
                        = Arrays
                        .stream(clazz.getDeclaredConstructors())//потік конструкторів класу clazz.
                        .filter(a -> a.isAnnotationPresent(Inject.class))
                        .toList();//конвертація потоку в список
                if (clazz.isAnnotationPresent(Singleton.class)) {
                    try {
                        //наприклад clazz=MySingleton.class
                        return (T) clazz.getDeclaredMethod("getInstance").invoke(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                } else if (injectedConstructors.size() == 1) {
                    final Constructor<?> ctor = injectedConstructors.get(0);
                    final Object ctorParam = getComponent(
                            Arrays.stream(ctor.getParameterTypes()).toList().get(0)
                    );
                    try {
                        //наприклад clazz=UseA.class
                        return (T) ctor.newInstance(ctorParam);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                return (T) getComponent(diMap.get(clazz));
            }
        }
        if (objInstances.containsKey(clazz)) {
            return (T) objInstances.get(clazz);
        }
        return null;
    }
}
