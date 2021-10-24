package com.devartlab.ui.dialogs.massages;

import com.devartlab.data.room.massages.MassageEntity;
import com.devartlab.data.room.poduct.ProductEntity;
import com.devartlab.model.SlideModel;

import java.util.List;

public interface OnMassageSelect {

    void setOnMassageSelect(MassageEntity massageEntity);
    void addCustomMassage(MassageEntity massageEntity, ProductEntity productEntity , List<SlideModel> slides);
    void addEditMassage(MassageEntity massageEntity, ProductEntity productEntity , List<SlideModel> slides, int arrangedId);
}
