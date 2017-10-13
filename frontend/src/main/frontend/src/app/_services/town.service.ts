import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Town } from '../_models/town';
 
@Injectable()
export class TownService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTown', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTown' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(town: Town) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listTown', town, this.jwt()).map((response: Response) => response.json());
    }
 
    update(town: Town) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listTown' + town.id, town, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listTown' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentTown = JSON.parse(localStorage.getItem('currentTown'));
        if (currentTown && currentTown.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentTown.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}