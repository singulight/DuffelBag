import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
import {BaseNode} from "./POJOs";
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
                    <tr *ngFor="let node of nodes">
                        <td>{{node.nodeId}}</td>
                        <td>{{node.topic}}</td>
                        <td>{{node.thisName}}</td>
                        <td>{{node.thisValue}}</td>
                        <td>5867</td>
                        <td><a ref="#">21</a> <a ref="#">22</a> <a ref="#">добавить</a> <a ref="">создать</a></td>
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
    public nodes: BaseNode[];

    ngOnInit() {
        this.webService.start();
        let getAllNodesJSON = {
            token:  0,
            ver:    10,
            verb:   'read',
            entity: 'nodes',
            param:  'all'
        };
        this.webService.send(JSON.stringify(getAllNodesJSON));
        this.webService.message.subscribe((msg: any) => {
            if (msg['entity'] === 'nodes') {
                if (msg['verb'] === 'create' && msg['param'] === 'all') {
                    this.nodes = [];
                    this.nodes = msg['data'];
                }
            }
        });
    }
}
