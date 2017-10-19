import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Status } from '../_models/status';
 
@Injectable()
export class StatusService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllStatus', this.jwt()).map((response: Response) => response.json());
       
    }
    
    getAllByDate(date:Date) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + date, this.jwt()).map((response: Response) => response.json());
      
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(status: Status) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listTypeMeeting', status, this.jwt()).map((response: Response) => response.json());
    }
 
    update(status: Status) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + status.id, status, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentStatus = JSON.parse(localStorage.getItem('currentStatus'));
        if (currentStatus && currentStatus.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentStatus.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}