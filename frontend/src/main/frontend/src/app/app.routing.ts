import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index';
import { LoginFormComponent } from './login-form/index';
import {MemberRegistrationComponent } from './member-registration/index';
import {MemberSessionComponent } from './member-session/member-session.component';
import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login-form', component: LoginFormComponent },
    { path: 'member-registration', component: MemberRegistrationComponent },
    // { path: 'home', component: HomeComponent },
    { path: 'member-session', component: MemberSessionComponent },
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);