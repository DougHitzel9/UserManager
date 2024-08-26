import { HttpClientTestingModule } from '@angular/common/http/testing';
import { provideLocationMocks } from '@angular/common/testing';
import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AuthService } from './service/auth.service';

describe('AppComponent', () => {

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        provideRouter([{path: '', component: AppComponent}]),
        provideLocationMocks(),
        { provide: AuthService,
          useValue: jasmine.createSpyObj('AuthService', ['isAuthenticated'])
        }
      ]
    }).compileComponents();
  });

  it('should create the app', async () => {
    
    const harness = await RouterTestingHarness.create();
    const component = await harness.navigateByUrl('', AppComponent);

    expect(component).toBeTruthy();
  });

  it(`should have the 'User Manager' title`, async () => {

    const harness = await RouterTestingHarness.create();
    const component = await harness.navigateByUrl('', AppComponent);

    expect(component.title).toEqual('User Manager');
  });

  it('should render title', () => {
    const serviceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>; 
    serviceSpy.isAuthenticated.and.returnValue(true);

    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('#title')?.textContent).toContain('User Manager');
  });
});