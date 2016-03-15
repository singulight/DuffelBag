package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.singulight.duffelbag.mqttnodes.ActuatorNode;
import ru.singulight.duffelbag.mqttnodes.SensorNode;
import ru.singulight.duffelbag.mqttnodes.AllNodes;
import ru.singulight.duffelbag.mqttnodes.Thing;
import ru.singulight.duffelbag.mqttnodes.types.NodeType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;

import static ru.singulight.duffelbag.mqttnodes.types.NodeType.*;
import static ru.singulight.duffelbag.mqttnodes.types.NodeType.RGB;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.03.16.
 */
public class AddOrRefreshNode {

    private String topic;
    private MqttMessage mqttMessage;
    private String strValue;
    private float floatValue;
    private long id;
    private NodeType nodeType = null;
    private int error;
    private AllNodes allNodes = AllNodes.getInstance();

    public AddOrRefreshNode(String topic, MqttMessage message) {
        this.topic = topic;
        this.mqttMessage = message;
    }

    public void detectAndParseDuffelbagNode() {

        String [] nodeParts = topic.split("/");
        if (nodeParts[0].equals("duffelbag") && nodeParts.length == 3) {
            switch (nodeParts[1]) {
                case "temperature": nodeType = TEMPERATURE; break;
                case "rel_humidity": nodeType = REL_HUMIDITY; break;
                case "atmospheric_pressure": nodeType = ATMOSPHERIC_PRESSURE; break;
                case "rainfall": nodeType = RAINFALL; break;
                case "wind_speed": nodeType = WIND_SPEED; break;
                case "power": nodeType = POWER; break;
                case "power_consumption": nodeType = POWER_CONSUMPTION; break;
                case "voltage": nodeType = VOLTAGE; break;
                case "current": nodeType = CURRENT; break;
                case "water_flow": nodeType = WATER_FLOW; break;
                case "water_consumption": nodeType = WATER_CONSUMPTION; break;
                case "resistance": nodeType = RESISTANCE; break;
                case "gas_concentration": nodeType = GAS_CONCENTRATION; break;
                case "push_button": nodeType = PUSH_BUTTON; break;
                case "swt": nodeType = SWT; break;
                case "text": nodeType = TEXT; break;
                case "rgb": nodeType = RGB; break;
                case "relay": nodeType = RELAY; break;
                case "ac_voltage": nodeType = AC_VOLTAGE; break;
                default: nodeType = OTHER; break;
            }
            switch (nodeType) {
                case TEMPERATURE:
                case REL_HUMIDITY:
                case ATMOSPHERIC_PRESSURE:
                case RAINFALL:
                case WIND_SPEED:
                case POWER:
                case POWER_CONSUMPTION:
                case VOLTAGE:
                case CURRENT:
                case WATER_FLOW:
                case WATER_CONSUMPTION:
                case RESISTANCE:
                case GAS_CONCENTRATION:
                case PUSH_BUTTON:
                case SWT:
                    /**
                     * Parse sensor with duffelbag format and float value
                     * */
                    byte [] messageByteArray = mqttMessage.getPayload();
                    floatValue = ByteBuffer.wrap(messageByteArray).order(ByteOrder.LITTLE_ENDIAN).getFloat(); //May throw BufferUnderflowException
                    id = new BigInteger(nodeParts[2], 16).longValue(); // May throw NumberFormatException
                    if(!allNodes.isSensorExist(topic)) {
                        SensorNode sensor = new SensorNode(id, topic, nodeType);
                        sensor.setValue(floatValue);
                        sensor.setKnown(false);
                        allNodes.addSensor(sensor);
                    } else {
                        allNodes.getSensor(topic).setValue(floatValue);
                    }
                    break;
                case TEXT:
                    /**
                     * Parse sensor with duffelbag format and text value
                     * */
                    id = new BigInteger(nodeParts[2], 16).longValue(); // May throw NumberFormatException
                    if(!allNodes.isSensorExist(topic)) {
                        SensorNode sensorText = new SensorNode(id, topic, nodeType);
                        sensorText.setTextValue(mqttMessage.toString());
                        sensorText.setKnown(false);
                        allNodes.addSensor(sensorText);
                    } else {
                        allNodes.getSensor(topic).setTextValue(mqttMessage.toString());
                    }
                    break;
                case RGB:
                case RELAY:
                case AC_VOLTAGE:
                    /**
                     * Parse actuator with duffelbag format
                     * */
                    id = new BigInteger(nodeParts[2], 16).longValue(); // May throw NumberFormatException
                    if(!allNodes.isActuatorExist(topic)) {
                        ActuatorNode actuatorNode = new ActuatorNode(id, topic, nodeType);
                        allNodes.addActuator(actuatorNode);
                    }
                    break;
            }
        } else {
            /**
             * Parse sensor with non duffelbag format
             * */
            if(!allNodes.isSensorExist(topic)) {
                SensorNode otherNode = new SensorNode(404, topic, NodeType.OTHER);
                otherNode.setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
                otherNode.setKnown(false);
                allNodes.addSensor(otherNode);
            } else {
                allNodes.getSensor(topic).setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
            }
        }
    }

    private void parseThingNode(Thing thing) throws ParseException {
        String message = thing.getConfigMessage();
        JSONParser jsonParser = new JSONParser();
        JSONObject mainObject = (JSONObject) jsonParser.parse(message);

    }
}
