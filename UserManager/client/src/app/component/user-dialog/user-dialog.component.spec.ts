import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { MockRender } from 'ng-mocks';
import { Role } from '../../entity/role';
import { User } from '../../entity/user';
import { UserDialogComponent } from './user-dialog.component';

describe('UserDialogComponent', () => {

  let component: UserDialogComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, UserDialogComponent]
    }).compileComponents();

    let title = 'Add User';

    let roles: Role[] = [ { roleId: 1, role: 'Administrator' },
                          { roleId: 2, role: 'Editor' },
                          { roleId: 3, role: 'User' } ];

    const params = { title, roles, user: {} };

    const fixture = MockRender(UserDialogComponent, params);

    component = fixture.point.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return sum of a and b', () => {
    let a = 3;
    let b = 4;

    expect(component.add(a,b)).toEqual(7);
  });

  it ('should detect changes', () => {
    let title = 'Add User';

    let roles: Role[] = [ { roleId: 1, role: 'Administrator' },
                          { roleId: 2, role: 'Editor' },
                          { roleId: 3, role: 'User' } ];

    const params = { title, roles, user: {} };

    const fixture = MockRender(UserDialogComponent, params);

    expect(fixture.point.componentInstance.user).toBeTruthy();

    let user = new User;
    user.username = 'Changed';

    params.user = user;

    fixture.detectChanges();

    expect(fixture.point.componentInstance.user.username).toEqual('Changed');
  })
});
