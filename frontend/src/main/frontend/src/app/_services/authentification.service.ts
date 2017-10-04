
import { Injectable } from '@angular/core';
import { Http,HttpModule,RequestOptions,RequestMethod,RequestOptionsArgs,Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
//pour test
import 'rxjs/add/operator/do';

import 'rxjs/add/operator/catch';

declare function maketoast(status: string, message: string) : void;

@Injectable()
export class AuthenticationService {
    public value :string;

    constructor(private http: Http) { }

   
 
    login(pseudonym: string, password: string) {
        const headers1 =  new Headers({ 'Access-Control-Allow-Origin': '*' });
        const options = new RequestOptions({  headers: headers1 });
        
              const object = {
                pseudonym: pseudonym,
                password: password,
               };
               
              const url ='http://localhost:8091/rencontre/Member/Connexion?pseudonym='+pseudonym+'&password='+password;
              const url2 = 'https://jsonplaceholder.typicode.com/posts';
            return  this.http.post(url, object, options)
                      .do((res: Response ) => console.log(res.json()))
                       //.map((res: Response ) => res.json());
                        //ajouté a partir dici
                      .map((res: Response) => {
                        
                // login successful if there's a jwt token in the response
                let member = res.json();
                //test
                console.log("hey",member.emailAdress);
                if (member) {
                    // store member details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentMember', JSON.stringify(member));
                    this.value= localStorage.getItem("currentMember");
                    
                    console.log("heycurrentlogin",this.value);
                }
 
                return member;
            });
    }
 
    logout() {
        // remove member from local storage to log user out
        localStorage.removeItem('currentMember');
    }
}
