package cn.mrzhqiang.android_api_oreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    public void clickUE(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_UE);
        startActivity(intent);
    }

    public void clickSystem(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_SYSTEM);
        startActivity(intent);
    }

    public void clickMedia(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_MEDIA);
        startActivity(intent);
    }

    public void clickConnect(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_CONNECT);
        startActivity(intent);
    }

    public void clickShare(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_SHARE);
        startActivity(intent);
    }

    public void clickAccessible(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_ACCESSIBLE);
        startActivity(intent);
    }

    public void clickSafetyAndPrivacy(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_SAFETY);
        startActivity(intent);
    }

    public void clickTest(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_TEST);
        startActivity(intent);
    }

    public void clickRunning(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_RUNNING);
        startActivity(intent);
    }

    public void clickEE(View view) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction(DetailsActivity.ACTION_EE);
        startActivity(intent);
    }
}
