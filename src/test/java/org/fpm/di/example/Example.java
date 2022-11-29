package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;
import org.fpm.di.Container;
import org.fpm.di.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class Example {

    private Container container;

    @Before
    public void setUp() {
        Environment env = new DummyEnvironment();
        container = env.configure(new MyConfiguration());
    }

    @Test
    public void shouldInjectSingleton() {
        //майсінглтон є сінглтоном тому незалежно від конфігурації контейнера він повертає одну й ту ж саму силку на сінглтон
        assertSame(container.getComponent(MySingleton.class), container.getComponent(MySingleton.class));
    }

    @Test
    public void shouldInjectPrototype() {
        //Контейнер сконфігуровано так що він кожен раз створює новий об'єкт класу MyPrototype
        assertNotSame(container.getComponent(MyPrototype.class), container.getComponent(MyPrototype.class));
    }
    @Test
    public void shouldInjectA() {
        //Контейнер сконфігуровано так щоб він повертав одне й те саме посилання на об'єкт класу А, а саме посилання на вже створений об'єкт класу В
        assertSame(container.getComponent(A.class), container.getComponent(A.class));
    }
    @Test
    public void shouldInjectB() {
        assertSame(container.getComponent(B.class), container.getComponent(B.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
         */
        final B bAsSingleton = container.getComponent(B.class);
        assertSame(container.getComponent(A.class), bAsSingleton);
        assertSame(container.getComponent(B.class), bAsSingleton);
    }

    @Test
    public void shouldBuildInjectDependencies() {
        final UseA hasADependency = container.getComponent(UseA.class);
        assertSame(hasADependency.getDependency(), container.getComponent(B.class));
    }
    @Test
    public void shouldUseSameDependencies() {
        final UseA useA1 = container.getComponent(UseA.class);
        final UseA useA2 = container.getComponent(UseA.class);
        assertNotSame(useA1, useA2);
        assertSame(useA1.getDependency(), useA2.getDependency());
    }
}
