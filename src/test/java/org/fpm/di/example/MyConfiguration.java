package org.fpm.di.example;

import org.fpm.di.Binder;//імпортування інтерфейсу Binder
import org.fpm.di.Configuration;//імпортування інтерфейсу Configuration

public class MyConfiguration implements Configuration {//клас MyConfiguration реалізує інтерфейс Configuration
    @Override
    public void configure(Binder binder) {//реалізація інтерфейсної функції configure
        binder.bind(MySingleton.class);//Конфігурація контейнера який буде повертати інстанси класу MySingleton.
        //клас MySingleton може мати лише один інстанс, який і буде повертатися контейнером.
        binder.bind(MyPrototype.class);//Конфігурація контейнера який буде повертати інстанси класу MyPrototype
//контейнер буде створювати кожен раз нові інстанси класу MyPrototype.

        binder.bind(UseA.class);//Конфігурація контейнера який буде повертати інстанси класу MyPrototype
//*class UseA має залежність від класу А.

        binder.bind(A.class, B.class);//конфігурація контейнера, який на запит інстансу класу А буде повертати інстанс класу В.
        //Клас В є розширенням класу А.
        binder.bind(B.class, new B());//Конфігурація контейнера, який на запит класу В буде повертати заданий інстанс (new B())
    }
}
