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

  }