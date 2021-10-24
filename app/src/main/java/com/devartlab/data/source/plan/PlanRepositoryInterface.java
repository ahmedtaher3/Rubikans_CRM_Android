package com.devartlab.data.source.plan;


import java.util.List;

import com.devartlab.data.room.plan.PlanEntity;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Łukasz on 09/11/2017.
 */

public interface PlanRepositoryInterface<T> {

    Observable<Boolean> add(T item);

    Observable<Boolean> addList(List<T> item);

    Observable<Boolean> update(T item);

    Observable<Boolean> removeAll();

    Observable<Boolean> remove(T item);

    Observable<Boolean> removeDouble(String id , String shift , String date);

    Flowable<List<PlanEntity>> getAll();


    List<PlanEntity> getAllByDateAndShift(String date , String shift);

    Flowable<PlanEntity> getPlan(int userId);


}
