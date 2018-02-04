/**
 * Created by grigorii on 01.02.18.
 */

import { Component } from '@angular/core';


@Component({
    selector: 'actionlua',
    template: `
        <div class="container-fluid">
            
        </div>
        <div ace-edit 
             [(text)]="text"
             [mode]="'lua'"
             [theme]="'eclipse'"
             [options]="options"
             [readOnly]="false"
             (textChanged)="onChange($event)"
             style="min-height: 500px; width:50%; overflow: auto;">
            
        </div>`
})

export class ActionLuaComponent {

   onChange(txt: any) {
        console.log(txt);
    }
}