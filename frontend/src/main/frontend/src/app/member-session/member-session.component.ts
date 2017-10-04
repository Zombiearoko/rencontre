import { Component, OnInit, Input } from '@angular/core';


import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { RestProvider } from '../../providers/rest/rest';
import { HttpClientModule} from '@angular/common/http';
import { Http } from '@angular/http';

import { Member } from '../_models/index';
import { MemberService } from '../_services/index';

@Component({
  selector: 'app-member-session',
  templateUrl: './member-session.component.html',
  styleUrls: ['./member-session.component.css', '../../bootstrap/css/bootstrap.css']
})
export class MemberSessionComponent implements OnInit {
 public currentMember: Member;
  members: Member[] = [];

  @Input() item: any;
  private results: [any];
   collectionJson: Object;
collection: any[] = [];
model: any = {};

  constructor(private memberService: MemberService, public rest: RestProvider, private http: Http ) {
     
  }

 
  
  ngOnInit() {
    this.currentMember = JSON.parse(localStorage.getItem('currentUser'));
    let value: string = localStorage.getItem("currentMember");
    console.log("de session",value);
    console.log("current member submitted ooooo", this.model.pseudonym);
    
  }

}
