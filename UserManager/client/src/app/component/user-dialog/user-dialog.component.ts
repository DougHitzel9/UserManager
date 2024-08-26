import { NgFor, NgIf } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Role } from '../../entity/role';
import { User } from '../../entity/user';
import { UserService } from '../../service/user.service';


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
  @Output() saveEvent = new EventEmitter<User>();

  public show = false;

  public userFormGroup: FormGroup;

  private modalDialog: NgbActiveModal;

  constructor(public modalService: NgbModal,
              private userService: UserService) {
    console.log('*** UserDialogComponent()');
  }

  // detect changes to @Input value
  public ngOnChanges(changes: SimpleChanges) {
    console.log("*** ngOnChanges()");
    if (changes['user'] !== undefined) {
      this.user=changes['user'].currentValue;

      if (this.userFormGroup) {
        this.userFormGroup.controls['username'].setValue(this.user.username);
        this.userFormGroup.controls['role'].setValue(this.user.role.roleId);
        this.userFormGroup.markAsPristine();
      }
    }
  }

  public ngOnInit() {
    this.userFormGroup = new FormGroup({
      username: new FormControl({value: '', disabled: false}, Validators.required),
      role: new FormControl(null)
    });
  }

  public onSubmit() {
    this.user.username = this.userFormGroup.controls['username'].value;
    this.user.role.roleId = this.userFormGroup.controls['role'].value;

    console.log('*** onSubmit() - ' + this.user.username + ' : ' + this.user.role.roleId + ' : ' + this.user.transaction);
    this.showOverlay();
    this.userService.save(this.user).subscribe(data => {
      let user: User = data;

      // copy fields to preserve transaction
      this.user.userId = user.userId;
      this.user.username = user.username;
      this.user.role.roleId = user.role.roleId;
      this.user.role.role = user.role.role;

      this.userFormGroup.markAsPristine();
      console.log('*** onSubmit() - userId: ' + this.user.userId + ' : ' + this.user.transaction);

      // return user to parent
      this.saveEvent.emit(this.user);
      this.closeModal();
    });
  }

  public closeModal() {
    if (this.userFormGroup.dirty) {
      let confirmText = 'Unsaved changes will be lost. Do you wish to proceed?';

      if (!confirm(confirmText)) {
        return;
      };
    }
    this.modalDialog.close();
  }

  public showModal() {
    this.modalDialog = this.modalService.open(this.modal);
  }

  public add(a: number, b: number): number {
    return a + b;
  }

  private showOverlay() {
    this.show = true;
  }
}