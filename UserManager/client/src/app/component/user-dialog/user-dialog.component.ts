import { NgFor, NgIf } from '@angular/common';
import { Component, ElementRef, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Role } from '../../entity/role';
import { User } from '../../entity/user';
import { UserService } from '../../service/user.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';


@Component({
  selector: 'app-user-dialog',
  standalone: true,
  imports: [ FormsModule, NgFor, NgIf, ReactiveFormsModule ],
  providers: [ UserService ],
  templateUrl: './user-dialog.component.html',
  styleUrl: './user-dialog.component.scss'
})

export class UserDialogComponent implements OnChanges, OnInit {

  @ViewChild('userDialog') modal: ElementRef;

  @Input() title: string;
  @Input() roles: Role[];
  @Input() user: User;
  @Output() refinedUser: User;

  public show = false;

  public userFormGroup: FormGroup;

  private modalDialog: NgbActiveModal;

  constructor(public modalService: NgbModal,
              private userService: UserService) {
    console.log('*** UserDialogComponent()');
  }

  // detect changes to @Input value
  ngOnChanges(changes: SimpleChanges) {
    console.log("ngOnChanges()");
    if (changes['user'] !== undefined) {
      this.user=changes['user'].currentValue;

      if (this.userFormGroup) {
        this.userFormGroup.controls['username'].setValue(this.user.username);
        this.userFormGroup.controls['role'].setValue(this.user.role.roleId);
        this.userFormGroup.markAsPristine();
      }
    }
  }

  ngOnInit() {
    this.userFormGroup = new FormGroup({
      username: new FormControl({value: '', disabled: false}, Validators.required),
      role: new FormControl(null)
    });
  }

  public onClose() {
    console.log('*** onClose()');
  }

  onSubmit() {
    console.log('*** onSubmit()');
    this.showOverlay(true);
    this.userService.save(this.user).subscribe(data => {
      this.user = data;
      this.userFormGroup.markAsPristine();
      console.log('*** onSubmit() - userId: ' + this.user.userId);
      this.closeModal();
    });
  }

  closeModal() {
    console.log('*** UserDialogComponent.closeModal()');

    this.showOverlay(false);

    if (this.userFormGroup.dirty) {
      let confirmText = 'Unsaved changes will be lost. Do you wish to proceed?';

      if (!confirm(confirmText)) {
        return;
      };
    }
    this.modalDialog.close();
  }

  showModal() {
    console.log('*** UserDialogComponent.showModal()');

    this.modalDialog = this.modalService.open(this.modal);
  }
  
  public showOverlay(show: boolean) {
    console.log('*** UserDialogComponent.overlay()');

    this.show = show;
  }
}