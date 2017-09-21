package com.wuzhou.wlibrary.zxing;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.wuzhou.wlibrary.R;
import com.wuzhou.wlibrary.page.BaseActivity;
import com.wuzhou.wlibrary.zxing.camera.CameraManager;
import com.wuzhou.wlibrary.zxing.decoding.CaptureActivityHandler;
import com.wuzhou.wlibrary.zxing.decoding.InactivityTimer;
import com.wuzhou.wlibrary.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * 扫二维码界面
 *
 * @author wfy
 */
public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback,View.OnClickListener{
    public final static int SCANNIN_REQUEST_CODE = 0x112;
    public static final String SCAN_RESULT = "scan_result";
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    /*******************
     * 解码格式*******在DecodeThead中初始化
     ********/
    private Vector<BarcodeFormat> decodeFormats;
    /***
     * 生成qr图的字符的编码方式
     **/
    private String characterSet;

    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    /**********
     * 左右音量
     **********/
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

//    private Button btn_scan_backward;
//    private RelativeLayout rl_scan_bar;
//    private ImageView imv_flight;
    protected Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
        int color = ta.getColor(0, 0);
        ta.recycle();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        toolbar.setTitle("扫一扫");
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
//        rl_scan_bar = (RelativeLayout) findViewById(R.id.rl_scan_bar);
//        btn_scan_backward = (Button) findViewById(R.id.btn_scan_backward);
//        imv_flight = (ImageView) findViewById(R.id.imv_flight);
        fitScreen();
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
//        btn_scan_backward.setOnClickListener(this);
//        imv_flight.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(SCAN_RESULT, resultString);
            intent.putExtras(bundle);
            this.setResult(RESULT_OK, intent);
        }
        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {//setPreviewDisplay
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            //在此handler初始化时完成取景
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    private boolean is_open = false;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
//        if(v.getId()==R.id.btn_scan_backward){
//            finish();
//        }else
        if(v.getId()==R.id.imv_flight){
            if(!hasFlash()){
                Toast.makeText(mActivity,"对不起，您的设备没有闪光灯",Toast.LENGTH_SHORT).show();
                return;
            }

            if (is_open) {
                is_open = false;
//                    imv_flight.setBackgroundResource(R.mipmap.shanguangdeng_off_img_2x);
            } else {
                is_open = true;
//                    imv_flight.setBackgroundResource(R.mipmap.sanguangdeng_on_img_2x);
            }
//				setFlashlightEnabled(is_open);
            CameraManager.get().flush(is_open);
        }
    }


    private void fitScreen() {
//         TODO Auto-generated method stub
//        smg.FragmentLayoutParams(rl_scan_bar, 0, TitleActivity.TITLE_HEIGHT, 0, 0, 0, 0);
//        smg.RelativeLayoutParams(btn_scan_backward, 53, 53, 0, 30, 0, 0);
//        smg.RelativeLayoutParams(imv_flight, 62, 62, 0, 0, 0, 200);
    }

    /**
     * 判断是否有闪光灯
     * @return
     */
    private boolean hasFlash(){
        boolean has=true;

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            has=false;
        }

        return has;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}