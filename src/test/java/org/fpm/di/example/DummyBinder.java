package org.fpm.di.example;

import org.fpm.di.Binder;

import java.util.Map;

public class DummyBinder implements Binder {//DummyBinder є реалізацією інтерфейсу Binder.
    private final Map<Class<?>, Object> objInstances;//спільний словник(для DummyBinder і DummyContainer), який містить інстанси ініціалізованих об'єктів контейнеру
    private final Map<Class<?>, Class<?>> diMap;//спільний словник(для DummyBinder і DummyContainer), який містить розширення класів
    public DummyBinder(Map<Class<?>, Object> objInstances, Map<Class<?>, Class<?>> diMap) {//конструктор класу DummyBinder в який передаються спільні словники, створені ззовні
        this.objInstances = objInstances;//зберегли передану в конструктор силку на словник як приватний член класу.
        this.diMap = diMap;////зберегли передану в конструктор силку на словник як приватний член класу.
    }
    @Override
    public <T> void bind(Class<T> clazz) {//реалізація інтерфейсної функції Bind
        this.diMap.put(clazz, clazz);//записуємо в словник значення яке дорівнює ключу, тобто це випадок коли потрібно створити інстанс відповідного класу за замовчуванням.
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {//реалізація інтерфейсної функції Bind
        //це випадок коли у функції Bind передається деякий клас та клас, що його розширює, у цьому випадку контейнер на запит класу повинен повернути клас що є його розширенням.
        this.diMap.put(clazz, implementation);//В словник записуємо цю пару класів, тобто класи та його реалізацію.
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {//реалізація інтерфейсної функції Bind
        //Випадок коли в Bind передається клас та об'єкти цього класу, тобто обєкти треба записати в словник обєктів.
        //Тобто на запит класу clazz контейнер повинен повернути обєкт instance(аргумент).
        this.objInstances.put(clazz, instance);//цей інстанс зберегли в словнику.
    }
}
