/**
 * Created by grigorii on 04.02.18.
 */
import { Directive, ElementRef, EventEmitter, Input, Output } from '@angular/core';

import 'brace';
import 'brace/theme/eclipse';
import 'brace/mode/lua';
declare var ace: any;

@Directive({
    selector: '[ace-edit]'
})
export class AceDirective {
    _readOnly: any;
    _theme: any;
    _mode: any;

    editor: any;
    oldVal: any;

    @Input() set options(value: any) {
        this.editor.setOptions(value || {});
    }

    @Input() set readOnly(value: any) {
        this._readOnly = value;
        this.editor.setReadOnly(value);
    }

    @Input() set theme(value: any) {
        this._theme = value;
        this.editor.setTheme(`ace/theme/${value}`);
    }

    @Input() set mode(value: any) {
        this._mode = value;
        this.editor.getSession().setMode(`ace/mode/${value}`);
    }

    @Input() set text(value: any) {
        if(value === this.oldVal) return;
        this.editor.setValue(value);
        this.editor.clearSelection();
        this.editor.focus();
    }

    @Output() textChanged = new EventEmitter();
    @Output() editorRef = new EventEmitter();

    constructor(private elementRef: ElementRef) {
        const el = elementRef.nativeElement;
        el.classList.add('editor');

        this.editor = ace.edit(el);

        setTimeout(() => {
            this.editorRef.next(this.editor);
        });

        this.editor.on('change', () => {
            const newVal = this.editor.getValue();
            if(newVal === this.oldVal) return;
            if(typeof this.oldVal !== 'undefined') {
                this.textChanged.next(newVal);
            }
            this.oldVal = newVal;
        });
    }
}