import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
import {TokenCounterService} from "./token-counter.service";
import {BaseNode, NodeOptions, NodeActions} from "./POJOs";
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 03.03.17
 */

@Component ({
    selector: 'node-wiev',
    template: `
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Идентификатор</label>
                        <table width="100%">
                            <tr>
                                <td width="100%"><input class="form-control" name="ID" [(ngModel)]="ID"/></td>
                                <td width="100%">
                                    <button class="btn btn-default" (click)="searchById(ID)">
                                        <img src="resources/pic/search.png" alt="" style="vertical-align:middle"> Найти
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="form-group">
                        <label>Сетевое имя (топик)</label>
                        <table width="100%">
                            <tr>
                                <td width="100%"><input class="form-control" name="topic" [(ngModel)]="topic"/></td>
                                <td width="100%">
                                    <button class="btn btn-default" (click)="searchByTopic(topic)">
                                        <img src="resources/pic/search.png" alt="" style="vertical-align:middle"> Найти
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="form-group">
                        <label>Название</label>
                        <input class="form-control" name="name" [(ngModel)]="name"/>
                    </div>
                    <div class="form-group">
                        <label>Тип</label>
                        <select class="form-control" name="type" [(ngModel)]="type1">
                            <option value="TEMPERATURE">Датчик температуры</option>
                            <option value="REL_HUMIDITY">Датчик влажности</option>
                            <option value="ATMOSPHERIC_PRESSURE">Датчик атмосферного давления</option>
                            <option value="RAINFALL">Датчик росы</option>
                            <option value="WIND_SPEED">Датчик скорости ветра</option>
                            <option value="POWER">Измеритель мощности</option>
                            <option value="POWER_CONSUMPTION">Датчик потребляемой мощности</option>
                            <option value="VOLTAGE">Измеритель напряжения</option>
                            <option value="CURRENT">Измеритель тока</option>
                            <option value="TEXT">Текстовая информация</option>
                            <option value="DIMMER">Регулятор мощности</option>
                            <option value="SWITCH">Выключатель</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Тип</label>
                        <select class="form-control" name="purpose" [(ngModel)]="purpose">
                            <option value="SENSOR">Датчик</option>
                            <option value="ACTUATOR">Исполнительное устройство</option>
                            <option value="THING">Агрегат</option>
                            <option value="UNKNOWN">Неизвестно</option>
                        </select>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="known" [(ngModel)]="known"/> Конфигурация сформирована
                        </label>
                    </div>
                    <button class="btn btn-default" (click)="sendChanges()">
                        <img src="resources/pic/accept.png" alt="" style="vertical-align:middle"> Сохранить
                    </button>
                </div>
                <div class="col-md-6">
                    <label>Параметры</label>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th style="width: 50%">Название</th>
                            <th style="width: 30%">Значение</th>
                            <th style="width: 20%">Управление</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr *ngFor="let option of nodeOpts let i=index ">
                            <td>{{option.key}}</td>
                            <td>{{option.value}}</td>
                            <td><img src="resources/pic/delete.png" (click)="deleteOption(i)" alt="Удалить"></td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="col-md-5"><input class="form-control" name="new_opt_key" #new_opt_key/></div>
                        <div class="col-md-4"><input class="form-control" name="new_opt_value" #new_opt_value/></div>
                        <div class="col-md-3">
                            <button class="btn btn-default" type="image"
                                    (click)="addOption(new_opt_key.value, new_opt_value.value)">
                                <img src="resources/pic/add.png" alt="" style="vertical-align:middle"> Добавить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    providers:[WebSocketService, TokenCounterService]
})
export class NodeComponent {
    constructor (public webService: WebSocketService, public tokenCounter: TokenCounterService) {}

    public ID: string;
    public topic: string;
    public name: string;
    public type1: string;
    public purpose: string;
    public known: boolean;
    public nodeOpts: NodeOptions[] = new Array();
    public nodeActs: NodeActions[] = new Array();

    private getNodeJSON = {
        token: 0,
        ver: 10,
        verb: 'read',
        entity: 'nodes',
        param: 'none',
        data: 'none'
    }

    private thisNode: BaseNode;

    private nodeJSON = {
        ver: "1.0",
        id: 0,
        topic: "",
        name: "",
        known: false,
        type: 1,
        purpose: "SENSOR",
        value: "10",
        options: [""]
    }

    ngOnInit() {
        this.webService.start();
        this.webService.message.subscribe((message: any) => {

            if (message['entity'] === 'nodes' &&
                message['verb'] === 'create' &&
                message['ver'] === 10 &&
                message['token'] === this.tokenCounter.getCurrent()) {
                let data = message['data'];
                this.ID = data[0].id;
                this.topic = data[0].topic;
                this.name = data[0].name;
                this.type1 = data[0].type;
                this.purpose = data[0].purpose;
                this.known = data[0].known;
                this.nodeOpts = [];
                this.nodeOpts = data[0].options;
            }
        });
    }

    public searchById (id: string) {
        this.getNodeJSON.param = id;
        this.send(this.getNodeJSON);
    }

    public searchByTopic (topic: string) {
        this.getNodeJSON.param = "byTopic";
        this.getNodeJSON.data = topic;
        this.send(this.getNodeJSON);
    }

    public sendChanges() {
        console.log("Send");
    }

    public addOption(opt: string, value: string) {
        this.nodeOpts.push({key: opt, value: value});
    }
    public deleteOption(i:number) {
        this.nodeOpts.splice(i,1)
    }

    private send(msg: any) {
        msg.token = this.tokenCounter.getNew();
        this.webService.send(JSON.stringify(msg));
       // console.log(JSON.stringify(msg));

    }
}