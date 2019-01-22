package com.developers.stable.mytestapp.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public abstract class BaseFragment extends Fragment {

    @NonNull
    private List<Call<?>> calls;

    public BaseFragment() {
        calls = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        for (Call<?> call : calls) call.cancel();
        super.onDestroy();
    }

    protected void cancelOnDestroy(@NonNull Call<?> call) {
        calls.add(call);
    }

}
