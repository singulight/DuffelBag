/**
    * Created by Grigorii Nizovoi info@singulight.ru on 24.02.17.
    */
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {Routes, RouterModule} from "@angular/router";

import {MainComponent}  from './main.component';
import {NodeListComponent} from "./node-list.component";
import {NodeComponent} from "./node.component";
import {NotFoundComponent} from "./notfound.component";
import {WebSocketService} from "./websocket.service";
import {TokenCounterService} from "./token-counter.service";

const appRoutes : Routes = [
    {path:'', component: NodeListComponent},
    {path:'nodelist', component: NodeListComponent},
    {path:'node', component: NodeComponent},
    {path:'**', component: NotFoundComponent}
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes), FormsModule ],
    declarations: [ MainComponent, NodeListComponent, NodeComponent, NotFoundComponent ],
    providers:    [ WebSocketService, TokenCounterService],
    bootstrap:    [ MainComponent ]
})
export class AppModule {}