import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Borough } from '../_models/borough';
 
@Injectable()
export class BoroughService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listBorough', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listBorough' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(borough: Borough) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listBorough', borough, this.jwt()).map((response: Response) => response.json());
    }
 
    update(borough: Borough) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listBorough' + borough.id, borough, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listBorough' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentBorough = JSON.parse(localStorage.getItem('currentBorough'));
        if (currentBorough && currentBorough.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentBorough.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}   