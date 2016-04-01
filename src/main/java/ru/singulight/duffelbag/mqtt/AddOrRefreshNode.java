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

import org.apache.log4j.Logger;

import static ru.singulight.duffelbag.mqttnodes.types.NodeType.*;

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
    private static final Logger log = Logger.getLogger(AddOrRefreshNode.class);


    public AddOrRefreshNode(String topic, MqttMessage message) {
        this.mqttTopic = topic;
        this.mqttMessage = message;
    }

    public void detectDuffelbagNode()  {

        String [] nodeParts = mqttTopic.split("/");
        if (nodeParts[0].equals("duffelbag") && nodeParts.length == 3) {
            this.nodeType = detectNodeType(nodeParts[1]);
            try {
                this.id = new BigInteger(nodeParts[2] , 16).longValue(); // May throw NumberFormatException
            } catch (NumberFormatException nfe) {
                log.error("Node parse error: wrong ID. Topic: "+mqttTopic);
                return;
            }
            SensorNode sensorObj = null;
            try {
                sensorObj = createSensor(nodeType, nodeParts[2], mqttTopic);
            } catch (Exception e) {
                log.error("Node parse error in sensor case. Topic: "+mqttTopic);
                return;
            }
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
            ActuatorNode actuatorObj = null;
            try {
                actuatorObj = createActuator(nodeType, nodeParts[2], mqttTopic);
            } catch (Exception e) {
                log.error("Node parse error in actuator case. Topic: "+mqttTopic);
                return;
            }
            if (actuatorObj != null) {
                if(!allNodes.isActuatorExist(mqttTopic)) {
                    allNodes.addActuator(actuatorObj);
                }
                return;
            }
            Thing thingObj = null;
            try {
                thingObj = createThing(nodeType, nodeParts[2], mqttTopic);
            } catch (Exception e) {
                log.error("Node parse error in thing case. Topic: "+mqttTopic);
                return;
            }
            if (thingObj != null) {
                if (!allNodes.isThingExist(mqttTopic)) {
                    allNodes.addThing(thingObj);
                }
                return;
            }

        } else {
            /**
                        * Parse sensor with non duffelbag format
                        * */
            if(!allNodes.isSensorExist(mqttTopic)) {
                log.info("Add non duffelbag unknown format node: "+mqttTopic);
                SensorNode otherNode = new SensorNode(404, mqttTopic, NodeType.OTHER);
                otherNode.setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
                otherNode.setKnown(false);
                otherNode.setVersion("0");
                allNodes.addSensor(otherNode);
            } else {
                allNodes.getSensor(mqttTopic).setTextValue(Base64.getEncoder().encodeToString(mqttMessage.getPayload()));
            }
        }
    }

    public NodeType detectNodeType(String strType) {
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
            case "rgb_lamp": type = RGB_LAMP; break;
            case "switch": type  = SWITCH; break;
            case "outlet": type = OUTLET; break;
            case "dimmer": type = DIMMER; break;
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
        return actuator;
    }

    private Thing createThing(NodeType type, String nodeId, String topic) throws NumberFormatException {
        long localId = new BigInteger(nodeId , 16).longValue(); // May throw NumberFormatException
        Thing thing = new Thing(localId, topic, type);
        thing.setMqttTopic(topic);
        switch (type) {
            case RGB_LAMP:
            case SWITCH:
            case OUTLET:
            case DIMMER:
                thing.setConfigMessage(mqttMessage.toString());
                try {
                    parseDuffelbagThingConfMessage(thing);
                } catch (Exception e) {
                    log.error("Thing config message parse error. Topic: "+mqttTopic+" Message: "+mqttMessage.toString());
                    thing = null;
                }
                break;
            default:
                thing = null;
        }
        return thing;
    }

    private void parseDuffelbagThingConfMessage(Thing thing) throws Exception {
        String message = thing.getConfigMessage();
        JSONParser jsonParser = new JSONParser();
        JSONObject mainObject = (JSONObject) jsonParser.parse(message);

        String version = (String) mainObject.get("ver");
        thing.setVersion(version);
        if(version.equals("1.0")) {
            thing.setName((String) mainObject.get("name"));
            thing.setLocation((String) mainObject.get("location"));
            JSONArray thingActuators = (JSONArray) mainObject.get("actuators");
            JSONArray thingSensors = (JSONArray) mainObject.get("sensors");

            for (Object thingActuator : thingActuators) {
                JSONObject actuatorObject = (JSONObject) thingActuator;
                String actuatorTopic = (String) actuatorObject.get("topic");
                String actuatorName = (String) actuatorObject.get("name");
                String actuatorMinValueStr = (String) actuatorObject.get("minvalue");
                String actuatorMaxValueStr = (String) actuatorObject.get("maxvalue");
                float actuatorMinValue = Float.parseFloat(actuatorMinValueStr);
                float actuatorMaxValue = Float.parseFloat(actuatorMaxValueStr);
                String[] splitTopic = actuatorTopic.split("/");
                if (splitTopic[0].equals("duffelbag") && splitTopic.length == 3) {
                    NodeType type = detectNodeType(splitTopic[1]);
                    ActuatorNode actuator = createActuator(type, splitTopic[2], actuatorTopic);
                    if (actuator == null) throw new ParseException(0);
                    if (!allNodes.isActuatorExist(actuatorTopic)) {
                        allNodes.addActuator(actuator);
                        thing.addActuator(actuator);
                    }
                    ActuatorNode actuatorFromMap = allNodes.getActuator(actuatorTopic);
                    if (!actuatorFromMap.isKnown()) {
                        actuatorFromMap.setName(actuatorName);
                        actuatorFromMap.setMinValue(actuatorMinValue);
                        actuatorFromMap.setMaxValue(actuatorMaxValue);
                        actuatorFromMap.setKnown(true);
                        actuatorFromMap.setVersion(version);
                    }
                } else throw new ParseException(0);
            }
            for (Object thingSensor : thingSensors) {
                JSONObject sensorObject = (JSONObject) thingSensor;
                String sensorTopic = (String) sensorObject.get("topic");
                String sensorName = (String) sensorObject.get("name");
                String sensorMinValueStr = (String) sensorObject.get("minvalue");
                String sensorMaxValueStr = (String) sensorObject.get("maxvalue");
                float sensorMinValue = Float.parseFloat(sensorMinValueStr);
                float sensorMaxValue = Float.parseFloat(sensorMaxValueStr);

                String[] splitTopic = sensorTopic.split("/");
                if (splitTopic[0].equals("duffelbag") && splitTopic.length == 3) {
                    NodeType type = detectNodeType(splitTopic[1]);
                    SensorNode sensor = createSensor(type, splitTopic[2], sensorTopic);
                    if (sensor == null) throw new ParseException(0);
                    if (!allNodes.isSensorExist(sensorTopic)) {
                        allNodes.addSensor(sensor);
                        thing.addSensor(sensor);
                    }
                    SensorNode sensorFromMap = allNodes.getSensor(sensorTopic);
                    if (!sensorFromMap.isKnown()) {
                        sensorFromMap.setName(sensorName);
                        sensorFromMap.setMinValue(sensorMinValue);
                        sensorFromMap.setMaxValue(sensorMaxValue);
                        sensorFromMap.setKnown(true);
                        sensorFromMap.setVersion(version);

                    }
                } else throw new ParseException(0);
            }
        }
    }
}
