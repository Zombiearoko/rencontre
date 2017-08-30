import { Component } from '@angular/core';

//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';
@Component({
  selector: 'app-root',
  templateUrl: 'home.html'
})
export class HomePage {
  public name:string='rrr';
  data: any = {};
    constructor(public rest:RestProvider) {
       this.getImages();
    }
    public getImages(){
      let nom=this.name;
      this.rest.getUser().subscribe((data)=>{
      console.log(data);
      alert("merrrde je n'arrive pas enore à appeler a affiche champs des objects de l'api voir home.html"  );
        nom=data;
    });
    }
  }