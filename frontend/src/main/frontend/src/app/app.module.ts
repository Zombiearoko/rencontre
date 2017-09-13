import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RestProvider } from '../providers/rest/rest';
import { HomePage } from '../pages/home/home';
import { HelloComponent } from './hello/hello.component';
import { MemberRegistrationComponent } from './member-registration/member-registration.component';
@NgModule({
  declarations: [
    AppComponent,
     HomePage,
     HelloComponent,
     MemberRegistrationComponent
  ],
  imports: [
   BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule
  ],
  providers: [
  	 RestProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
