import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Country } from '../_models/country';
 
@Injectable()
export class CountryService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllCountry', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllCountry' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(country: Country) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listAllCountry', country, this.jwt()).map((response: Response) => response.json());
    }
 
    update(country: Country) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listAllCountry' + country.id, country, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listAllCountry' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
        if (currentCountry && currentCountry.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentCountry.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}