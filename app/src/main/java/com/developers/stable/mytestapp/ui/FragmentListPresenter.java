package com.developers.stable.mytestapp.ui;

import android.support.annotation.NonNull;

import com.developers.stable.mytestapp.App;
import com.developers.stable.mytestapp.R;
import com.developers.stable.mytestapp.models.AnimalViewModel;
import com.developers.stable.mytestapp.models.ModelAnimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListPresenter {

    private final FragmentView fragmentView;

    private AnimalViewModel viewModel;
    //we need own variable "animalType" because viewModel.getAnimalType() may be changed from activity
    private String animalType;

    public FragmentListPresenter(@NonNull FragmentView fragmentView) {
        this.fragmentView = fragmentView;
    }

    public void setViewModel(AnimalViewModel viewModel){
        this.viewModel = viewModel;
        animalType = viewModel.getAnimalType();
    }

    public AnimalViewModel getViewModel() {
        return viewModel;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void showContent() {
        fragmentView.showLoading(true);
        if (viewModel != null) {
            if (animalType.equals(App.getInstance().getString(R.string.cat))) {
                getCatRequest();
            } else if (animalType.equals(App.getInstance().getString(R.string.dog))) {
                getDogRequest();
            }
        }
    }

    private void getDogRequest() {
        if (viewModel.getModelDog() == null) {
            Call<ModelAnimal> call = App.getNetworkClass().getApiService().getData(viewModel.getAnimalType());
            call.enqueue(new Callback<ModelAnimal>() {
                @Override
                public void onResponse(Call<ModelAnimal> call, Response<ModelAnimal> response) {
                    fragmentView.showLoading(false);
                    if (response.isSuccessful()) {
                        fragmentView.showLoadedList(response.body());
                        viewModel.setModelDog(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ModelAnimal> call, Throwable t) {
                    if (!call.isCanceled()) {
                        fragmentView.showLoading(false);
                    }
                }
            });
            fragmentView.cancelRequestOnDestroy(call);

        } else {
            fragmentView.showLoading(false);
            fragmentView.showLoadedList(viewModel.getModelDog());
        }
    }

    private void getCatRequest() {
        if (viewModel.getModelCat() == null) {
            Call<ModelAnimal> call = App.getNetworkClass().getApiService().getData(viewModel.getAnimalType());
            call.enqueue(new Callback<ModelAnimal>() {
                @Override
                public void onResponse(Call<ModelAnimal> call, Response<ModelAnimal> response) {
                    fragmentView.showLoading(false);
                    if (response.isSuccessful()) {
                        fragmentView.showLoadedList(response.body());
                        viewModel.setModelCat(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ModelAnimal> call, Throwable t) {
                    if (!call.isCanceled()) {
                        fragmentView.showLoading(false);
                    }
                }
            });
            fragmentView.cancelRequestOnDestroy(call);

        } else {
            fragmentView.showLoading(false);
            fragmentView.showLoadedList(viewModel.getModelCat());
        }
    }
}
