import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
import {TokenCounterService} from "./token-counter.service";
import {BaseNode, NodeOptions, NodeActions} from "./POJOs";
import any = jasmine.any;
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
                                <td width="100%"><input class="form-control" name="ID" [(ngModel)]="currentNode.id"/></td>
                                <td width="100%">
                                    <button class="btn btn-default" (click)="searchById(currentNode.id)">
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
                                <td width="100%"><input class="form-control" name="topic" [(ngModel)]="currentNode.topic"/></td>
                                <td width="100%">
                                    <button class="btn btn-default" (click)="searchByTopic(currentNode.topic)">
                                        <img src="resources/pic/search.png" alt="" style="vertical-align:middle"> Найти
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="form-group">
                        <label>Название</label>
                        <input class="form-control" name="name" [(ngModel)]="currentNode.name"/>
                    </div>
                    <div class="form-group">
                        <label>Тип</label>
                        <select class="form-control" name="type" [(ngModel)]="currentNode.type">
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
                        <select class="form-control" name="purpose" [(ngModel)]="currentNode.purpose">
                            <option value="SENSOR">Датчик</option>
                            <option value="ACTUATOR">Исполнительное устройство</option>
                            <option value="THING">Агрегат</option>
                            <option value="UNKNOWN">Неизвестно</option>
                        </select>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="known" [(ngModel)]="currentNode.known"/> Конфигурация сформирована
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
                        <tr *ngFor="let option of currentNode.options let i=index ">
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
    providers:[WebSocketService]
})
export class NodeComponent {
    constructor (private webService: WebSocketService, private tokenCounter: TokenCounterService) {}

    public currentNode: BaseNode = new BaseNode();

    private requestPreamble = {
        token: 0,
        ver: 10,
        verb: 'read',
        entity: 'nodes',
        param: 'none',
        data: {}
    }

    ngOnInit() {
        this.webService.start();
        this.webService.message.subscribe((message: any) => {

            if (message['entity'] === 'nodes' &&
                message['verb'] === 'create' &&
                message['ver'] === 10 &&
                message['token'] === this.tokenCounter.getCurrent()) {
                let data = message['data'];
                this.currentNode.id = data[0].id;
                this.currentNode.topic = data[0].topic;
                this.currentNode.name = data[0].name;
                this.currentNode.type = data[0].type;
                this.currentNode.purpose = data[0].purpose;
                this.currentNode.known = data[0].known;
                this.currentNode.options = [];
                this.currentNode.options = data[0].options;
            }
        });
        this.searchByTopic(this.tokenCounter.getTopic());
    }

    public searchById (id: string) {
        this.requestPreamble.param = id;
        this.send(this.requestPreamble);
    }

    public searchByTopic (topic: string) {
        this.requestPreamble.verb = "read";
        this.requestPreamble.param = "byTopic";
        this.requestPreamble.data = topic;
        this.send(this.requestPreamble);
    }

    public sendChanges() {
        this.requestPreamble.verb = "update";
        this.requestPreamble.param = "normal";
        this.requestPreamble.data = this.currentNode;
        this.send(this.requestPreamble);
        console.log("Send");
    }

    public addOption(opt: string, value: string) {
        this.currentNode.options.push({key: opt, value: value});
    }
    public deleteOption(i:number) {
        this.currentNode.options.splice(i,1)
    }

    private send(msg: any) {
        msg.token = this.tokenCounter.getNew();
        this.webService.send(JSON.stringify(msg));
       // console.log(JSON.stringify(msg));

    }
}