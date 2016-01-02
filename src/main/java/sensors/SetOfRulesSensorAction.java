package sensors;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 29.12.15.
 */
public class SetOfRulesSensorAction implements ISensorAction {

    private int actionId;
    private EachActuator targetActuator;

    @Override
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public int getActionId() {
        return actionId;
    }

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
