import { Component } from '@angular/core';
import { HomePage } from '../pages/home/home';
import { RestProvider } from '../providers/rest/rest';
import { Http,Response,HttpModule  } from '@angular/http';
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	rootPage:any =HomePage;
	title = 'app';
	constructor(public rest:RestProvider){
		//this.getName();
	}
}

