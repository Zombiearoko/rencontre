
import { Injectable } from '@angular/core';
import { Http, HttpModule, RequestOptions, RequestMethod, RequestOptionsArgs, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
//pour test
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

import { Member } from '../_models/index';
import { Administrator } from '../_models/administrator';

@Injectable()
export class AuthenticationService {
    public value: string;
    public valueC: string;
    private results: [any];
    private collectionJson: object;
    currentMember: Member;
    currentAdministrator: Administrator;

    constructor(private http: Http) {
        this.currentMember = JSON.parse(localStorage.getItem('currentMember'));
        this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));

    }

    login(pseudonym: string, password: string, meetingName: string) {
        const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
        const options = new RequestOptions({ headers: headers1 });

        const object = {
            pseudonym: pseudonym,
            password: password,
            meetingName: meetingName,
        };

        const url = 'http://localhost:8091/rencontre/Member/connexion?pseudonym=' + pseudonym + '&password=' + password + '&meetingName=' + meetingName;
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
        const urlout = 'http://localhost:8091/rencontre/Member/logout';

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
        const url = 'http://localhost:8091/rencontre/Member/logout';
        this.http.get(url).subscribe((resp) => {
            this.results = resp['results'];
            this.collectionJson = resp.json();
            console.log('members log', this.collectionJson);
            console.log("avant le remove current Member", this.currentMember);
            // alert("bye mm");
            localStorage.removeItem('currentMember');
            this.currentMember = JSON.parse(localStorage.getItem('currentMember'));
            console.log("après le remove current Member", this.currentMember);


        }
        );
    }
    logoutAdmin() {
        const url = 'http://localhost:8091/rencontre/Administrator/logoutAdministrator';
        this.http.get(url).subscribe((resp) => {
            this.results = resp['results'];
            this.collectionJson = resp.json();
            console.log('members log', this.collectionJson);
            console.log("avant le remove currentAdministrator", this.currentAdministrator);
            // alert("bye mm");
            localStorage.removeItem('currentAdministrator');
            this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));
            console.log("après le remove currentAdministrator", this.currentAdministrator);

            localStorage.removeItem('currentAdministrator');
        });

    }
}
