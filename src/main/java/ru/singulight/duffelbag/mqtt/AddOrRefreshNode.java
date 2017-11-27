package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.singulight.duffelbag.nodes.*;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;

import java.math.BigInteger;
import java.util.*;

import org.apache.log4j.Logger;

import static com.sun.imageio.plugins.jpeg.JPEG.version;
import static ru.singulight.duffelbag.nodes.types.NodePurpose.*;
import static ru.singulight.duffelbag.nodes.types.NodeType.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.03.16.
 */
public class AddOrRefreshNode {

    private String mqttTopic;
    private MqttMessage mqttMessage;
    private AllNodes allNodes = AllNodes.getInstance();
    private IdCounter idCounter = IdCounter.getInstance();
    private static final Logger log = Logger.getLogger(AddOrRefreshNode.class);


    public AddOrRefreshNode(String topic, MqttMessage message) {
        this.mqttTopic = topic;
        this.mqttMessage = message;
    }

    /**
     *  @Return id of detected node, null if error
     *  @Param updateValue true will updateNodeValueEvent value, false will not
     *  */
    public Long detectDuffelbagNode(boolean updateValue)  {

        BaseNode currentNode;
        Long id = null;
        
        String [] nodeParts = mqttTopic.split("/");
        if (nodeParts[0].equals("duffelbag") && nodeParts.length == 3) {
            NodeType nodeType = detectNodeType(nodeParts[1]);
            NodePurpose purpose = detectNodePurpose(nodeType);

            try {
                id = new BigInteger(nodeParts[2] , 16).longValue(); // May throw NumberFormatException
            } catch (NumberFormatException nfe) {
                log.error("Duffelbag topic format error: wrong ID. Topic: "+mqttTopic);
                return null;
            }

            currentNode = allNodes.getNodeById(id);
            /* If node exists updateNodeValueEvent value only */
            if (currentNode != null) {
                if (mqttTopic.equals(currentNode.getMqttTopic())) {
                    if (updateValue) {
                        currentNode.setValue(mqttMessage.toString());
                        currentNode.setRawValue(mqttMessage.getPayload());
                    }
                } else {
                    log.error("Existed node id. Current topic: "+mqttTopic+", existed topic: "+currentNode.getMqttTopic());
                    return null;
                }
            /* If node not exists create a new one */
            } else {
                currentNode = new BaseNode();
                try {
                    currentNode.setId(idCounter.checkDbId(id));
                } catch (Exception e) {
                    log.error("Create new duffelbag node error: id not valid. Topic: "+mqttTopic);
                    return null;
                }
                currentNode.setMqttTopic(mqttTopic);
                currentNode.setNodeType(nodeType);
                currentNode.setPurpose(purpose);
                if (updateValue) {
                    currentNode.setValue(mqttMessage.toString());
                    currentNode.setRawValue(mqttMessage.getPayload());
                }
                if (purpose == THING) {
                    try {
                        parseDuffelbagThingConfMessage(currentNode);
                        if (updateValue) {
                            currentNode.setValue(mqttMessage.toString());
                            currentNode.setRawValue(mqttMessage.getPayload());
                        }
                    } catch (Exception e) {
                        log.error("Thing config message parse error. Topic: "+mqttTopic+", payload: "+mqttMessage.toString());
                        return null;
                    }
                }
                allNodes.insert(currentNode);
            }

        } else {
            /**
            * Parse node with non duffelbag format
            * */
            currentNode = allNodes.getNodeByTopic(mqttTopic);
                /* If node exists updateNodeValueEvent value only */
            if (currentNode != null) {
                if(updateValue) currentNode.setRawValue(mqttMessage.getPayload());
                /* If node not exists create a new one */
            } else {
                currentNode = new BaseNode();
                try {
                    id = idCounter.getNewId();
                    currentNode.setId(id);
                } catch (Exception e) {
                    log.error("Create new non duffelbag node error: id too big. Topic: "+mqttTopic);
                    return null;
                }
                currentNode.setMqttTopic(mqttTopic);
                if (updateValue) {
                    currentNode.setValue("unknown");
                    currentNode.setRawValue(mqttMessage.getPayload());
                }
                allNodes.insert(currentNode);
            }
        }
        return id;
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
            case "rgb_lamp": type = RGB_LAMP; break;
            case "switch": type  = SWITCH; break;
            case "outlet": type = OUTLET; break;
            case "dimmer": type = DIMMER; break;
            default: type = OTHER; break;
        }
        return type;
    }

    private NodePurpose detectNodePurpose(NodeType nodeType) {
        NodePurpose nodePurpose = UNKNOWN;
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
                nodePurpose = SENSOR;
                break;
            case RGB:
            case RELAY:
            case AC_VOLTAGE:
                nodePurpose = ACTUATOR;
                break;
            case RGB_LAMP:
            case SWITCH:
            case OUTLET:
            case DIMMER:
                nodePurpose = THING;
                break;
            default:
                nodePurpose = UNKNOWN;
        }
        return nodePurpose;
    }

    private void parseDuffelbagThingConfMessage(BaseNode thing) throws Exception {
        String message = thing.getValue();
        JSONParser jsonParser = new JSONParser();
        JSONObject mainObject = (JSONObject) jsonParser.parse(message);
        List<Long> localIds = new LinkedList<Long>();
        Map<Long, BaseNode> detectedNodes = new HashMap<>();

        String version = (String) mainObject.get("ver");
        thing.setVersion(version);
        if(version.equals("1.0")) {
            thing.setName((String) mainObject.get("name"));
            JSONArray thingNodes = (JSONArray) mainObject.get("nodes");
            /* Parse nodes */
            for (Object thingNode : thingNodes) {
                JSONObject nodeObject = (JSONObject) thingNode;
                String topic = (String) nodeObject.get("topic");
                String name = (String) nodeObject.get("name");
                Map<String, String> options = new HashMap<>();
                JSONArray nodeOptions = (JSONArray) nodeObject.get("options");
                for(Object option : nodeOptions)
                    options.put((String)((JSONObject) option).get("key"), (String)((JSONObject) option).get("value"));

                this.mqttTopic = topic;
                this.mqttMessage = new MqttMessage();
                Long localId = detectDuffelbagNode(false);
                if (localId == null) {
                    /* if error, do rollback*/
                    localIds.forEach((id) -> allNodes.delete(id));
                    throw new Exception();
                }
                localIds.add(localId);
                /*fill node*/
                BaseNode localNode = allNodes.getNodeById(localId);
                localNode.setKnown(true);
                localNode.setName(name);
                localNode.setVersion("1.0");
                localNode.setOptions(options);
            }
        }
    }
}
