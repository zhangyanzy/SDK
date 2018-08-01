package com.tarenwang.trwgamesdk.floatmenu;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * author MinoZhang
 * date 2017/2/9
 */
public class ServiceConnectionManager implements ServiceConnection {

    private final Context context;
    private final Class<? extends Service> service;
    private boolean attemptingToBind = false;
    private boolean bound = false;
    private QdServiceConnection mQdServiceConnection;

    public ServiceConnectionManager(Context context, Class<? extends Service> service, QdServiceConnection mQdServiceConnection) {
        this.context = context;
        this.service = service;
        this.mQdServiceConnection = mQdServiceConnection;
    }

    public void bindToService() {
        if (!attemptingToBind) {
            attemptingToBind = true;
            context.bindService(new Intent(context, service), this, Context.BIND_AUTO_CREATE);

        }

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        attemptingToBind = false;
        bound = true;
        mQdServiceConnection.onServiceConnected(componentName, iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mQdServiceConnection.onServiceDisconnected(componentName);
        bound = false;
    }

    public void unbindFromService() {
        attemptingToBind = false;
        if (bound) {
            context.unbindService(this);
            bound = false;
        }
    }

    public interface QdServiceConnection {
        void onServiceConnected(ComponentName name, IBinder service);

        void onServiceDisconnected(ComponentName name);
    }


}