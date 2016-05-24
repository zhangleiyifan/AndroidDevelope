package com.gyz.androiddevelope.service;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.gyz.androiddevelope.IMyAidlInterface;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.ToastUtil;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.service.LocalService.java
 * @author: ZhaoHao
 * @date: 2016-05-20 08:47
 */
public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    private MyBind myBind;
    private MyConn myConn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return myBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myBind = new MyBind();
        if (myConn == null) {
            myConn = new MyConn();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RemoteService.this.bindService(new Intent( RemoteService.this, LocalService.class), myConn, Context.BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }


    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.show(getBaseContext(), "与本地连接建立关联", Toast.LENGTH_SHORT);
            L.e(TAG, "与本地连接建立关联");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ToastUtil.show(getBaseContext(), "本地连接断开", Toast.LENGTH_SHORT);
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), myConn, Context.BIND_IMPORTANT);
        }
    }
    class MyBind extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getServiceName() throws RemoteException {
            return "RemoteService";
        }
    }

}
