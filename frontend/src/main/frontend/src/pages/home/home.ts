import { Component } from '@angular/core';

//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';
@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
public name:string='rrr';
  constructor(public rest:RestProvider) {
		this.getName();
  }
  public getName(){
    let nom=this.name;
    this.rest.getHello().subscribe((data)=>{
		console.log(data);
		alert("name: "+data);
    	nom=data;
	});
  }
}
