import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';
import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, MeetingService } from '../_services/index';
import { Meeting } from '../_models/meeting';


@Component({
  selector: 'app-add-meeting',
  templateUrl: './add-meeting.component.html',
  styleUrls: ['./add-meeting.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddMeetingComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public meetingName: string;
  public collectionJson = Object;
  submitted = false;
  public currentMeeting: Meeting;

  public meeting: Meeting;
  public meetings: Meeting[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a meeting';
  private results: [any];


  constructor(private meetingService: MeetingService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'meetingName': [null, Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(55)])]

    });

    this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
  }

  onSubmit(post) {
    this.loadAllMeetings();
    var j = 0;
  
    for (var i = 0; i < this.meetings.length; i++) {
      if (this.meetings[i].meetingName == post.meetingName)
        j++;
    }
  
    if (j == 0) {
      this.meetingName = post.meetingName;
      const urlD = 'http://localhost:8091/rencontre/Administrator/typeMeeting?meetingName=' + this.meetingName;
  
      this.http.get(urlD).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection typeMeeting", this.collectionJson);
     
        this.loadAllMeetings();
      });
  
      
    }
  
  
    else {
      alert("désolé! Cet type de rencontre existe déja ");
  
    }

   

  }
  ngOnInit() {
    this.loadAllMeetings();

  }

  private loadAllMeetings() {
    this.meetingService.getAll().subscribe(meetings => { this.meetings = meetings; });
    console.log("meetings", this.meetings);
  }

}

