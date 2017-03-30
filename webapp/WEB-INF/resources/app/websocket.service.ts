/**
 * Created by Grigorii Nizovoi info@singulight.ru on 20.03.17
 */
import {WebSocketSubject} from "rxjs/observable/dom/WebSocketSubject";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {Subscription} from "rxjs";
import {Observable} from "rxjs";

@Injectable()
export class WebSocketService {
    constructor() {}

    private ws: WebSocketSubject<Object>;
    private socket: Subscription;
    private url = "ws://localhost:8080";

    public message: Subject<Object> = new Subject();
    public opened: Subject<boolean> = new Subject();

    public start(): void {
        let self = this;
        this.ws = Observable.webSocket(this.url);
        console.log("start started", this.ws)
        this.socket = this.ws.subscribe({
            next: (msg:MessageEvent) => {
                this.message.next(msg);
                console.log("Message arrived")
            },
            error: () => {
                console.log("Error")
            },
            complete: () => {

            }
        });

    }
}