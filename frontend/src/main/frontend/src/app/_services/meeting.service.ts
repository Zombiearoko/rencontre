import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
 
import { Meeting } from '../_models/meeting';
 
@Injectable()
export class MeetingService {
    constructor(private http: Http) { }
 
    getAll() {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listAllTypeMeeting', this.jwt()).map((response: Response) => response.json());
       
    }

    getAllByPeudo(pseudonym:string) {
        return this.http.get('http://localhost:8091/rencontre/Member/returnTypeMeeting?pseudonym=' + pseudonym, this.jwt()).map((response: Response) => response.json());
      
    }
    
    getAllByDate(date:Date) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + date, this.jwt()).map((response: Response) => response.json());
      
    }
 
    getById(id: number) {
        return this.http.get('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    create(meeting: Meeting) {
        return this.http.post('http://localhost:8091/rencontre/Administrator/listTypeMeeting', meeting, this.jwt()).map((response: Response) => response.json());
    }
 
    update(meeting: Meeting) {
        return this.http.put('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + meeting.id, meeting, this.jwt()).map((response: Response) => response.json());
    }
 
    delete(id: number) {
        return this.http.delete('http://localhost:8091/rencontre/Administrator/listTypeMeeting' + id, this.jwt()).map((response: Response) => response.json());
    }
 
    // private helper methods
 
    private jwt() {
        // create authorization header with jwt token
        let currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
        if (currentMeeting && currentMeeting.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + JSON.stringify(+ currentMeeting.token )});
            return new RequestOptions({ headers: headers });
        }
    }
}