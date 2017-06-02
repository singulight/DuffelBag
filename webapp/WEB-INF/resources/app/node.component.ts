import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 03.03.17
 */

@Component ({
    selector: 'node-wiev',
    template: `
                <div class="col-sm-6">
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
                        <select class="form-control" name="type" [(ngModel)]="purpose" >
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
                
               `,
    providers:[WebSocketService]
})
export class NodeComponent {

}