package jp.ddo.cloverrose.goodmorning;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class GoodMorningActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private double prev=-1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /* センサーマネージャを取得する */
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Listenerの登録解除
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
     
        // 照度センサー
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
     
        // センサマネージャへリスナーを登録
        if (sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            if(!sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)){
                // device doesn't support this sensor
                // TODO output error message
            }
            
            /*
             * int  SENSOR_DELAY_FASTEST    get sensor data as fast as possible
             * int  SENSOR_DELAY_GAME       rate suitable for games
             * int  SENSOR_DELAY_NORMAL     rate (default) suitable for screen orientation changes
             * int  SENSOR_DELAY_UI         rate suitable for the user interface
             */
        }
    }

    
    // SensorEventListenerなので追加。ただし、使わないのでStub
    // センサーの精度が変更されたときに呼び出される。
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Log.v("Activity","Sensor.TYPE_LIGHT accuracy:" + String.valueOf(accuracy) );
    }

    // 通知タイミングはSENSOR_DELAY_FASTEST、変化があり次第即座に反応します
    @Override
    public void onSensorChanged(SensorEvent event) {
        double now=Double.parseDouble(String.valueOf(event.values[0]));
        if(now-this.prev>500){
            Log.v("Activity","Sensor.TYPE_LIGHT :" + now ); //照度NexusOneの場合、10～10000程度で値が変わる
        }
        this.prev=now;
    }
}