package ru.singulight.duffelbag.actions;

import ru.singulight.duffelbag.messagebus.NodeEventObserver;
import ru.singulight.duffelbag.nodes.BaseNode;

import static ru.singulight.duffelbag.messagebus.MessageBus.ACTION;

/**
 * Created by Grigorii info@singulight.ru on 11.01.18.
 */
public class JavaClassAction extends Action {
    public JavaClassAction(){
        super();
        actionId = 34;
        description = "Вывод бессмысленного сообщения в консоль";
    }
    @Override
    public void doAction(BaseNode node) {
        System.out.println("AAAAAAAAAAaaaaaaaaaaaAAAAAAAAAAAAaaaaaaaaaaaAAAAAAAAAAAAAaaaaaaaaaaAAAAAAAAAAAAAAAAAAa");
    }
}
