/**
 * Created by Grigorii Nizovoi info@singulight.ru on 31.03.17
 */
export class BaseNode {
    public ver: number;
    public nodeId: number;
    public topic: string;
    public thisName: string;
    public thisValue: string;
    public thisOptions: string [];
}

export class NodeOptions {
    public optionName: string;
    public optionValue: string;
}

export class NodeActions {
    public actionId: number;
}