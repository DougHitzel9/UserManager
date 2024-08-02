import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-info-dialog',
  standalone: true,
  imports: [],
  templateUrl: './info-dialog.component.html',
  styleUrl: './info-dialog.component.scss'
})
export class InfoDialogComponent {

  @ViewChild('infoDialog') modal: ElementRef;

  @Input() infoText: string;

  title = 'Information';

  constructor(public modalService: NgbModal) {}

  showModal() {
    this.modalService.open(this.modal);
  }

  close() {
    this.modalService.dismissAll();
  }
}
