package com.farias.pelota_acelerometro;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Pelota extends View implements SensorEventListener {

    Paint pincel = new Paint();
    int alto, ancho;
    int tamanio = 40;
    int borde = 18;
    float ejeX = 0, ejeY = 0, ejeZ = 0;
    String x,y,z;

    public Pelota (Context interfaz){
        super(interfaz);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor((Sensor.TYPE_ACCELEROMETER));
        smAdministrador.registerListener(this,snsRotacion,SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejeX = sensorEvent.values[0];
        x = Float.toString(ejeX);
        if(ejeX < (tamanio + borde)){
            ejeX = tamanio + borde;
        }
        else if ( ejeX > (ancho-(tamanio + borde))){
            ejeX = ancho - (tamanio + borde);
        }
        ejeY += sensorEvent.values[1];
        y = Float.toString(ejeY);
        if(ejeY < (tamanio + borde)){
            ejeY = tamanio + borde;
        }
        else if ( ejeY > (alto-tamanio-170)){
            ejeY = alto - tamanio - 170;
        }

        ejeZ = sensorEvent.values[2];
        z = String.format("%.2f",ejeZ);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        pincel.setColor(Color.RED);
        // lienzo
        canvas.drawCircle(ejeX, ejeY, ejeZ+tamanio, pincel);

        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        canvas.drawText("pelota", ejeX-35,ejeY+3,pincel);

        super.onDraw(canvas);


    }
}
