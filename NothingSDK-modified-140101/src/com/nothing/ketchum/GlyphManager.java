package com.nothing.ketchum;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.nothing.thirdparty.IGlyphService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GlyphManager {
    private static final String TAG = "GlyphManager";

    private static final int GLYPH_C = 1;

    private static final int GLYPH_D = 2;

    private static final int DEFAULT_MAX_LIGHT = 4096;

    private static final int DEFAULT_MIN_LIGHT = 800;

    private static final int NO_LIGHT = 0;

    private Context mContext;

    private RemoteServiceConnection mConnection = new RemoteServiceConnection();

    private IGlyphService mService;

    private Callback mCallback;
    private ExecutorService mExecutor;

    private boolean mFrameTask = false;

    private Future mTask = null;

    private String mDevice = null;

    private static GlyphManager mInstance = null;

    public static GlyphManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new GlyphManager(context);
        return mInstance;
    }

    private GlyphManager(Context context) {
        this.mContext = context;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    public void init(Callback callback) {
        this.mCallback = callback;
        Intent launchService = new Intent();
        launchService.setPackage("com.nothing.thirdparty");
        launchService.setAction("com.nothing.thirdparty.bind_glyphservice");
        launchService.setComponent(new ComponentName("com.nothing.thirdparty", "com.nothing.thirdparty.GlyphService"));
        this.mContext.bindService(launchService, this.mConnection, 1);
    }

    public void unInit() {
        this.mContext.unbindService(this.mConnection);
    }

    public boolean register() {
        this.mDevice = Common.DEVICE_22111;
        Log.w("GlyphManager", "You are targeting " + Common.DEVICE_22111 + " as your device.");
        return true;
    }

    public boolean register(String targetDevice) {
        this.mDevice = targetDevice;
        Log.w("GlyphManager", "You are targeting " + targetDevice + " as your device.");
        return true;
    }

    public GlyphFrame.Builder getGlyphFrameBuilder() {
        if (this.mDevice == null)
            return null;
        return new GlyphFrame.Builder(this.mDevice);
    }

    public void openSession() throws GlyphException {
        if (this.mService == null)
            throw new GlyphException("Please use it after service connected.");
        try {
            this.mService.openSession();
        } catch (RemoteException e) {
            Log.d("GlyphManager", e.getMessage());
        }
    }

    public void closeSession() throws GlyphException {
        if (this.mService == null)
            throw new GlyphException("Please use it after service connected.");
        try {
            stopCurrentTask();
            this.mService.closeSession();
        } catch (RemoteException e) {
            Log.d("GlyphManager", e.getMessage());
        }
    }

    public void setFrameColors(int[] colors) throws GlyphException {
        if (this.mService == null)
            throw new GlyphException("Please use it after service connected.");
        try {
            stopCurrentTask();
            this.mService.setFrameColors(colors);
        } catch (RemoteException e) {
            Log.e("GlyphManager", e.getMessage());
        }
    }

    public void toggle(final GlyphFrame frame) {
        stopCurrentTask();
        Runnable task = new Runnable() {
            public void run() {
                try {
                    GlyphManager.this.mService.setFrameColors(frame.getChannel());
                } catch (RemoteException e) {
                    Log.e("GlyphManager", e.getMessage());
                }
            }
        };
        executeFrame(task);
    }

    public void animate(final GlyphFrame frame) {
        stopCurrentTask();
        Runnable task = new Runnable() {
            public void run() {
                int cycle = 0;
                int period = frame.getPeriod();
                int interval = frame.getInterval();
                GlyphFrame emptyFrame = (new GlyphFrame.Builder()).build();
                while (cycle < frame.getCycles()) {
                    long startOn = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startOn < period) {
                        try {
                            int light, colors[] = frame.getChannel();
                            if (System.currentTimeMillis() - startOn < (period / 2)) {
                                light = 0 + 4096 / period / 2 * (int)(System.currentTimeMillis() - startOn);
                            } else {
                                light = 4096 - 4096 / period / 2 * (int)(System.currentTimeMillis() - startOn - (period / 2));
                            }
                            for (int i = 0; i < colors.length; i++) {
                                if (colors[i] != 0)
                                    colors[i] = light;
                            }
                            GlyphManager.this.mService.setFrameColors(colors);
                        } catch (RemoteException e) {
                            Log.e("GlyphManager", e.getMessage());
                        }
                        GlyphManager.this.pauseAWhile(10L);
                    }
                    long startOff = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startOff < interval) {
                        try {
                            GlyphManager.this.mService.setFrameColors(emptyFrame.getChannel());
                        } catch (RemoteException e) {
                            Log.e("GlyphManager", e.getMessage());
                        }
                        GlyphManager.this.pauseAWhile(100L);
                    }
                    cycle++;
                }
            }
        };
        executeFrame(task);
    }

    public void displayProgressAndToggle(GlyphFrame frame, int progress, boolean isReverse) throws GlyphException {
        displayProgress(frame, progress, isReverse, true);
    }

    public void displayProgress(GlyphFrame frame, int progress) throws GlyphException {
        displayProgress(frame, progress, false, false);
    }

    public void displayProgress(GlyphFrame frame, int progress, boolean isReverse) throws GlyphException {
        displayProgress(frame, progress, isReverse, false);
    }

    private void displayProgress(GlyphFrame frame, final int progress, final boolean isReverse, final boolean isToggle) throws GlyphException {
        stopCurrentTask();
        final int[] channel = frame.getChannel();
        int light = 0;
        if (Common.isTargetDevice20111(this.mDevice)) {
            if (channel[Glyph.Code_20111.D1_1] == 0)
                throw new GlyphException("Please choose D1_1 while using display progress in 20111.");
            light = 2;
        }
        if (Common.isTargetDevice22111(this.mDevice)) {
            if ((channel[Glyph.Code_22111.C1_1] == 0 && channel[Glyph.Code_22111.D1_1] == 0) || (channel[Glyph.Code_22111.C1_1] > 0 && channel[Glyph.Code_22111.D1_1] > 0))
                throw new GlyphException("Please choose C1 or D1 while using display progress in 22111");
            light = (channel[Glyph.Code_22111.C1_1] == 0) ? 2 : 1;
        }
        final int finalLight = light;
        Runnable task = new Runnable() {
            public void run() {
                GlyphFrame.Builder builder;
                int sum = 0, start = 0, end = 0, length = 0;
                if (isReverse) {
                    if (finalLight == 1 &&
                            Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                        sum = progress * 700;
                        start = Glyph.Code_22111.C1_16;
                        end = Glyph.Code_22111.C1_1;
                        length = 16;
                    }
                    if (finalLight == 2) {
                        sum = progress * 400;
                        if (Common.isTargetDevice20111(GlyphManager.this.mDevice)) {
                            start = Glyph.Code_20111.D1_8;
                            end = Glyph.Code_20111.D1_1;
                        }
                        if (Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                            start = Glyph.Code_22111.D1_8;
                            end = Glyph.Code_22111.D1_1;
                        }
                        length = 8;
                    }
                } else {
                    if (finalLight == 1)
                        if (Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                            sum = progress * 700;
                            start = Glyph.Code_22111.C1_1;
                            end = Glyph.Code_22111.C1_16;
                            length = 16;
                        }
                    if (finalLight == 2) {
                        sum = progress * 400;
                        if (Common.isTargetDevice20111(GlyphManager.this.mDevice)) {
                            start = Glyph.Code_20111.D1_1;
                            end = Glyph.Code_20111.D1_8;
                        }
                        if (Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                            start = Glyph.Code_22111.D1_1;
                            end = Glyph.Code_22111.D1_8;
                        }
                        length = 8;
                    }
                }
                if (sum == 0)
                    return;
                int progressLight = sum / 4096;
                int remain = sum % 4096;
                if (Common.isTargetDevice20111(GlyphManager.this.mDevice)) {
                    builder = new GlyphFrame.Builder(Common.DEVICE_20111);
                } else if (Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                    builder = new GlyphFrame.Builder(Common.DEVICE_22111);
                } else {
                    builder = new GlyphFrame.Builder();
                }
                if (isReverse) {
                    for (int i = 0; i < progressLight && i <= length &&
                            end <= start; i++) {
                        builder.buildChannel(start);
                        start--;
                    }
                    if (end <= start)
                        if (remain < 800) {
                            builder.buildChannel(start, 800);
                        } else {
                            builder.buildChannel(start, remain);
                        }
                } else {
                    for (int i = 0; i < progressLight && i <= length &&
                            start <= end; i++) {
                        builder.buildChannel(start);
                        start++;
                    }
                    if (start <= end)
                        if (remain < 800) {
                            builder.buildChannel(start, 800);
                        } else {
                            builder.buildChannel(start, remain);
                        }
                }
                if (isToggle) {
                    if (Common.isTargetDevice20111(GlyphManager.this.mDevice)) {
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.A1]))
                            builder.buildChannelA();
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.B1]))
                            builder.buildChannelB();
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.C1]))
                            builder.buildChannel(Glyph.Code_20111.C1);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.C2]))
                            builder.buildChannel(Glyph.Code_20111.C2);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.C3]))
                            builder.buildChannel(Glyph.Code_20111.C3);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.C4]))
                            builder.buildChannel(Glyph.Code_20111.C4);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_20111.E1]))
                            builder.buildChannelE();
                    }
                    if (Common.isTargetDevice22111(GlyphManager.this.mDevice)) {
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.A1]))
                            builder.buildChannel(Glyph.Code_22111.A1);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.A2]))
                            builder.buildChannel(Glyph.Code_22111.A2);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.B1]))
                            builder.buildChannelB();
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.C2]))
                            builder.buildChannel(Glyph.Code_22111.C2);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.C3]))
                            builder.buildChannel(Glyph.Code_22111.C3);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.C4]))
                            builder.buildChannel(Glyph.Code_22111.C4);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.C5]))
                            builder.buildChannel(Glyph.Code_22111.C5);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.C6]))
                            builder.buildChannel(Glyph.Code_22111.C6);
                        if (GlyphManager.this.hasLight(channel[Glyph.Code_22111.E1]))
                            builder.buildChannelE();
                    }
                }
                try {
                    GlyphManager.this.mService.setFrameColors(builder.build().getChannel());
                } catch (RemoteException e) {
                    Log.e("GlyphManager", e.getMessage());
                }
            }
        };
        executeFrame(task);
    }

    private void executeFrame(Runnable task) {
        this.mTask = this.mExecutor.submit(task);
    }

    private boolean isTaskOn() {
        return this.mFrameTask;
    }

    private boolean hasLight(int light) {
        if (light > 0)
            return true;
        return false;
    }

    private void stopCurrentTask() {
        if (this.mTask != null)
            this.mTask.cancel(true);
    }

    private void pauseAWhile(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Log.e("GlyphManager", e.getMessage());
        }
    }

    private class RemoteServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("GlyphManager", "Service connected");
            GlyphManager.this.mService = IGlyphService.Stub.asInterface(service);
            if (GlyphManager.this.mCallback != null)
                GlyphManager.this.mCallback.onServiceConnected(name);
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d("GlyphManager", "Service disconnected");
            GlyphManager.this.mService = null;
            if (GlyphManager.this.mCallback != null)
                GlyphManager.this.mCallback.onServiceDisconnected(name);
        }
    }

    public static interface Callback {
        void onServiceConnected(ComponentName param1ComponentName);

        void onServiceDisconnected(ComponentName param1ComponentName);
    }
}
