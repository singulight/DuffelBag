package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.singulight.duffelbag.sensors.AllMqttNodes;
import ru.singulight.duffelbag.sensors.EachSensor;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class SimpleMqttCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(s+" "+mqttMessage.toString());
        AllMqttNodes sensors = AllMqttNodes.getInstance();
        EachSensor mevent = new EachSensor();
        mevent.configure(300,"temperature",s, EachSensor.SensorType.CURRENT,0.0f,1000.0f);
        sensors.addSensor(mevent);
        System.out.println(sensors.sensorsSize());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
