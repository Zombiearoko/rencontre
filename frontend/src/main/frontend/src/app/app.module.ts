import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RestProvider } from '../providers/rest/rest';
import { HomePage } from '../pages/home/home';
@NgModule({
  declarations: [
    AppComponent,
     HomePage
  ],
  imports: [
    BrowserModule
  ],
  providers: [
  	 RestProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
