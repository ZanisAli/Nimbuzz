package startup.softflix.com.startup

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    //defning sensor
    var sensor:Sensor?=null
    var sensorManager:SensorManager?=null
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    var xold=0.0 //x old value ,fload
    var yold=0.0 //y axis old value
    var zold=0.0 //z axis old value

    //how much minimum threshold nimbuzz should do
    var threshhold=3000.0
    var oldtime:Long=0 //long
    override fun onSensorChanged(event: SensorEvent?) {
        var x=event!!.values[0]
        var y=event!!.values[1]
        var z=event!!.values[2]

        var currentTime=System.currentTimeMillis()
        if((currentTime-oldtime)>100)
        {
            var timeDifference=currentTime-oldtime
            oldtime=currentTime
            var speedOfVibraion=Math.abs(x+y+z-xold-yold-zold)/timeDifference*1000 //just an equation for speed
            if(speedOfVibraion>threshhold)
            {
                //then vibrate
                var v=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(500)
                Toast.makeText(applicationContext,"shock",Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //have to initialize sensor manager
        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //initilaize sensor also
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    //start the sensor
    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}
