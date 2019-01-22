package com.developers.stable.mytestapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.developers.stable.mytestapp.App;
import com.developers.stable.mytestapp.R;
import com.developers.stable.mytestapp.models.AnimalViewModel;
import com.developers.stable.mytestapp.models.Datum;
import com.developers.stable.mytestapp.models.ModelAnimal;

import retrofit2.Call;

public class FragmentList extends BaseFragment implements IFragmentList, FragmentView {

    private FragmentListPresenter presenter;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AdapterItems adapter;

    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        adapter = new AdapterItems(getContext(), this);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        recyclerView = v.findViewById(R.id.recyclerView);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_list));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (presenter.getViewModel().getStateByKey(presenter.getAnimalType()) != null) {
            recyclerView.getLayoutManager()
                    .onRestoreInstanceState(presenter.getViewModel()
                            .getStateByKey(presenter.getAnimalType()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // save RecyclerView state
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        presenter.getViewModel().setStateByKey(presenter.getAnimalType(), listState);
    }

    public void setViewModel(AnimalViewModel viewModel) {
        presenter = new FragmentListPresenter(this);
        presenter.setViewModel(viewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter != null){
            presenter.showContent();
        }
    }

    public void showLoadedList(ModelAnimal body) {
        adapter.setData(body.getData());
    }

    @Override
    public void cancelRequestOnDestroy(@NonNull Call<?> call) {
        cancelOnDestroy(call);
    }

    @Override
    public void openChoosed(Datum item) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.INTENT_ID, item);
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
