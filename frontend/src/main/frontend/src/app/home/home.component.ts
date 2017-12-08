import { Component, OnInit } from '@angular/core';
import { Response, HttpModule, Http } from '@angular/http';

import { Member } from '../_models/index';
import { Status } from '../_models/status';
import { MemberService, StatusService } from '../_services/index';


@Component({
    moduleId: module.id,
    templateUrl: 'home.component.html',
    styleUrls: ['./home.css', '../../bootstrap/css/bootstrap.css', '../../font-awesome-4.7.0/css/font-awesome.css']
})

export class HomeComponent implements OnInit {
    currentMember: Member;
    members: Member[] = [];
    currentStatus: Status;
    statuss: Status[] = [];
    public statusName: string = 'connected';
    private results: [any];
    private collectionJson: object;


    constructor(private http: Http, private memberService: MemberService, private statusService: StatusService) {
        this.currentMember = JSON.parse(localStorage.getItem('currentMember'));
        console.log("heooooohomets", this.currentMember.pseudonym);
        this.currentStatus = JSON.parse(localStorage.getItem('currentStatus'));
    }

    //getting meeting type when selected
  public Filter(value: string) {
        console.log('status donne', value);
        this.statusName = value;
        const url = 'http://localhost:8091/rencontre/Member/changeStatus?statusName='+this.statusName;
    
        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
           console.log("collection stautus", this.collectionJson);
        });
        //  this.rest.getAllByDate(this.age).subscribe(meetings => { this.meetings = meetings; });
        //  console.log("meetings", this.meetings);
      }
     
    ngOnInit() {
        //    this.loadAllMembers();
        this.loadAllStatuss();

    }

    private loadAllStatuss() {
        this.statusService.getAll().subscribe(statuss => { this.statuss = statuss; });
        console.log("statuss", this.statuss);
      }

    private deleteMember(id: number) {
        this.memberService.delete(id).subscribe(() => { this.loadAllMembers() });
    }

    private loadAllMembers() {
        this.memberService.getAll().subscribe(members => { this.members = members; });
    }
}