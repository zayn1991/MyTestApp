package com.developers.stable.mytestapp.ui;

import android.support.annotation.NonNull;

import com.developers.stable.mytestapp.models.ModelAnimal;

import retrofit2.Call;

public interface FragmentView {

    void showLoading(boolean show);

    void showLoadedList(ModelAnimal body);

    void cancelRequestOnDestroy(@NonNull Call<?> call);

}
