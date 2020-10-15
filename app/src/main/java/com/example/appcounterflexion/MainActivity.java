package com.example.appcounterflexion;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm;
    private Sensor s;
    private SensorEventListener evento;
    //
    private int A;
    private int B;
    private int C;
    private int posicion=0;
    private int cA=0;
    private int cFlex=0;
    private int[] vPosicion=new  int[2];
    //
    public void PosicionActual(int a){
        vPosicion[0]=vPosicion[1];
        vPosicion[1]=a;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(s==null){
            finish();
        }
        evento = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent event){
                //System.out.println("X= "+event.values[0]);
                // System.out.println("Y= "+event.values[1]);
                // System.out.println("Z= "+event.values[2]);
                float A=event.values[0];
                float B=event.values[1];
                float C=event.values[2];
                //
                String valorX=Float.toHexString(A);
                TextView tvValX=(TextView) findViewById(R.id.tvValorX);
                tvValX.setText(valorX);
                String valorY=Float.toHexString(B);
                TextView tvValY=(TextView) findViewById(R.id.tvValorY);
                tvValY.setText(valorY);
                String valorZ=Float.toHexString(C);
                TextView tvValZ=(TextView) findViewById(R.id.tvValorZ);
                tvValZ.setText(valorZ);
                //
                if((A>-1.24  && A<2.59) && (B>3.11 && B<9.75)&&(C>1 && C<8.55)){
                    System.out.println("Esta de pie");
                    posicion=0;
                }else if((A>-3.57  && A<1) && (B>0.31 && B<9.75)&&(C>-2.6 && C<9.85)){
                    System.out.println("Esta con brazos extendidos");
                    posicion=2;
                }else if((A>-9.67  && A<8.95) && (B>-5.68 && B<0.79)&&(C>-0.79 && C<9.57)){
                    System.out.println("Esta con brazo flexionados ");
                    posicion=1;
                }else{

                }
                PosicionActual(posicion);
                String posicionCuerpo="";
                switch (posicion){
                    case 0:
                        posicionCuerpo="Esta de Pie";
                        break;
                    case 1:
                        posicionCuerpo="Esta con Brazos Extendidos!!!";
                        break;
                    case 2:
                        posicionCuerpo="Esta con Brazos Flexionados!!!";
                        break;
                    default:
                        break;
                }

                TextView tvPosiciones=(TextView)findViewById(R.id.tvPosicionesCuerpo);
                tvPosiciones.setText(posicionCuerpo);
                if(vPosicion[0]==vPosicion[1]){

                }
                else if(vPosicion[0]==2 && vPosicion[1]==1){
                    cA=cA+1;
                }else if(vPosicion[0]==1 && vPosicion[1]==2){
                    cA=cA+1;
                }else if((vPosicion[0]==1 && vPosicion[1]==0)||(vPosicion[0]==2 && vPosicion[1]==0)){
                    //cA=0;
                    cFlex=0;
                }
                if(cA==2){
                    cFlex=cFlex+1;
                    String countFlex=Integer.toString(cFlex);
                    TextView tvCountF=(TextView)findViewById(R.id.tvCountFlex);
                    tvCountF.setText(countFlex);
                    System.out.println(cFlex);

                    cA=0;
                }







            }
            @Override
            public void onAccuracyChanged(Sensor sensor,int acurracy){

            }

        };
        sm.registerListener(evento,s,SensorManager.SENSOR_DELAY_NORMAL);
    }

}
