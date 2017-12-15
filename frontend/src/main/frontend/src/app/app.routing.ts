import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index';
import { LoginFormComponent } from './login-form/index';
import { MemberRegistrationComponent } from './member-registration/index';
import { MemberRegister1Component } from './member-register1/member-register1.component';
import { MemberSessionComponent } from './member-session/member-session.component';

import { AddRegionComponent } from './add-region/add-region.component';
import { AddDepartmentComponent } from './add-department/add-department.component';
import { AddBoroughComponent } from './add-borough/add-borough.component';
import { AddTownComponent } from './add-town/add-town.component';
import { AddMeetingComponent } from './add-meeting/add-meeting.component';
import { AddStatusComponent } from './add-status/add-status.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { AboutComponent } from './about/about.component';
import { SessionAdminComponent } from './login-admin/session-admin/session-admin.component';
import { ConfimrAccountComponent } from './member-registration/confimr-account/confimr-account.component';
import { RencotreComponent } from './rencotre/rencotre.component';
import { AuthGuard } from './_guards/index';
import { AdminRegistrationComponent } from './admin-registration/admin-registration.component';


const appRoutes: Routes = [
    { path: 'confimr-account', component: ConfimrAccountComponent },
    { path: 'rencotre', component: RencotreComponent },
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    // { path: 'home', component: HomeComponent },
    { path: 'login-form', component: LoginFormComponent },
    { path: 'member-registration', component: MemberRegistrationComponent },
    { path: 'member-register1', component: MemberRegister1Component },
    // { path: 'add-country', component: AddCountryComponent },add country=member-session
    { path: 'add-region', component: AddRegionComponent },
    { path: 'add-department', component: AddDepartmentComponent },
    { path: 'add-borough', component: AddBoroughComponent },
    { path: 'add-town', component: AddTownComponent },
    { path: 'add-meeting', component: AddMeetingComponent },
    { path: 'add-status', component: AddStatusComponent },
    { path: 'member-session', component: MemberSessionComponent },
    { path: 'login-admin', component: LoginAdminComponent },
    { path: 'about', component: AboutComponent },
    { path: 'session-admin', component: SessionAdminComponent },
    { path: 'admin-registration', component: AdminRegistrationComponent },

    // otherwise redirect to rencotre
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);