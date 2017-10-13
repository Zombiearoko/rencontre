import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Department } from '../_models/department';
 
@Injectable()
export class DepartmentService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listDepartment', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listDepartment' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(department: Department) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listDepartment', department, this.jwt()).map((response: Response) => response.json());
    }
 
    update(department: Department) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listDepartment' + department.id, department, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listDepartment' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentDepartment = JSON.parse(localStorage.getItem('currentDepartment'));
        if (currentDepartment && currentDepartment.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentDepartment.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}   