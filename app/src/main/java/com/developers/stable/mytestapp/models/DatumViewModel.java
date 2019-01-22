package com.developers.stable.mytestapp.models;

import android.arch.lifecycle.ViewModel;

public class DatumViewModel extends ViewModel {
    Datum item;

    public Datum getItem() {
        return item;
    }

    public void setItem(Datum item) {
        this.item = item;
    }
}
