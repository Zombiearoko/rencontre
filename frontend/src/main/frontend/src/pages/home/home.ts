import { Component } from '@angular/core';
import { Response,HttpModule,RequestOptions,RequestMethod,RequestOptionsArgs,Http,Headers } from '@angular/http';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';
@Component({
  selector: 'app-home',
  templateUrl: 'home.html',
  styleUrls: ['./home.scss']
})
export class HomePage {
  public name:string='rrr';
 private results: [any];
 private collectionJson: object;

    constructor(private http:Http) {
       this.getInfo();
    }
    public getInfo(){
     let url7= "http://jsonplaceholder.typicode.com/posts";
      this.http.get(url7).subscribe((resp)=>{
        this.results = resp['results'];
        this.collectionJson = resp.json();
      console.log(this.collectionJson);
      // alert("merrrde je n'arrive pas enore à appeler a affiche champs des objects de l'api voir home.html"  );
        // nom=data;
    });
  //   constructor(private http:Http, private rest: RestProvider) {
  //     this.getData();
  //  }
  //  public getData(){
  //   let url7= "http://jsonplaceholder.typicode.com/posts";
  //   this.rest.getUser()
  //   .do((res: Response) => console.log(res.json()));
  //   // .subscribe((resp)=>{
    //    this.results = resp['results'];
    //     this.collectionJson = resp.json();
    //     console.log(this.collectionJson);
    // 	nom=data;
    //  alert("merrrde je n'arrive pas enore à appeler a affiche champs des objects de l'api voir home.html"  );
    //    nom=data;
  //  });
    }

<<<<<<< HEAD
  }
=======
=======
import { Component } from '@angular/core';

//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';
@Component({
  selector: 'app-root',
  templateUrl: 'home.html'
})
export class HomePage {
<<<<<<< HEAD
public name:string='rrr';
  constructor(public rest:RestProvider) {
	//	this.getName();
  }
 /* public getName(){
    let nom=this.name;
    this.rest.getHello().subscribe((data)=>{
		console.log(data);
		alert("name: "+data);
    	nom=data;
	});
  }*/
}
=======
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
<<<<<<< HEAD
  }
>>>>>>> a5da2ec78b6390d9e8ba488e6224ee0a7eb57bac
=======
>>>>>>> 1aab8853c67f650c7d1775e7066f376605967a87
  }
>>>>>>> 8552e6d2a3486e9043b9acd79d193c37f796eb88
>>>>>>> 9244047c072cc895dabb3332a3971842ed06610c
