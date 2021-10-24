package com.devartlab.data.source.values;


import java.util.List;

import com.devartlab.data.room.values.ValuesDao;
import com.devartlab.data.room.values.ValuesEntity;
import com.devartlab.data.source.RepositoryInterface;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class ValuesRepository implements RepositoryInterface<ValuesEntity> {

    private volatile static ValuesRepository INSTANCE = null;

    private final ValuesDao dao;

    public static ValuesRepository getInstance(ValuesDao dao) {
        if (INSTANCE == null) {
            synchronized (ValuesRepository.class) {
                INSTANCE = new ValuesRepository(dao);
            }
        }
        return INSTANCE;
    }

    private ValuesRepository(ValuesDao dao) {
        this.dao = dao;
    }


    @Override
    public void insert(ValuesEntity item) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dao.insert(item);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void update(ValuesEntity item) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dao.update(item);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void delete(ValuesEntity item) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dao.delete(item);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();

    }

    @Override
    public Flowable<List<ValuesEntity>> getAll() {
        return dao.getAll();
    }

    @Override
    public List<ValuesEntity> getAllList() {


        return null;
    }

    @Override
    public Flowable<ValuesEntity> getModel() {
        return dao.getModel();

    }


}
