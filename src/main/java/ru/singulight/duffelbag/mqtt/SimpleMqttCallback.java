package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.singulight.duffelbag.mqttnodes.AllNodes;

/**ra6sk1We
 *
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class SimpleMqttCallback implements MqttCallback {

    private AllNodes sensors = AllNodes.getInstance();

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(s+" "+mqttMessage.toString());
        System.out.println(sensors.sensorsSize());
        AddOrRefreshNode nodeFactory = new AddOrRefreshNode(s,mqttMessage);
        nodeFactory.detectDuffelbagNode();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
