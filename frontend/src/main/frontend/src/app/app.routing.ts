import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index';
import { LoginFormComponent } from './login-form/index';
import {MemberRegistrationComponent } from './member-registration/index';
import {MemberSessionComponent } from './member-session/member-session.component';
import {AddCountryComponent  } from './add-country/add-country.component';
import {LoginAdminComponent } from './login-admin/login-admin.component';
import {AboutComponent } from './about/about.component';



import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login-form', component: LoginFormComponent },
    { path: 'member-registration', component: MemberRegistrationComponent },
    { path: 'add-country', component: AddCountryComponent },
    { path: 'member-session', component: MemberSessionComponent },
    { path: 'login-admin', component: LoginAdminComponent },
    { path: 'about', component: AboutComponent },
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);