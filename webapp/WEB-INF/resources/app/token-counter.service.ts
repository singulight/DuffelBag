/**
 * Created by Grigorii Nizovoi info@singulight.ru on 15.06.17
 */
import {Injectable} from "@angular/core";

@Injectable()
export class TokenCounterService {
    constructor () {}
    private count: number = 999;
    getNew (): number {
        if (this.count > 2147483640) this.count = 999;
        return ++this.count;
    }
    getCurrent(): number {
        return this.count;
    }
}
