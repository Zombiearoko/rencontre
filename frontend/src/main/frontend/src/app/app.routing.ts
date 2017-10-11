import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index';
import { LoginFormComponent } from './login-form/index';
import {MemberRegistrationComponent } from './member-registration/index';
import {MemberSessionComponent } from './member-session/member-session.component';
import {AddRegionComponent  } from './add-region/add-region.component';
import {LoginAdminComponent } from './login-admin/login-admin.component';
import {AboutComponent } from './about/about.component';
import {SessionAdminComponent } from './login-admin/session-admin/session-admin.component';



import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login-form', component: LoginFormComponent },
    { path: 'member-registration', component: MemberRegistrationComponent },
    // { path: 'add-country', component: AddCountryComponent },add country=member-session
    { path: 'add-region', component: AddRegionComponent },    
    { path: 'member-session', component: MemberSessionComponent },
    { path: 'login-admin', component: LoginAdminComponent },
    { path: 'about', component: AboutComponent },
    { path: 'session-admin', component: SessionAdminComponent },
    
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);