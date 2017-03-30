/**
    * Created by Grigorii Nizovoi info@singulight.ru on 24.02.17.
    */
import { Component } from '@angular/core';

@Component({
    selector: 'main-view',
    template: `
        <div>
            <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <div class="container-fluid">
                    <a class="navbar-brand" href="">Duffel Bag</a>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Главная</a></li>
                        <li><a href="#">Установки</a></li>
                        <li><a href="#">Действия</a></li>
                        <li><a href="#">Помощь</a></li>
                    </ul>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2 sidebar">
                        <ul class="nav nav-sidebar">
                            <li routerLinkActive="active"><a routerLink="nodelist">Информация</a></li>
                            <li routerLinkActive="active"><a routerLink="node">Ноды</a></li>
                            <li routerLinkActive="active"><a routerLink="actions">Действия</a></li>
                            <li routerLinkActive="active"><a routerLink="locations">Расположения</a></li>
                        </ul>
                    </div>
                    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                        <router-outlet></router-outlet>
                    </div>
                </div>
            </div>
        </div>`
})
export class MainComponent  {}