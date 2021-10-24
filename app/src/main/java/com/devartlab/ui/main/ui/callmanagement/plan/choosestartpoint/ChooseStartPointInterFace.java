package com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint;

import com.devartlab.data.room.activity.ActivityEntity;

public interface ChooseStartPointInterFace {

    void chooseStartPoint(int id , String date , String name , int type , ActivityEntity activities);
    void editStartPoint(int id , String date , String name , int type , ActivityEntity activities);
}
