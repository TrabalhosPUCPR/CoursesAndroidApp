package com.bradesco.projetoprogramacao.Model.Database;

import java.util.List;

public interface DAO<T> {

    String DB_NAME = "courses.sqlite";

    boolean add(T object);
    boolean remove(int id);
    boolean clearAll();
    boolean edit(T object, int id);
    List<T> getList();
    T get(int id);
}
