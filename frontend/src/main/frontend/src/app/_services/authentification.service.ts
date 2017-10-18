
import { Injectable } from '@angular/core';
import { Http, HttpModule, RequestOptions, RequestMethod, RequestOptionsArgs, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
//pour test
import 'rxjs/add/operator/do';

import 'rxjs/add/operator/catch';

@Injectable()
export class AuthenticationService {
    public value: string;
    public valueC: string;


    constructor(private http: Http) {

    }

    login(pseudonym: string, password: string, meetingName: string) {
        const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
        const options = new RequestOptions({ headers: headers1 });

        const object = {
            pseudonym: pseudonym,
            password: password,
            meetingName: meetingName,
        };

        const url = 'http://localhost:8091/rencontre/Member/Connexion?pseudonym=' + pseudonym + '&password=' + password + '&meetingName=' + meetingName;
        return this.http.post(url, object, options)
            .do((res: Response) => console.log(res.json()))
            //.map((res: Response ) => res.json());
            //ajouté a partir dici
            .map((res: Response) => {

                // login successful if there's a jwt token in the response
                let member = res.json();
                //test
                console.log("hey", member.emailAdress);
                if (member) {
                    // store member details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentMember', JSON.stringify(member));
                    this.value = localStorage.getItem("currentMember");

                    console.log("heycurrentlogin", this.value);
                }

                return member;
            });
    }

    addCountry(countryName: string) {
        const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
        const options = new RequestOptions({ headers: headers1 });

        const object = {
            countryName: countryName,

        };

        const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName=' + countryName;
        const urlR = 'http://localhost:8091/rencontre/Administrator/addRegion';
        const urlD = 'http://localhost:8091/rencontre/Administrator/addDepartment';
        const urlB = 'http://localhost:8091/rencontre/Administrator/addBorough';

        return this.http.post(urlC, object, options)
            .do((res: Response) => console.log(res.json()))
            //.map((res: Response ) => res.json());
            //ajouté a partir dici
            .map((res: Response) => {

                // login successful if there's a jwt token in the response
                let country = res.json();
                //test
                console.log("hey", country.countryName);
                if (country) {
                    // store member details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentCountry', JSON.stringify(country));
                    this.valueC = localStorage.getItem("currentcountry");

                    console.log("heycurrentcountry", this.valueC);
                }

                return country;
            });
    }

    loginAdmin(loginAdmin: string, passwordAdmin: string) {
        const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
        const options = new RequestOptions({ headers: headers1 });

        const object = {
            loginAdmin: loginAdmin,
            passwordAdmin: passwordAdmin,
        };

        const urlC = 'http://localhost:8091/rencontre/Administrator/administratorConnexion?loginAdmin=' + loginAdmin + '&passwordAdmin=' + passwordAdmin;
        return this.http.post(urlC, object, options)
            .do((res: Response) => console.log("welcome admin", res.json()))
            //.map((res: Response ) => res.json());
            //ajouté a partir dici
            .map((res: Response) => {

                // login successful if there's a jwt token in the response
                let administrator = res.json();
                //test
                console.log("hey", administrator.loginAdmin);
                if (administrator) {
                    // store member details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentAdministrator', JSON.stringify(administrator));
                    this.value = localStorage.getItem("currentAdministrator");

                    console.log("hey currentAdministrator", this.value);
                }

                return administrator;
            });
    }


    logout() {
        // remove member from local storage to log user out
        localStorage.removeItem('currentMember');
    }

    logoutAdmin() {
        // remove member from local storage to log user out
        localStorage.removeItem('currentAdministrator');
    }
}
