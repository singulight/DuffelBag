package sensors;

/**
 * Created by init on 29.12.15.
 */
public interface ISensorAction {

    void setTargetActuator (EachActuator targetActuator);
    EachActuator getTargetActuator();

    void go(float value);
    void go(String value);
}
