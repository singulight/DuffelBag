package sensors;

/**
 * Created by init on 29.12.15.
 */
public class SetOfRulesSensorAction implements ISensorAction {

    private EachActuator targetActuator;

    @Override
    public void setTargetActuator(EachActuator ta) {
        this.targetActuator = ta;
    }

    @Override
    public EachActuator getTargetActuator() {
        return this.targetActuator;
    }

    @Override
    public void go(float value) {

    }

    @Override
    public void go(String value) {

    }
}
