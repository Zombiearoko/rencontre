import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';



import { AlertService, MemberService, MeetingService, CountryService, RegionService, DepartmentService, BoroughService, TownService } from '../_services/index';
import { Meeting } from '../_models/meeting';
import { Town } from '../_models/town';

import { ProfessionalMeetingInformation } from '../_models/professionalMeetingInformation';
import { AcademicDatingInformation } from '../_models/academicDatingInformation';
import { DatingInformation } from '../_models/datingInformation';
import { FriendlyDatingInformation } from '../_models/friendlyDatingInformation';

import { Country, Region, Borough, Department, Member } from '../_models/index';
import { ConfimrAccountComponent } from './confimr-account/confimr-account.component';


@Component({
  selector: 'app-member-registration',
  templateUrl: './member-registration.component.html',
  styleUrls: ['./member-registration.component.css', '../../bootstrap/css/bootstrap.css'],
  entryComponents: [ConfimrAccountComponent]
})
export class MemberRegistrationComponent implements OnInit {
  model: any = {};
  loading = false;
  clientForm: FormGroup;
  public meetings: Meeting[] = [];
  public countries: Country[] = [];
  public regions: Region[] = [];
  public departments: Department[] = [];

  public boroughs: Borough[] = [];
  // public towns: Town[] = [];
  customerPictureFile: File;
  @ViewChild('customerPicture') customer_picture;
  post: any;
  titleAlert: string = 'You must specify an unused pseudo for this meeting type! characters between 3 and 6 ';
  titleAlertP: string = 'password have to be same with preview one';

  private birthDate: Date;
  private meetingName: string;

  public currentMeeting: Meeting;
  public currentCountry: Country;
  public currentRegion: Region;
  public currentDepartment: Department;
  public currentBorough: Borough;
  // public currentTown: Town;
  private pseudonym: string;
  private name: string = '';
  private friendlyDatingInformatio: FriendlyDatingInformation = null;
  private firstName: string = '';
  private lastName: string = '';
  private schoolName: string = '';
  private levelStudy: string = '';
  private academicDatingInformation: AcademicDatingInformation = null;
  private profession: string = '';
  private professionalMeetingInformation: ProfessionalMeetingInformation = null;
  private fatherName: string = '';
  private motherName: string = '';
  private datingInformation: DatingInformation = null;

  private countryName: string = '';
  private regionName: string = '';
  private departmentName: string = '';
  private boroughName: string = '';
  private townName: string = '';
  private concessionName: string = '';
  private password: string;
  private confirmPassword: string;
  private emailAdress: string;
  private gender: string;
  private phoneNumber: string;
  private picture: string;
  private results: [any];
  private collectionJson: object;
  private currentMember: Member;
  private member: Member;
  private members: Member[] = [];
  submitted = false;
  private existe: number;


  constructor(public rest: RestProvider,
    private meetingService: MeetingService,
    private memberService: MemberService,
    private countryService: CountryService,
    private regionService: RegionService,
    private departmentService: DepartmentService,
    private boroughService: BoroughService,
    private townService: TownService,
    public fb: FormBuilder, private http: Http, private router: Router, private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'birthDate': [null, Validators.compose([Validators.required])],
      'meetingName': [null, Validators.compose([Validators.required])],
      'pseudonym': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(6)])],

      'pseudonym1': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(6)])],
      'name': '',
      'firstName': '',
      'lastName': '',
      'schoolName': '',
      'levelStudy': '',
      'profession': '',
      'fatherName': '',
      'motherName': '',
      'countryName': '',
      'regionName': '',
      'departmentName': '',
      'boroughName': '',
      'townName': '',
      'concessionName': '',
      'emailAdress': [null, Validators.compose([Validators.required, Validators.email])],
      'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      'confirmPassword': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      'phoneNumber': [null, Validators.compose([Validators.required])],
      'gender': [null, Validators.compose([Validators.required])],


      // 'customerPicture': [null, Validators.required]
      'customerPicture': ''
    });
    // this.currentMember = JSON.parse(localStorage.getItem('currentMember'));
    this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
    this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
    this.currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
    this.currentDepartment = JSON.parse(localStorage.getItem('currentDepartment'));
    this.currentBorough = JSON.parse(localStorage.getItem('currentBorough'));

    // this.currentTown = JSON.parse(localStorage.getItem('currentTown'));


  }
  FilterSe(value: Date) {
  }

  //getting meeting type when selected age
  public Filter(value: Date) {
    this.model.gender = '';
    console.log(this.model.gender);
    this.model.phoneNumber = '';
    this.model.emailAdress = '';
    this.model.password = '';
    this.model.confirmPassword = '';

    // var ageDifMs = Date.now() - this.age.getTime();  
    // var ageDate = new Date(ageDifMs); // miliseconds from epoch
    // this.date =Math.abs(ageDate.getUTCFullYear() - 1970);
    // alert(date);

    console.log('age donne', value);
    this.birthDate = value;
    const url = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?birthDate=' + this.birthDate;

    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();

      console.log(this.collectionJson);
    });
    //  this.rest.getAllByDate(this.age).subscribe(meetings => { this.meetings = meetings; });
    //  console.log("meetings", this.meetings);
  }
  //looking if pseudo is unique
  public FilterS(value: string) {



    // var ageDifMs = Date.now() - this.age.getTime();  
    // var ageDate = new Date(ageDifMs); // miliseconds from epoch
    // this.date =Math.abs(ageDate.getUTCFullYear() - 1970);
    // alert(date);

    console.log('pseudo donne', value);
    this.model.pseudonym = value;
    this.model.gender = '';
    console.log(this.model.gender);
    this.model.phoneNumber = '';
    this.model.emailAdress = '';
    this.model.password = '';
    this.model.confirmPassword = '';
    // this.loadAllMeetings(this.pseudonym);
    // const url = 'http://localhost:8091/rencontre/Member/returnMember?pseudonym=' + this.pseudonym;
    const url = 'http://localhost:8091/rencontre/Administrator/listAllMember';
    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.members = resp.json();
      console.log('le membre avec le pseudo', this.model.pseudonym, 'est:', this.members, this.model.meetingName);

      var j = 0;

      for (var i = 0; i < this.members.length; i++) {
        if (this.members[i].pseudonym == this.model.pseudonym) {
          this.member = this.members[i];
          console.log('pour voir les info du membre deja existant', this.member);
          i = this.members.length;
          j++;


        }
        j++;
      }
      this.existe = j;
      console.log('j', j, 'membelengh', this.members.length);
      if (!(j == this.members.length)) {
        // console.log(this.me mber.datingInformation,this.member.professionalMeetingInformation,this.member.professionalMeetingInformation,'this.member.academicDatingInformation',this.member.academicDatingInformation);
        if (!(this.member.friendlyDatingInformatio == null) && (this.model.meetingName == 'Amicale')) {

          console.log('ce pseudo est deja utilisé pour le type de rencontre Amicale');
          // alert('ce pseudo est deja utilisé');
          this.model.pseudonym = null;


        }
        else if (!(this.member.datingInformation == null) && (this.model.meetingName == 'Amoureuse')) {

          console.log('ce pseudo est deja utilisé pour le type de rencontre Amoureuse');
          // alert('ce pseudo est deja utilisé');
          this.model.pseudonym = null;

        }
        else if (!(this.member.academicDatingInformation == null) && (this.model.meetingName == 'Academique')) {

          console.log('ce pseudo est deja utilisé pour le type de rencontre Académique');
          // alert('ce pseudo est deja utilisé');
          this.model.pseudonym = null;

        }
        else if (!(this.member.professionalMeetingInformation == null) && (this.model.meetingName == 'Professionnelle')) {

          console.log('ce pseudo est deja utilisé pour le type de rencontre Professionnelle');
          // alert('ce pseudo est deja utilisé');
          this.model.pseudonym = null;

        }

      }
      else {

        console.log('membre non existant');

      }
    });


    //   console.log('****member****',this.member);
    //   var j = 0;

    //       for (var i = 0; i < this.members.length; i++) {
    //         if (this.members[i].pseudonym == this.pseudonym)
    //           j++;
    //       }

    //       if (j != 0) {
    //         this.loadAllMeetings(this.pseudonym);

    //               console.log('liste des type meeting de',this.pseudonym,'donne:',this.meetings);
    //               var k = 0;
    //               console.log('le type de rencontre selectionné pour:',this.pseudonym,'=',this.model.meetingName);

    //               for (var i = 0; i < this.meetings.length; i++) {
    //                 if (this.meetings[i].meetingName == this.model.meetingName)
    //                   console.log('dans la boucle meetingName',this.meetings[i].meetingName);
    //                   k++;
    //               }
    //               console.log('valeur de k',k,this.meetings.length);
    //               if (k != 0)
    //                 {
    //               console.log('ce pseudo est deja utilisé pour ce type de rencontre');
    //               // alert('ce pseudo est deja utilisé');
    //           this.model.pseudonym = null;
    //         }


    //       }


    // this.rest.getAllByDate(this.age).subscribe(meetings => { this.meetings = meetings; });
    //  console.log("meetings", this.meetings);
  }

  public FilterC(value: string) {
    // alert(value);
    this.countryName = value;
    this.loadAllRegionsByCountry(this.countryName);
    // this.loadAllRegions();
  }
  public FilterR(value: string) {
    // alert(value);
    this.regionName = value;
    this.loadAllDepartmentsByRegion(this.regionName);
  }
  public FilterD(value: string) {
    // alert(value);
    this.departmentName = value;
    this.loadAllBoroughsByDepartment(this.departmentName);
  }
  public FilterB(value: string) {
    // console.log(value);
    this.boroughName = value;
    // this.loadAllTownsByBorough(this.boroughName);
  }
  // public FilterT(value: string) {
  //   alert(value);
  //   this.townName = value;
  //  this.loadAllConcessionByTown pour la concession une fois que les membres auront fournir les différents concessions
  // }

  onSubmit(post) {

    this.birthDate = post.birthDate;
    this.meetingName = post.meetingName;

    this.model.gender = post.gender;
    this.model.phoneNumber = post.phoneNumber;
    this.model.emailAdress = post.emailAdress;
    this.model.password = post.password;
    this.model.confirmPassword = post.password;
    this.name = post.name;
    this.firstName = post.firstName;
    this.lastName = post.lastName;
    this.schoolName = post.schoolName;
    this.levelStudy = post.levelStudy;
    this.profession = post.profession;
    this.fatherName = post.fatherName;
    this.motherName = post.motherName;
    this.countryName = post.countryName;
    this.regionName = post.regionNAme;
    this.departmentName = post.departementName;
    this.boroughName = post.boroughName;
    this.townName = post.townName;
    this.concessionName = post.concessionName;
    // this.gender = post.gender;
    // this.emailAdress = post.emailAdress;
    // this.phoneNumber = post.phoneNumber;
    // this.password = post.password;
    // this.confirmPassword = post.confirmPassword;
    this.picture = post.picture;

    if (!((this.name == null) || (this.name == ""))) {
      if (!(this.existe == this.members.length)) {
        console.log("helloooooo", this.name, 'membre deja existant qui sinscrit pour le type Amicale', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.meetingName + '&name=' + this.name;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page

          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Amicale, please check your account to confirm your account before login', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.emailAdress]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }
      else {
        console.log("helooooo", this.name, 'new member with  type Amicale', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.model.gender + '&emailAdress=' + this.model.emailAdress
          + '&phoneNumber=' + this.model.phoneNumber + '&password=' + this.model.password + '&confirmPassWord=' + this.model.confirmPassword + '&meetingName=' + this.meetingName
          + '&name=' + this.name;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page

          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Amicale, please check your account to confirm your account before login', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.emailAdress]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }
    }
    // pour inscrire pour Académique
    else if ((this.firstName != null) || (this.firstName != "") || (this.lastName != null) || (this.lastName != "") || (this.schoolName != null) || (this.schoolName != "") || (this.levelStudy != null) || (this.levelStudy != "")) {
      if (!(this.existe == this.members.length)) {
        console.log("hi your firstname is", this.firstName, 'pour un membre deja existant qui sousscrit pour le type Académique', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.meetingName + '&firstName=' + this.firstName + '&lastName=' + this.lastName + '&schoolName=' + this.schoolName + '&levelStudy=' + this.levelStudy;
        console.log("academique", this.firstName);

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          //   this.alertService.success('Registration successful', true);
          //   this.router.navigate(['/member-registration']);
          // },
          //   error => {
          //     this.alertService.error(error);
          //     this.loading = false;
          //     console.log(this.pseudonym);
          //     // console.log(this.gender);
          //     this.submitted = true;
          //   });
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Academique, please check your account to confirm your account before login ', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }
      else {
        console.log("helooooo", this.firstName, 'new member with  type Académique', this.existe, '==', this.members.length);

        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
          + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord=' + this.confirmPassword + '&meetingName=' + this.meetingName + '&firstName=' + this.firstName + '&lastName=' + this.lastName + '&schoolName=' + this.schoolName + '&levelStudy=' + this.levelStudy;
        console.log("academique", this.firstName);

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          //   this.alertService.success('Registration successful', true);
          //   this.router.navigate(['/member-registration']);
          // },
          //   error => {
          //     this.alertService.error(error);
          //     this.loading = false;
          //     console.log(this.pseudonym);
          //     // console.log(this.gender);
          //     this.submitted = true;
          //   });
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Academique, please check your account to confirm your account before login ', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }
    }
    //  pour inscrire un member professionnel

    else if (!((this.firstName == null) || (this.firstName == "") || (this.lastName == null) || (this.lastName == "") || (this.levelStudy == null) || (this.levelStudy == "") || (this.profession == null) || (this.profession == ""))) {
      if (!(this.existe == this.members.length)) {
        console.log("hi your firstname is", this.firstName, 'pour un membre deja existant qui souscrit pour le type Professionnel', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.meetingName
          + '&firstName=' + this.firstName + '&lastName=' + this.lastName
          + '&levelStudy=' + this.levelStudy + '&profession=' + this.profession;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for professional, please check your account to confirm your account before login', true);
            alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }
      else {
        console.log("helooooo", this.firstName, 'new member with  type Professionnelle', this.existe, '==', this.members.length);

        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
          + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord='+ this.confirmPassword + '&meetingName=' + this.meetingName
          + '&firstName=' + this.firstName + '&lastName=' + this.lastName
          + '&levelStudy=' + this.levelStudy + '&profession=' + this.profession;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if (resp.status == 0) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for professional, please check your account to confirm your account before login', true);
            alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }
    }

    // pour inscrire un membre Amoureuse
    else if (!((this.fatherName == null) || (this.fatherName == "") || (this.motherName == null) || (this.motherName == "") || (this.countryName == null) || (this.countryName == "") || (this.regionName == null) || (this.regionName == ""))) {
      if (!(this.existe == this.members.length)) {
        console.log("hi your ", this.model.pseudonym, 'pour un membre deja existant qui souscrit pour le type Amoureuse', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.meetingName
        + '&fatherName=' + this.fatherName + '&motherName=' + this.motherName
        + '&countryName=' + this.countryName + '&regionName=' + this.regionName
        + '&departmentName=' + this.departmentName + '&boroughName=' + this.boroughName + '&townName=' + this.townName + '&concessionName=' + this.concessionName;

      this.http.get(url).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log(this.collectionJson);
        //  set success message and pass true paramater to persist the message after redirecting to the login page
        console.log(typeof (resp));
        console.log(resp.status);
        // this.collectionJson.push(resp);
        if (resp.status == 0) {
          this.alertService.error('Registration failed', true);
          this.router.navigate(['/member-registration']);
        }
        else {
          //  if(resp.status==1){
          this.alertService.success('Registration successful', true);
          alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
          // this.router.navigate(['/confimr-account/' + this.pseudonym]);
          this.router.navigate(['/rencotre']);

          //  }
        }

      }
      );

      }
      else {
        console.log("helooooo", this.firstName, 'new member with  type Professionnelle', this.existe, '==', this.members.length);
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
        + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord=' + this.confirmPassword + '&meetingName=' + this.meetingName
        + '&fatherName=' + this.fatherName + '&motherName=' + this.motherName
        + '&countryName=' + this.countryName + '&regionName=' + this.regionName
        + '&departmentName=' + this.departmentName + '&boroughName=' + this.boroughName + '&townName=' + this.townName + '&concessionName=' + this.concessionName;

      this.http.get(url).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log(this.collectionJson);
        //  set success message and pass true paramater to persist the message after redirecting to the login page
        console.log(typeof (resp));
        console.log(resp.status);
        // this.collectionJson.push(resp);
        if (resp.status == 0) {
          this.alertService.error('Registration failed', true);
          this.router.navigate(['/member-registration']);
        }
        else {
          //  if(resp.status==1){
          this.alertService.success('Registration successful', true);
          alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
          // this.router.navigate(['/confimr-account/' + this.pseudonym]);
          this.router.navigate(['/rencotre']);

          //  }
        }

      }
      );

            }
    }
    else {
      const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
        + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord='
        + this.confirmPassword + '&meetingName=' + this.meetingName
        + '&fatherName=' + this.fatherName + '&motherName=' + this.motherName
        + '&countryName=' + this.countryName + '&regionName=' + this.regionName + '&departmentName=' + this.departmentName + '&boroughName=' + this.boroughName + '&townName=' + this.townName + '&concessionName=' + this.concessionName;

      this.http.get(url).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log(this.collectionJson);
        //  set success message and pass true paramater to persist the message after redirecting to the login page
        console.log(typeof (resp));
        console.log(resp.status);
        // this.collectionJson.push(resp);
        if (resp.status == 0) {
          this.alertService.error('Registration failed', true);
          this.router.navigate(['/member-registration']);
        }
        else {
          //  if(resp.status==1){
          this.alertService.success('Registration successful for Amoureuse, please check your account to confirm your account before login', true);
          alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
          // this.router.navigate(['/confimr-account/' + this.pseudonym]);
          this.router.navigate(['/rencotre']);

          //  }
        }

      }
      );

      console.log("******hummmm*******");
    }
    // const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
    //   + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord='
    //   + this.confirmPassword + '&meetingName=' + this.meetingName
    //   + '&firstName=' + this.firstName + '&lastName=' + this.lastName + '&schoolName='
    //   + this.schoolName + '&levelStudy=' + this.levelStudy + '&profession=' + this.profession
    //   + '&countryName=' + this.countryName + '&regionName=' + this.regionName + '&departmentName=' + this.departmentName + '&boroughName=' + this.boroughName + '&townName=' + this.townName + '&concessionName=' + this.concessionName;

    console.log('NAme', this.name);
    // this.rest.postAccount(this.pseudonym, this.birthDate, this.gender, this.emailAdress, this.phoneNumber, this.password, this.confirmPassword, this.name, this.meetingName, this.firstName, this.lastName,
    //   this.schoolName, this.levelStudy, this.profession, this.countryName, this.regionName, this.departmentName,
    //   this.boroughName, this.townName, this.concessionName)
    //   .subscribe((data) => {
    //     // set success message and pass true paramater to persist the message after redirecting to the login page
    //     this.alertService.success('Registration successful', true);
    //     this.router.navigate(['/login-form']);
    //   },
    //   error => {
    //     this.alertService.error(error);
    //     this.loading = false;
    //     console.log(this.pseudonym);
    //     // console.log(this.gender);
    //     this.submitted = true;
    //   });


  }

  // confirmPass(post) {
  //   this.password = post.password;
  //   this.confirmPassword = post.confirmPassword;
  //   console.log(this.password != this.confirmPassword);
  //   if (this.password != this.confirmPassword) {
  //     this.alertService.error('passeword non identique', true);
  //     // const errorPassword = document.getElementById('errorPassword');
  //     // errorPassword.innerHTML = 'le mot de passe non valide';
  //   }

  // }
  ngOnInit() {
    this.loadAllCountries();
    this.loadAllMembers();
    // this.loadAllRegions() ;
    console.log(this.countries);
    this.model.gender = '';
    console.log(this.model.gender);
    this.model.gender = '';
    console.log(this.model.gender);
    this.model.phoneNumber = '';
    this.model.emailAdress = '';
    this.model.password = '';
    this.model.confirmPassword = '';

  }
  // recupere les members
  private loadAllMembers() {
    this.memberService.getAll().subscribe(members => { this.members = members; });
    console.log("members", this.members);
  }
  // recuperation pays 
  private loadAllCountries() {
    this.countryService.getAll().subscribe(countries => { this.countries = countries; });
    console.log("countries1", this.countries);
  }

  // recuperation region du pays ou tt
  private loadAllRegions() {
    this.regionService.getAll().subscribe(regions => { this.regions = regions; });
    console.log("regions1", this.regions);
  }
  private loadAllRegionsByCountry(countryName) {
    this.rest.getAllRegionByCountry(countryName).subscribe(regions => { this.regions = regions; });
    console.log("regions2", this.regions);
  }

  // recuperation departement dune region ou tt
  private loadAllDepartments() {
    this.departmentService.getAll().subscribe(departments => { this.departments = departments; });
    console.log("departments", this.departments);
  }
  private loadAllDepartmentsByRegion(regionName) {
    this.rest.getAllDepartmentByRegion(regionName).subscribe(departments => { this.departments = departments; });
    console.log("departments", this.departments);
  }

  // recuperation arrondissement dun departement ou tt
  private loadAllBoroughs() {
    this.boroughService.getAll().subscribe(boroughs => { this.boroughs = boroughs; });
  }

  private loadAllBoroughsByDepartment(departmentName) {
    this.rest.getAllBoroughByDepartment(departmentName).subscribe(boroughs => { this.boroughs = boroughs; });
  }

  // recuperation des ville dun arrondissement ou tt
  // private loadAllTowns() {
  //   this.townService.getAll().subscribe(towns => { this.towns = towns; });
  //   console.log("departments", this.departments);
  // }
  // private loadAllTownsByBorough(boroughName) {
  //   this.rest.getAllTownByBorough(boroughName).subscribe(towns => { this.towns = towns; });
  //   console.log("departments", this.departments);
  // }
  private loadAllMeetings(pseudonym: string) {
    // this.meetingService.getAll().subscribe(meetings => { this.meetings = meetings; });
    //  this.rest.getAll().subscribe(meetings => { this.meetings = meetings; });
    this.meetingService.getAllByPeudo(pseudonym).subscribe(meetings => { this.meetings = meetings; });
  }

}
