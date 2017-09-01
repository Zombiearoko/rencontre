import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RestProvider } from '../providers/rest/rest';
import { HomePage } from '../pages/home/home';
import { HelloComponent } from './hello/hello.component';
@NgModule({
  declarations: [
    AppComponent,
     HomePage,
     HelloComponent
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
  bootstrap: [HomePage]
})
export class AppModule { }
