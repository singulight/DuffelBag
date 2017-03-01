/**
    * Created by Grigorii Nizovoi info@singulight.ru on 24.02.17.
    */
import { Component } from '@angular/core';

@Component({
    selector: 'ng-view',
    template: `<h1>Hello {{count}}</h1>
             <button (click)="increase($event)">Increase</button>`
})
export class AppComponent  {
    nm: string = "Gooooq";
    age: number = 24;
    count : number = 0;
    increase(): void {
        this.count++;
    }
    chg(nm:number) {
        if (nm == 1) this.count++ ; else this.count-- ;
    }
}