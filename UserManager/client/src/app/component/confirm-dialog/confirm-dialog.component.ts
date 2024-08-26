import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.scss'
})

export class ConfirmDialogComponent {

  @ViewChild('confirmDialog') modal: ElementRef;

  @Input() confirmText: string;
  @Output() confirmEvent = new EventEmitter<boolean>();

  title = 'Confirmation';

  constructor(public modalService: NgbModal) {}

  public showModal() {
    this.modalService.open(this.modal);
  }

  public close(value: boolean) {
    this.confirmEvent.emit(value);
    this.modalService.dismissAll();
  }
}
