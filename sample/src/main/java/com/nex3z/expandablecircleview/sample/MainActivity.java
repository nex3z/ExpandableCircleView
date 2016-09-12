package com.nex3z.expandablecircleview.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nex3z.expandablecircleview.ExpandableCircleView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int EXPAND_DURATION = 500;
    private static final int EVENT_EXPAND = 1;

    private ExpandableCircleView mCircle;
    private boolean mExpand = true;
    private boolean mAuto = true;
    private Random mRnd = new Random();
    private ExpandHandler mHandler = new ExpandHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircle = (ExpandableCircleView) findViewById(R.id.circle);
        mCircle.setExpandAnimationDuration(EXPAND_DURATION);
        mCircle.setProgress(0);

         mHandler.sendMessageDelayed(mHandler.obtainMessage(EVENT_EXPAND), EXPAND_DURATION);

        Button button = (Button) findViewById(R.id.btn_change_size);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuto = false;
                mCircle.setProgress(mRnd.nextInt(100), true);
            }
        });
    }

    private static class ExpandHandler extends Handler {
        private final WeakReference<MainActivity> mActivityRef;

        public ExpandHandler(MainActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_EXPAND:
                    MainActivity activity = mActivityRef.get();
                    if (activity != null && activity.mAuto) {
                        if (activity.mExpand) {
                            activity.mCircle.setProgress(100, true);
                        } else {
                            activity.mCircle.setProgress(0, true);
                        }
                        activity.mExpand = !activity.mExpand;

                        activity.mHandler.sendMessageDelayed(
                                activity.mHandler.obtainMessage(EVENT_EXPAND), EXPAND_DURATION);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
