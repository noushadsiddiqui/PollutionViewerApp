package com.codeGeekerz.project.pollutionTracker.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by User on 14-02-2016.
 */
public class AddressResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }


    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
