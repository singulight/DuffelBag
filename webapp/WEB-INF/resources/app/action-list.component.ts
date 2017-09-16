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
                    <th>Название</th>
                    <th>Инициаторы</th>
                </tr>
                </thead>
                <tbody>
                
                </tbody>
            </table>
        </div>
    </div>`,
    providers: [WebSocketService]
})
export class ActionListComponent {

}