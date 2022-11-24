package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DummyContainer implements Container {//DummyContainer реалізує інтерфейс Container
    private final Map<Class<?>, Object> objInstances;// спільний словник обєктів
    private final Map<Class<?>, Class<?>> diMap;//спільний словник класів
    public DummyContainer(Map<Class<?>, Object> objInstances, Map<Class<?>, Class<?>> diMap) {//конструктор DummyContainer
        this.objInstances = objInstances;//збереження переданих силок у спільні словники у приватні члени класу.
        this.diMap = diMap;//збереження переданих силок у спільні словники у приватні члени класу.
    }
    @Override
    public <T> T getComponent(Class<T> clazz) {//реалізація інтерфейсної функції getComponent
        if (diMap.containsKey(clazz)) {//умова яка перевіряє присутність ключа clazz у словнику класів контейнера.
            if (diMap.get(clazz).equals(clazz)) {//умова, яка перевіряє рівність ключа та значення у словнику класів контейнера
                //якщо рівні, то контейнер повинен повернути створений за замовчуванням інстанс класу clazz
                final List<Constructor<?>> injectedConstructors//змінна, що містить список конструкторів класу clazz з анотацією inject
                        = Arrays
                        .stream(clazz.getDeclaredConstructors())//потік конструкторів класу clazz.
                        .filter(a -> a.isAnnotationPresent(Inject.class))//фільтрація конструкторів, які містять анотація Inject (використовується лямбда(це функція без імені), що перевіряє присутність анотації Inject)
                        .toList();//конвертація потоку в список
                if (clazz.isAnnotationPresent(Singleton.class)) {//умова, що перевіряє присутність анотації Singleton класа clazz.
                    try {// блок коду, що може викликати винятків
                        //наприклад clazz=MySingleton.class
                        return (T) clazz.getDeclaredMethod("getInstance").invoke(null);//якщо анотація присутня, то викликаємо метод getInstance
                    } catch (IllegalAccessException e) {//обробка винятків
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {//обробка винятків
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {//обробка винятків
                        throw new RuntimeException(e);
                    }
                } else if (injectedConstructors.size() == 1) {//умова, що перевіряє наявність лише одного конструктору з анотацією inject
                    final Constructor<?> ctor = injectedConstructors.get(0);//отримуємо елемент з індексом 0, тобто єдиний конструктор з анотацією inject
                    final Object ctorParam = getComponent(
                            Arrays.stream(ctor.getParameterTypes()).toList().get(0)// отримуємо єдиний тип параметру конструктора з анотацією inject
                    );//отримуємо силку на обєкт відповідного типу з контейнеру(DummyContainer).
                    try {
                        //наприклад clazz=UseA.class
                        return (T) ctor.newInstance(ctorParam);//створюємо обєкт класу clazz(за допомогою конструктора ctor) з впроваденим обєктом ctorParam в якості аргументу
                    } catch (InstantiationException e) {//обробка винятків
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {//обробка винятків
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {//обробка винятків
                        throw new RuntimeException(e);
                    }
                } else {//якщо анотації Singleton та Inject відсутні
                    try {
                        //наприклад clazz=MyPrototype.class
                        return clazz.getDeclaredConstructor().newInstance();//створюємо обєкт класу clazz за допомогою конструктора за замовчуванням.
                    } catch (InstantiationException e) {//обробка винятків
                        throw new RuntimeException(e);//обробка винятків
                    } catch (IllegalAccessException e) {//обробка винятків
                        throw new RuntimeException(e);//обробка винятків
                    } catch (InvocationTargetException e) {//обробка винятків
                        throw new RuntimeException(e);//обробка винятків
                    } catch (NoSuchMethodException e) {//обробка винятків
                        throw new RuntimeException(e);//обробка винятків
                    }
                }
            } else {//якщо ключ та значення у словнику класів не співпадають
                //наприклад clazz=A.class
                return (T) getComponent(diMap.get(clazz));//отримуємо силку з контейнеру на обєкт класу що відповідає значенню за ключем clazz у словнику класів
            }
        }
        if (objInstances.containsKey(clazz)) {//умова що перевіряє присутність словнику clazz у словнику обєктів.
            //наприклад clazz=B.class
            return (T) objInstances.get(clazz);//Якщо обєкт відповідного типу присутній у словнику, то контейнер повертає силку на нього.
        }
      //якщо ключ clazz відсутній у словниках, то контейнер повертає силку на null обєкт
        return null;
    }
}
