package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MySingleton {
    private static MySingleton instance = null;
    private MySingleton() {}
    public static MySingleton getInstance() {
        if (instance == null) instance = new MySingleton();
        return instance;
    }
}
