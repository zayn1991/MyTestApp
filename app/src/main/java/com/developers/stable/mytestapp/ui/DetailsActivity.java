package com.developers.stable.mytestapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.stable.mytestapp.R;
import com.developers.stable.mytestapp.models.Datum;
import com.developers.stable.mytestapp.models.DatumViewModel;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static String INTENT_ID = "DetailsActivity";
    private DatumViewModel viewModel;
    private ImageView imageView;
    private TextView textId;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        viewModel = ViewModelProviders.of(this).get(DatumViewModel.class);
        Intent intent = getIntent();
        Datum item = intent.getParcelableExtra(INTENT_ID);
        if (item != null){
            viewModel.setItem(item);
        } else {
            Toast.makeText(this, getString(R.string.error_receiving_data), Toast.LENGTH_SHORT).show();
        }

        initViews();
        setData();
    }

    private void initViews() {
        imageView = findViewById(R.id.image);
        textId = findViewById(R.id.textId);
        textTitle = findViewById(R.id.textTitle);
    }

    private void setData() {
        Picasso.get()
                .load(!TextUtils.isEmpty(viewModel.getItem().getUrl()) ? viewModel.getItem().getUrl() : null)
//                .centerCrop()
//                .fit()
                .error(R.drawable.nophotoerror)
                .into(imageView);

        textId.setText(viewModel.getItem().getPosition() + "");
        textTitle.setText(viewModel.getItem().getTitle());
    }
}
