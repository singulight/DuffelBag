package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.web.websocket.SocketObjects;

/**
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
        AddOrRefreshNode nodeFactory = new AddOrRefreshNode(s,mqttMessage);
        nodeFactory.detectDuffelbagNode();
        System.out.println(s+" "+mqttMessage.toString());
        System.out.println(sensors.allSize());
        SocketObjects.getInstance().send("{\"page\":\"home\",\"verb\":\"create\",\"type\":\"row\",\"data\":\""+s+"\"}");
/*        SensorNode ss = sensors.getSensor(s);
        byte [] messageByteArray = mqttMessage.getPayload();
        float floatValue = ByteBuffer.wrap(messageByteArray).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        if (ss.getNodeType().equals(NodeType.VOLTAGE)) System.out.println(s+" "+ss.getValue()+" "+floatValue); */
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
