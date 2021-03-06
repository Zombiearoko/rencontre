import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Region } from '../_models/region';
 
@Injectable()
export class RegionService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllRegion', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllRegion' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(region: Region) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listAllRegion', region, this.jwt()).map((response: Response) => response.json());
    }
 
    update(region: Region) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listAllRegion' + region.id, region, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listAllRegion' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
        if (currentRegion && currentRegion.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentRegion.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}