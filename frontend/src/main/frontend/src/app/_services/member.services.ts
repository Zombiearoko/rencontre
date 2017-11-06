import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Member } from '../_models/index';
 
@Injectable()
export class MemberService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('localhost:8091/rencontre/Administrator/listAllMember', this.jwt()).map((response: Response) => response.json());
    }
 
    getById(id: number) {
        return this.http.get('localhost:8091/rencontre/Administrator/listAllMember' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(member: Member) {
        return this.http.post('localhost:8091/rencontre/Administrator/listAllMember', member, this.jwt()).map((response: Response) => response.json());
    }
 
    update(member: Member) {
        return this.http.put('localhost:8091/rencontre/Administrator/listAllMember' + member.id, member, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('localhost:8091/rencontre/Administrator/listAllMember' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentMember = JSON.parse(localStorage.getItem('currentMember'));
        if (currentMember && currentMember.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentMember.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}