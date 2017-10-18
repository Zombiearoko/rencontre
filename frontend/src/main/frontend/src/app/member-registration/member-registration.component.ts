import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';


import { LoginFormComponent } from '../login-form/index';
import { AlertService, MemberService, MeetingService } from '../_services/index';
import { Meeting } from '../_models/meeting';

@Component({
  selector: 'app-member-registration',
  templateUrl: './member-registration.component.html',
  styleUrls: ['./member-registration.component.css', '../../bootstrap/css/bootstrap.css'],
  entryComponents: [LoginFormComponent]
})
export class MemberRegistrationComponent implements OnInit {
  model: any = {};
  loading = false;
  clientForm: FormGroup;
  public meetings: Meeting[] = [];
  customerPictureFile: File;
  @ViewChild('customerPicture') customer_picture;
  post: any;
  titleAlert: string = 'You must specify a pseudo thats between 3 and 5 characters';
  private birthDate: Date;
  private meetingName: string;
  public currentMeeting: Meeting;
  private pseudonym: string;
  private name: string = '';
  private firstName: string  = '';
  private lastName: string  = '';
  private schoolName: string  = '';
  private levelStudy: string  = '';
  private password: string;
  private confirmPassword: string;
  private emailAdress: string;
  private gender: string;
  private phoneNumber: string;
  private picture: string;
  private results: [any];
  private collectionJson: object;
  submitted = false;

  constructor(public rest: RestProvider, private meetingService: MeetingService, public fb: FormBuilder, private http: Http, private router: Router, private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'birthDate': [null, Validators.compose([Validators.required])],
      'meetingName': [null, Validators.compose([Validators.required])],
      'pseudonym': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(5)])],
      'name': '',
      'firstName': '',
      'lastName': '',
      'schoolName':'',
      'levelStudy': '',



      'emailAdress': [null, Validators.compose([Validators.required, Validators.email])],
      'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      'confirmPassword': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      'phoneNumber': [null, Validators.compose([Validators.required])],
      'gender': [null, Validators.compose([Validators.required])],


      // 'customerPicture': [null, Validators.required]
      'customerPicture': ''
    });

    this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));


  }

  //getting country when selected
  public Filter(value: Date) {


    // var ageDifMs = Date.now() - this.age.getTime();  
    // var ageDate = new Date(ageDifMs); // miliseconds from epoch
    // this.date =Math.abs(ageDate.getUTCFullYear() - 1970);
    // alert(date);

    console.log('age donne', value);
    this.birthDate = value;
    const url = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + this.birthDate;

    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();

      console.log(this.collectionJson);
    });
    //  this.rest.getAllByDate(this.age).subscribe(meetings => { this.meetings = meetings; });
    //  console.log("meetings", this.meetings);
  }

  // public FilterM(value: Meeting)

  // {
  //   console.log('meeting choisi est bien', value);
  //   this.meetingName=value;
  // }

  onSubmit(post) {
    this.birthDate = post.birthDate;
    this.meetingName = post.meetingName;
    this.pseudonym = post.pseudonym;
    this.name = post.name;
    this.firstName = post.firstName;
    this.lastName = post.lastName;
    this.schoolName = post.schoolName;
    this.levelStudy = post.levelStudy;
    this.gender = post.gender;
    this.emailAdress = post.emailAdress;
    this.phoneNumber = post.phoneNumber;
    this.password = post.password;
    this.confirmPassword = post.confirmPassword;
    this.picture = post.picture;
    const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord=' + this.confirmPassword + '&name=' + this.name + '&meetingName=' + this.meetingName+ '&firstName=' + this.firstName+ '&lastName=' + this.lastName+ '&schoolName=' + this.schoolName+ '&levelStudy=' + this.levelStudy;
    console.log(this.pseudonym);
    console.log('meetingNAme', this.meetingName);
    this.rest.postAccount(this.pseudonym, this.birthDate, this.gender, this.emailAdress, this.phoneNumber, this.password, this.confirmPassword, this.name, this.meetingName, this.firstName, this.lastName, this.schoolName, this.levelStudy)
      .subscribe((data) => {
        // set success message and pass true paramater to persist the message after redirecting to the login page
        this.alertService.success('Registration successful', true);
        this.router.navigate(['/login-form']);
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
        console.log(this.pseudonym);
        console.log(this.gender);
        this.submitted = true;
      });
    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();
      console.log(this.collectionJson);
    });

  }
  ngOnInit() {


  }

}
