import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Administrator } from '../_models/administrator';
 
@Injectable()
export class AdministratorService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllAdministrator', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllAdministrato' + id, this.jwt()).map((response: Response) => response.json());
    }

    getByName(loginAdmin: string) {
        return this.http.get(' http://localhost:8091/rencontre/Member/searchAdministratorWithloginAdmin?loginAdmin=' + loginAdmin, this.jwt()).map((response: Response) => response.json());
    }
 
    create(administrator: Administrator) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listAllAdministrator', administrator, this.jwt()).map((response: Response) => response.json());
    }
 
    update(administrator: Administrator) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listAdministrator' + administrator.id, administrator, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listAllAdministrator' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));
        if (currentAdministrator && currentAdministrator.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentAdministrator.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}