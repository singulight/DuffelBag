import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 03.03.17
 */

@Component ({
    selector: 'nodelist-wiev',
    template: `
        <div ng-controller="HomeCtrl">
            <h2 class="page-header">Информация сервера</h2>
            <div class="row">
                <div class="col-sm-3">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">Заголовок</h3>
                        </div>
                        <div class="panel-body">Содержание</div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="panel panel-warning">
                        <div class="panel-heading">
                            <h3 class="panel-title">Заголовок</h3>
                        </div>
                        <div class="panel-body">Содержание</div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">Заголовок</h3>
                        </div>
                        <div class="panel-body">Содержание</div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">Заголовок</h3>
                        </div>
                        <div class="panel-body">Содержание</div>
                    </div>
                </div>
            </div>
            <h2 class="page-header">Ноды</h2>
            <div>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Топик</th>
                        <th>Название</th>
                        <th>Значение</th>
                        <th>Локация</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>00000000000000ef</td>
                        <td>{{jsond}}</td>
                        <td>Температура за окном</td>
                        <td>21</td>
                        <td>5867</td>
                        <td><a ref="#">21</a> <a ref="#">22</a> <a ref="#">добавить</a> <a ref="">создать</a></td>
                    </tr>
                    <tr>
                        <td>00000000000000a6</td>
                        <td>duffelbag/pressure/00000000000000a6</td>
                        <td>Атмосферное давление</td>
                        <td>762</td>
                        <td>445</td>
                        <td><a ref="#">23</a> <a ref="#">добавить</a> <a ref="">создать</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>`,
    providers:[WebSocketService]
})
export class NodeListComponent {
    constructor (public webService:WebSocketService) {};
    public jsond: string;
    ngOnInit() {
        this.webService.start();
        this.webService.message.subscribe((msg: any) => {this.jsond = msg.data});
    }
}