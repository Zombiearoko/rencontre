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
import { AlertService, AuthenticationService, MemberService } from './_services/index';
import { HomeComponent } from './home/index';



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
     FooterComponent
  ],
 
  providers: [
     RestProvider,
     AuthGuard,
     AlertService,
     AuthenticationService,
     MemberService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
