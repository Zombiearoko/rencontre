import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';


import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { AlertService, MemberService, MeetingService } from '../_services/index';
import { LoginFormComponent } from '../login-form/index';
import { Meeting } from '../_models/meeting';

@Component({
  selector: 'app-member-register1',
  templateUrl: './member-register1.component.html',
  styleUrls: ['./member-register1.component.css', '../../bootstrap/css/bootstrap.css'],
  entryComponents: [LoginFormComponent]
})
export class MemberRegister1Component implements OnInit {
  // @Input() item: any;
  loading = false;
  clientForm: FormGroup;
  // public meeting: Meeting;
  public meetings: Meeting[] = [];
  // name: any;
  post: any;
  titleAlert: string = 'You must specify a pseudo thats between 3 and 5 characters';
  private age: Date;
  date: number;
  private results: [any];
  private collectionJson: object;
  submitted = false;
  objDate = Date.now();

  constructor(public rest: RestProvider, private meetingService: MeetingService, public fb: FormBuilder, private http: Http, private router: Router, private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'date': [null, Validators.compose([Validators.required])],
      'meetingNameN':  [null, Validators.compose([Validators.required])]

    });


  }
  //getting country when selected
  public Filter(value: Date) {

    console.log('age donne', value);
    this.age = value;
    const url = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + this.age;

    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();

      console.log(this.collectionJson);
    });
    
  }
  onSubmit(post) {

    this.age = post.age;



  }
  ngOnInit() {
    console.log("date du jour", this.objDate);

  }


}

