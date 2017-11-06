import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Concession } from '../_models/concession';
 
@Injectable()
export class ConcessionService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllConcession', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllConcession' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(concession: Concession) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listAllConcession', concession, this.jwt()).map((response: Response) => response.json());
    }
 
    update(concession: Concession) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listAllConcession' + concession.id, concession, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listAllConcession' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentConcession = JSON.parse(localStorage.getItem('currentConcession'));
        if (currentConcession && currentConcession.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentConcession.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}