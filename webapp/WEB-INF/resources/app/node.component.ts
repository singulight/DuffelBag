import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
import {TokenCounterService} from "./token-counter.service";
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
                                <table width="100%" ><tr>
                                    <td width="100%"><input class="form-control" name="ID" [(ngModel)]="ID" /></td>
                                    <td width="100%"><button class="btn btn-default" (click)="searchById(ID)">Найти</button></td>
                                </tr></table>
                            </div>
                            
                            <div class="form-group">
                                <label>Сетевое имя (топик)</label>
                                <table width="100%"><tr>
                                    <td width="100%"><input class="form-control" name="topic" [(ngModel)]="topic" /></td>
                                    <td width="100%"><button class="btn btn-default" (click)="searchByTopic(topic)">Найти</button></td>
                                </tr></table>
                            </div>
                            
                            <div class="form-group">
                                <label>Название</label>
                                <input class="form-control" name="name" [(ngModel)]="name" />
                            </div>
                            <div class="form-group">
                                <label>Тип</label>
                                <select class="form-control" name="type" [(ngModel)]="type" >
                                    <option [value]="TEMPERATURE">Датчик температуры</option>
                                    <option [value]="REL_HUMIDITY">Датчик влажности</option>
                                    <option [value]="ATMOSPHERIC_PRESSURE">Датчик атмосферного давления</option>
                                    <option [value]="RAINFALL">Датчик росы</option>
                                    <option [value]="WIND_SPEED">Датчик скорости ветра</option>
                                    <option [value]="POWER">Измеритель мощности</option>
                                    <option [value]="POWER_CONSUMPTION">Датчик потребляемой мощности</option>
                                    <option [value]="VOLTAGE">Измеритель напряжения</option>
                                    <option [value]="CURRENT">Измеритель тока</option>
                                    <option [value]="DIMMER">Регулятор мощности</option>
                                    <option [value]="SWITCH">Выключатель</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Тип</label>
                                <select class="form-control" name="purpose" [(ngModel)]="purpose" >
                                    <option [value]="SENSOR">Датчик</option>
                                    <option [value]="ACTUATOR">Исполнительное устройство</option>
                                    <option [value]="THING">Агрегат</option>
                                </select>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="known" [(ngModel)]="known" /> Конфигурация сформирована
                                </label>                       
                            </div>
                        </div>
                        <div class="col-md-6">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Название</th>
                                        <th>Значение</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Max value</td>
                                        <td>200</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="row">
                                <div class="col-md-5"><input class="form-control" name="new-opt"/></div>
                                <div class="col-md-4"><input class="form-control" name="new-value"/></div>
                                <div class="col-md-3"><button class="btn btn-default" (click)="addRow(new-opt)">Добавить</button></div>
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
    public type: string;
    public purpose: string;
    private getNodeJSON = {
        token: 0,
        ver: 10,
        verb: 'read',
        entity: 'nodes',
        param: 'none'
    }

    ngOnInit() {
        this.webService.start();
        this.webService.message.subscribe((message: any) => {
            console.log(this.tokenCounter.getCurrent());
            if (message['entity'] === 'nodes' &&
                message['verb'] === 'create' &&
                message['ver'] === 10 &&
                message['token'] === this.tokenCounter.getCurrent()) {
                console.log(message);
            }
        });
    }

    public searchById (id: string) {
        this.getNodeJSON.param = id;
        this.send(this.getNodeJSON);
    }

    private send(msg: any) {
        msg.token = this.tokenCounter.getNew();
        this.webService.send(JSON.stringify(msg));
        console.log(JSON.stringify(msg));
    }
}