package com.bradesco.projetoprogramacao.Services.LocalServices;

import java.util.List;

public interface ServicesInterface<T> {
    String DB_NAME = "courses.sqlite";

    boolean add(T object);
    boolean remove(int id);
    boolean clearAll();
    boolean edit(T object, int id);
    List<T> getList();
    T get(int id);
}
