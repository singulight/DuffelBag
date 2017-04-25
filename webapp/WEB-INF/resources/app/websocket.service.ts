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
        console.log("start started", this.ws)    // TODO: only for test, must be deleted
        this.socket = this.ws.subscribe({
            next: (msg:MessageEvent) => {
                if (msg['service'] === 'welcome') {
                    self.opened.next(true);
                } else {
                    this.message.next(msg);
                }
                console.log("Message arrived", msg)   // TODO: only for test, must be deleted
            },
            error: () => {
                console.log("Фаел!!!11")   // TODO: only for test, must be deleted
                self.opened.next( false );
                self.socket.unsubscribe();
                setTimeout( () => {
                    self.start();
                }, 1000 );
            },
            complete: () => {
                self.opened.next(false);
            }
        });
    }
    public close(): void {
        this.socket.unsubscribe();
        this.ws.complete();
    }
    public send(msg: string): void {
        this.ws.next(msg);
    }
}