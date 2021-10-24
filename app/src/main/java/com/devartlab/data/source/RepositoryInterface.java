package com.devartlab.data.source;


import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Łukasz on 09/11/2017.
 */

public interface RepositoryInterface<T> {

    void insert(T item);

    void update(T item);

    void delete(T item);

    Flowable<List<T>> getAll();

    List<T> getAllList();


    Flowable<T> getModel();


}
