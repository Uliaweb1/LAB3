package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MySingleton {//класична реалізація патерну Singleton (приватний конструктор та публічна статична функція getInstance)
    private static MySingleton instance = null;//силка на єдиний MySingleton обєкт, якщо він присутній.
    private MySingleton() {}//пустий приватний конструктор, який унеможливлює створення обєкту new поза межами класу.
    public static MySingleton getInstance() {//функція яка ініціалізує обєкт при першому виклику і завжди повертає одну й ту саму силку на нього.
        if (instance == null) instance = new MySingleton();//ініціалізація обєкту за умовми його відсутності.
        return instance;//повертає силку на ініціалізований інстанс.
    }
}
