/**
 * Created by Grigorii Nizovoi info@singulight.ru on 31.03.17
 */
export class BaseNode {
    public ver: string;
    public id: number;
    public topic: string;
    public name: string;
    public known: boolean;
    public type: string;
    public purpose: string;
    public value: string;
    public options: NodeOptions [];
    public actions: NodeActions [];
}

export class NodeOptions {
    public key: string;
    public value: string;
}

export class NodeActions {
    public actionId: number;
    public actionName: string;
}