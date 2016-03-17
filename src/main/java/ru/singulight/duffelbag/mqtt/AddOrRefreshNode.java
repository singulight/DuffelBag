package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
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

    private String mqttTopic;
    private MqttMessage mqttMessage;
    private String strValue;
    private float floatValue;
    private long id;
    private NodeType nodeType = null;
    private int error;
    private AllNodes allNodes = AllNodes.getInstance();

    public AddOrRefreshNode(String topic, MqttMessage message) {
        this.mqttTopic = topic;
        this.mqttMessage = message;
    }

    public void detectDuffelbagNode() throws Exception {

        String [] nodeParts = mqttTopic.split("/");
        if (nodeParts[0].equals("duffelbag") && nodeParts.length == 3) {
            this.nodeType = detectNodeType(nodeParts[1]);
            this.id = new BigInteger(nodeParts[2] , 16).longValue(); // May throw NumberFormatException

            SensorNode sensorObj = createSensor(nodeType, nodeParts[2], mqttTopic);
            if (sensorObj != null) {
                if(!allNodes.isSensorExist(mqttTopic))
                    allNodes.addSensor(sensorObj);
                else {
                    SensorNode sn = allNodes.getSensor(mqttTopic);
                    sn.setValue(floatValue);
                    sn.setTextValue(strValue);
                }
                return;
            }
            ActuatorNode actuatorObj = createActuator(nodeType, nodeParts[2], mqttTopic);
            if (actuatorObj != null) {
                if(!allNodes.isActuatorExist(mqttTopic)) {
                    allNodes.addActuator(actuatorObj);
                }
                return;
            }




        } else {
            /**
             * Parse sensor with non duffelbag format
             * */
            if(!allNodes.isSensorExist(mqttTopic)) {
                SensorNode otherNode = new SensorNode(404, mqttTopic, NodeType.OTHER);
                otherNode.setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
                otherNode.setKnown(false);
                allNodes.addSensor(otherNode);
            } else {
                allNodes.getSensor(mqttTopic).setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
            }
        }
    }

    private NodeType detectNodeType(String strType) {
        NodeType type;
        switch (strType) {
            case "temperature": type = TEMPERATURE; break;
            case "rel_humidity": type = REL_HUMIDITY; break;
            case "atmospheric_pressure": type = ATMOSPHERIC_PRESSURE; break;
            case "rainfall": type = RAINFALL; break;
            case "wind_speed": type = WIND_SPEED; break;
            case "power": type = POWER; break;
            case "power_consumption": type = POWER_CONSUMPTION; break;
            case "voltage": type = VOLTAGE; break;
            case "current": type = CURRENT; break;
            case "water_flow": type = WATER_FLOW; break;
            case "water_consumption": type = WATER_CONSUMPTION; break;
            case "resistance": type = RESISTANCE; break;
            case "gas_concentration": type = GAS_CONCENTRATION; break;
            case "push_button": type = PUSH_BUTTON; break;
            case "swt": type = SWT; break;
            case "text": type = TEXT; break;
            case "rgb": type = RGB; break;
            case "relay": type = RELAY; break;
            case "ac_voltage": type = AC_VOLTAGE; break;
            default: type = OTHER; break;
        }
        return type;
    }

    private SensorNode createSensor(NodeType type, String nodeId, String topic) throws Exception {
        long localId = new BigInteger(nodeId , 16).longValue(); // May throw NumberFormatException
        SensorNode sensor = new SensorNode(localId, topic, type);
        switch (type) {
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
                 * Create sensor with float value
                 * */
                byte [] messageByteArray = mqttMessage.getPayload();
                floatValue = ByteBuffer.wrap(messageByteArray).order(ByteOrder.LITTLE_ENDIAN).getFloat(); //May throw BufferUnderflowException
                 // May throw NumberFormatException
                sensor.setValue(floatValue);
                break;
            case TEXT:
                /**
                 * Parse sensor with duffelbag format and text value
                 * */
                strValue = mqttMessage.toString();
                sensor.setTextValue(strValue);
                break;
            default:
                sensor = null;
        }
        sensor.setKnown(false);
        return sensor;
    }

    private ActuatorNode createActuator(NodeType type, String nodeId, String topic) throws NumberFormatException {
        long localId = new BigInteger(nodeId , 16).longValue(); // May throw NumberFormatException
        ActuatorNode actuator = new ActuatorNode(localId, topic, type);
        switch (type) {
            case RGB:
            case RELAY:
            case AC_VOLTAGE:
                break;
            default:
                actuator = null;
        }
        actuator.setKnown(false);
        return actuator;
    }

    private void parseDuffelbagThingNode(Thing thing) throws ParseException {
        String message = thing.getConfigMessage();
        JSONParser jsonParser = new JSONParser();
        JSONObject mainObject = (JSONObject) jsonParser.parse(message);

        String version = (String) mainObject.get("ver");
        if(version.equals("1.0")) {
            long thingId = (long) mainObject.get("id");
            String thingName = (String) mainObject.get("name");
            String thingLocation = (String) mainObject.get("location");
            JSONArray thingActuators = (JSONArray) mainObject.get("actuators");
            JSONArray thingSensors = (JSONArray) mainObject.get("sensors");
        }

    }
}
