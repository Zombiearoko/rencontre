import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { routing } from './app.routing';

import { RestProvider } from '../providers/rest/rest';
import { HomePage } from '../pages/home/home';
import { HelloComponent } from './hello/hello.component';
import { MemberRegistrationComponent } from './member-registration/member-registration.component';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { FooterComponent } from './footer/footer.component';


import { AlertComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import { AlertService, AuthenticationService, MemberService, CountryService, RegionService} from './_services/index';
import { HomeComponent } from './home/index';
import { MemberSessionComponent } from './member-session/member-session.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { AboutComponent } from './about/about.component';
import { AddRegionComponent } from './add-region/add-region.component';
import { SessionAdminComponent } from './login-admin/session-admin/session-admin.component';
import { NavComponent } from './nav/nav.component';
import { NavAdminComponent } from './nav-admin/nav-admin.component';




@NgModule({
imports: [
  BrowserModule,
   FormsModule,
   ReactiveFormsModule,
   HttpModule,
   routing
 ],


  declarations: [
    AppComponent,
     HomePage,
     AlertComponent,
     HomeComponent,
     HelloComponent,
     MemberRegistrationComponent,
     HeaderComponent,
     LoginFormComponent,
     FooterComponent,
     MemberSessionComponent,//pour add-country
     LoginAdminComponent,
     AboutComponent,
     AddRegionComponent,
     SessionAdminComponent,
     NavComponent,
     NavAdminComponent
  ],
 
  providers: [
     RestProvider,
     AuthGuard,
     AlertService,
     AuthenticationService,
     MemberService,
     CountryService,
     RegionService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
