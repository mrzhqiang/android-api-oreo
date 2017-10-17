package cn.mrzhqiang.android_api_oreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.mrzhqiang.android_api_oreo.accessible.AccessibleFragment;
import cn.mrzhqiang.android_api_oreo.connect.ConnectFragment;
import cn.mrzhqiang.android_api_oreo.ee.EEFragment;
import cn.mrzhqiang.android_api_oreo.media.MediaFragment;
import cn.mrzhqiang.android_api_oreo.running.RunningFragment;
import cn.mrzhqiang.android_api_oreo.safety.SafetyFragment;
import cn.mrzhqiang.android_api_oreo.share.ShareFragment;
import cn.mrzhqiang.android_api_oreo.system.SystemFragment;
import cn.mrzhqiang.android_api_oreo.test.TestFragment;
import cn.mrzhqiang.android_api_oreo.ue.UEFragment;


public class DetailsActivity extends AppCompatActivity {

    public static final String ACTION_UE = "action_UE";
    public static final String ACTION_SYSTEM = "action_SYSTEM";
    public static final String ACTION_MEDIA = "action_MEDIA";
    public static final String ACTION_CONNECT = "action_CONNECT";
    public static final String ACTION_SHARE = "action_SHARE";
    public static final String ACTION_ACCESSIBLE = "action_ACCESSIBLE";
    public static final String ACTION_SAFETY = "action_SSAFETY";
    public static final String ACTION_TEST = "action_TEST";
    public static final String ACTION_RUNNING = "action_RUNNING";
    public static final String ACTION_EE = "action_EE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String action = intent.getAction();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (action) {
            case ACTION_UE:
                transaction.replace(R.id.details, UEFragment.newInstance());
                break;
            case ACTION_SYSTEM:
                transaction.replace(R.id.details, SystemFragment.newInstance());
                break;
            case ACTION_MEDIA:
                transaction.replace(R.id.details, MediaFragment.newInstance());
                break;
            case ACTION_CONNECT:
                transaction.replace(R.id.details, ConnectFragment.newInstance());
                break;
            case ACTION_SHARE:
                transaction.replace(R.id.details, ShareFragment.newInstance());
                break;
            case ACTION_ACCESSIBLE:
                transaction.replace(R.id.details, AccessibleFragment.newInstance());
                break;
            case ACTION_SAFETY:
                transaction.replace(R.id.details, SafetyFragment.newInstance());
                break;
            case ACTION_TEST:
                transaction.replace(R.id.details, TestFragment.newInstance());
                break;
            case ACTION_RUNNING:
                transaction.replace(R.id.details, RunningFragment.newInstance());
                break;
            case ACTION_EE:
                transaction.replace(R.id.details, EEFragment.newInstance());
                break;
            default:
                Toast.makeText(this, "非法启动", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }
        transaction.commit();
    }
}
