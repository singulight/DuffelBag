import {Component} from "@angular/core";
import {WebSocketService} from "./websocket.service";
/**
 * Created by grigorii on 16.09.17.
 */

@Component({
    selector: 'actionlist-view',
    template: `
    <div>
        <h2 class="page-header">Действия</h2>
        <div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Описание</th>
                    <th>Инициаторы</th>
                </tr>
                </thead>
                <tbody>
                <tr *ngFor="let action of actions">
                    <td>{{action.id}}</td>
                    <td>{{action.desc}}</td>
                    <td>
                        <ul>
                            <li *ngFor="let o of action.initiators">
                                {{o}}
                            </li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>`,
    providers: [WebSocketService]
})
export class ActionListComponent {
    constructor (public webService:WebSocketService) {};
    public actions: any[];
    ngOnInit() {
        this.webService.start();
        this.actions = [];
        let getAllActionsJSON = {
            token:0,
            ver:10,
            verb:"read",
            entity:"actions",
            param:"0"
        };
        this.webService.send(JSON.stringify(getAllActionsJSON));
        this.webService.message.subscribe((msg: any) => {
            if(msg['entity'] === 'actions') {
                if(msg['verb'] === 'create') {
                    this.actions = msg['data'];
                }
            }
        });
    }
}