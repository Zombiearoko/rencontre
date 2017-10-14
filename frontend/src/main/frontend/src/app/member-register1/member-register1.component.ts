import { ViewChild, Component,Input,ElementRef, OnInit } from '@angular/core';


import { Response,HttpModule,Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { AlertService, MemberService, MeetingService } from '../_services/index';
import {LoginFormComponent} from '../login-form/index';
import {Meeting} from '../_models/meeting';

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
  titleAlert:string = 'You must specify a pseudo thats between 3 and 5 characters';
  private age:Date;
private results: [any];
private collectionJson: object;
submitted = false;

  constructor(private rest: RestProvider,private meetingService: MeetingService, public fb: FormBuilder, private http: Http, private router: Router, private alertService: AlertService) { 
  
    
    this.clientForm = this.fb.group({
      'age': [null, Validators.compose([Validators.required])]
    });
    
   
  }
  //getting country when selected
public Filter(value: any)
{
  alert(value);
   this.age=value;
   
   this.meetingService.getAll().subscribe(meetings => { this.meetings = meetings; });
   console.log("meetings", this.meetings);
}
onSubmit(post){

  this.age = post.age;
  


}
  ngOnInit() {
    
    
  }
  

}

