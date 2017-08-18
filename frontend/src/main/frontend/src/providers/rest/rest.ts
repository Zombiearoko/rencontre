import { Injectable } from '@angular/core';
import { Http,Response,HttpModule } from '@angular/http';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

/*
  Generated class for the RestProvider provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class RestProvider {
    constructor(public http: Http) {
		 
    }
	
	public getHello(){
	let url='/api/hello?name=FOKO';
		return  this.http.get(url)
            .do((res : Response ) => console.log(res.json()))
            .map((res : Response ) => res.json());
	}

}
