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
  styleUrls: ['./member-registration.component.css', '../../bootstrap/css/bootstrap.css', '../../font-awesome-4.7.0/css/font-awesome.css'],
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

  private birthDate: string;
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
  private choix: string = '';
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
      'choix': [null, Validators.compose([Validators.required])],
      'pseudonym': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(6)])],
      'pseudonym1': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(6)])],
      'birthDate': [null, Validators.compose([Validators.required])],
      'birthDate1': [null, Validators.compose([Validators.required])],
      'meetingName': [null, Validators.compose([Validators.required])],
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

  public FilterChoix(value: string) {
    this.choix = value;
    this.model.pseudonym1 = value;

    this.model.meetingName = '';
    this.model.birthDate = '';
    this.model.pseudonym1 = 'null';
    this.model.pseudonym = '';
    this.model.gender = '';
    this.model.phoneNumber = '';
    this.model.emailAdress = '';
    this.model.password = '';
    this.model.confirmPassword = '';
    this.model.name = '';
    this.model.firstName = '';
    this.model.lastName = '';
    this.model.schoolName = '';
    this.model.levelStudy = '';
    this.model.profession = '';
    this.model.fatherName = '';
    this.model.motherName = '';
    this.model.fatherName = '';
    this.model.regionName = '';
    this.model.departmentName = '';
    this.model.boroughName = '';
    this.model.townName = '';
    this.model.concessionName = '';

  }

  //looking if pseudo is unique
  public FilterS(value: string) {
    // var ageDifMs = Date.now() - this.age.getTime();  
    // var ageDate = new Date(ageDifMs); // miliseconds from epoch
    // this.date =Math.abs(ageDate.getUTCFullYear() - 1970);
    // alert(date);
this.model.pseudonym=value;

    console.log('pseudo donne', this.model.pseudonym);
    if (this.choix == 'oui') {
      console.log('tous les membres');
      const url = 'http://localhost:8091/rencontre/Administrator/listAllMember';
      this.http.get(url).subscribe((resp) => {
        this.results = resp['results'];
        this.members = resp.json();
        console.log(resp.status);

        console.log('le membre avec le pseudo', this.model.pseudonym,this.members);

        var j = 0;

        for (var i = 0; i < this.members.length; i++) {
          if (this.members[i].pseudonym == this.model.pseudonym) {
            this.model.birthDate = this.members[i].birthDate;
            console.log('pour voir les info du membre deja existant',this.model.birthDate, this.members[i]);
            console.log('age de select donne', this.model.birthDate);
            const url2 = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?birthDate=' + this.model.birthDate;
      
            this.http.get(url2).subscribe((resp) => {
              this.results = resp['results'];
              this.collectionJson = resp.json();
      
              console.log(this.collectionJson);
            });
            i = this.members.length;

          }

        }

      });
     
    }
    this.model.gender = '';
    console.log(this.model.gender);
    this.model.phoneNumber = '';
    this.model.emailAdress = '';
    this.model.password = '';
    this.model.confirmPassword = '';
  }


  //getting meeting type when selected age
  public Filter(value: string) {
    this.model.name = '';
    this.model.firstName = '';
    this.model.lastName = '';
    this.model.schoolName = '';
    this.model.levelStudy = '';
    this.model.profession = '';
    this.model.fatherName = '';
    this.model.motherName = '';
    this.model.fatherName = '';
    this.model.regionName = '';
    this.model.departmentName = '';
    this.model.boroughName = '';
    this.model.townName = '';
    this.model.concessionName = '';

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

    const url = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?birthDate=' + this.model.birthDate;

    this.http.get(url).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();
      console.log('hehe les meeting');
      console.log(this.collectionJson);
    });
    //  this.rest.getAllByDate(this.age).subscribe(meetings => { this.meetings = meetings; });
    //  console.log("meetings", this.meetings);
  }
  FilterM(value: string) {
    if (this.choix == 'oui') {
      console.log(this.choix);
      console.log(value);
      const url = 'http://localhost:8091/rencontre/Administrator/listAllMember';
      this.http.get(url).subscribe((resp) => {
        this.results = resp['results'];
        this.members = resp.json();
        console.log(resp.status);
        if (resp.status == 204) {
          // if (this.choix=='non'){
          console.log('aucun membre existant');
          this.existe = 0;
        }
        else {
          console.log('le membres', this.members);

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
              this.model.pseudonym = '';
              this.model.meetingName = '';



            }
            else if (!(this.member.datingInformation == null) && (this.model.meetingName == 'Amoureuse')) {

              console.log('ce pseudo est deja utilisé pour le type de rencontre Amoureuse');
              // alert('ce pseudo est deja utilisé');
              this.model.pseudonym = '';

            }
            else if (!(this.member.academicDatingInformation == null) && (this.model.meetingName == 'Academique')) {

              console.log('ce pseudo est deja utilisé pour le type de rencontre Académique');
              // alert('ce pseudo est deja utilisé');
              this.model.pseudonym = '';

            }
            else if (!(this.member.professionalMeetingInformation == null) && (this.model.meetingName == 'Professionnelle')) {

              console.log('ce pseudo est deja utilisé pour le type de rencontre Professionnelle');
              // alert('ce pseudo est deja utilisé');
              this.model.pseudonym = '';

            }

          }

        }
      });


    }
  }

  public FilterC(value: string) {
    // alert(value);
    this.model.fatherName = value;
    this.loadAllRegionsByCountry(this.model.fatherName);
    // this.loadAllRegions();
  }
  public FilterR(value: string) {
    // alert(value);
    this.model.regionName = value;
    this.loadAllDepartmentsByRegion(this.model.regionName);
  }
  public FilterD(value: string) {
    // alert(value);
    this.model.departmentName = value;
    this.loadAllBoroughsByDepartment(this.model.departmentName);
  }
  public FilterB(value: string) {
    // console.log(value);
    this.model.boroughName = value;
    // this.loadAllTownsByBorough(this.model.boroughName);
  }
  // public FilterT(value: string) {
  //   alert(value);
  //   this.model.townName = value;
  //  this.loadAllConcessionByTown pour la concession une fois que les membres auront fournir les différents concessions
  // }

  onSubmit(post) {

    // this.model.birthDate = post.birthDate;
    // this.model.meetingName = post.meetingName;
    // this.model.name = post.name;
    // this.model.firstName = post.firstName;
    // this.model.lastName = post.lastName;
    // this.model.schoolName = post.schoolName;
    // this.model.levelStudy = post.levelStudy;
    // this.model.profession = post.profession;
    // this.model.fatherName = post.fatherName;
    // this.model.motherName = post.motherName;
    // this.model.fatherName = post.countryName;
    // this.model.regionName = post.regionNAme;
    // this.model.departmentName = post.departementName;
    // this.model.boroughName = post.boroughName;
    // this.model.townName = post.townName;
    // this.model.concessionName = post.concessionName;
    // this.model.gender = post.gender;
    // this.model.emailAdress = post.emailAdress;
    // this.model.phoneNumber = post.phoneNumber;
    // this.model.password = post.password;
    // this.model.confirmPassword = post.confirmPassword;
    this.picture = post.picture;

    if (this.model.meetingName == 'Amicale') {
      if (this.choix == 'non') {
        console.log("helooooo", this.model.pseudonym, "new member with  type Amicale");
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.model.birthDate + '&gender=' + this.model.gender + '&emailAdress=' + this.model.emailAdress
          + '&phoneNumber=' + this.model.phoneNumber + '&password=' + this.model.password + '&confirmPassWord=' + this.model.confirmPassword + '&meetingName=' + this.model.meetingName
          + '&name=' + this.model.name;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page

          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Amicale, please check your email to confirm your account before login', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.model.emailAdress]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }

      else {
        console.log("helloooooo", this.model.pseudonym, 'membre deja existant qui sinscrit pour le type Amicale');
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.model.meetingName + '&name=' + this.model.name;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page

          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Amicale, please check your email to confirm your account before login', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.model.emailAdress]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }

    }
    // pour inscrire pour Académique
    else if (this.model.meetingName == 'Academique') {
      if (this.choix == 'non') {
        console.log("helooooo", this.model.pseudonym, 'new member with  type Académique');

        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.model.birthDate + '&gender=' + this.model.gender + '&emailAdress=' + this.model.emailAdress
          + '&phoneNumber=' + this.model.phoneNumber + '&password=' + this.model.password
          + '&confirmPassWord=' + this.model.confirmPassword + '&meetingName=' + this.model.meetingName + '&firstName=' + this.model.firstName + '&lastName=' + this.model.lastName + '&schoolName=' + this.model.schoolName + '&levelStudy=' + this.model.levelStudy;
        console.log("academique", this.model.firstName);

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);

          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Academique, please check your email to confirm your account before login ', true);
            // alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }

      else {
        console.log("hi", this.model.pseudonym, 'pour un membre deja existant qui sousscrit pour le type Académique');
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.model.meetingName + '&firstName=' + this.model.firstName + '&lastName=' + this.model.lastName + '&schoolName=' + this.model.schoolName + '&levelStudy=' + this.model.levelStudy;
        console.log("academique", this.model.firstName);

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for Academique, please check your email to confirm your account before login ', true);
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

    else if (this.model.meetingName == 'Professionnelle') {
      if (this.choix == 'non') {

        console.log("helooooo", this.model.pseudonym, 'new member with  type Professionnelle');

        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.model.birthDate + '&gender=' + this.model.gender + '&emailAdress=' + this.model.emailAdress
          + '&phoneNumber=' + this.model.phoneNumber + '&password=' + this.model.password + '&confirmPassWord=' + this.model.confirmPassword + '&meetingName=' + this.model.meetingName
          + '&firstName=' + this.model.firstName + '&lastName=' + this.model.lastName
          + '&levelStudy=' + this.model.levelStudy + '&profession=' + this.model.profession;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }

          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for professional, please check your email to confirm your account before login', true);
            alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );
      }

      else {
        console.log("hi ", this.model.pseudonym, 'pour un membre deja existant qui souscrit pour le type Professionnel');
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.model.meetingName
          + '&firstName=' + this.model.firstName + '&lastName=' + this.model.lastName
          + '&levelStudy=' + this.model.levelStudy + '&profession=' + this.model.profession;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
            this.alertService.success('Registration successful for professional, please check your email to confirm your account before login', true);
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
    else if (this.model.meetingName == 'Amoureuse') {
      if (this.choix == 'non') {
        console.log("helooooo", this.model.pseudonym, 'new member with  type Amoureuse');
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&birthDate=' + this.model.birthDate + '&gender=' + this.model.gender + '&emailAdress=' + this.model.emailAdress
          + '&phoneNumber=' + this.model.phoneNumber + '&password=' + this.model.password + '&confirmPassWord=' + this.model.confirmPassword + '&meetingName=' + this.model.meetingName
          + '&fatherName=' + this.model.fatherName + '&motherName=' + this.model.motherName
          + '&countryName=' + this.model.fatherName + '&regionName=' + this.model.regionName
          + '&departmentName=' + this.model.departmentName + '&boroughName=' + this.model.boroughName + '&townName=' + this.model.townName + '&concessionName=' + this.model.concessionName;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
              this.alertService.success('Registration successful for Amoureuse, please check your email to confirm your account before login', true);              
            alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }
      else {
        console.log("helooooo", this.model.pseudonym, 'member existant with  type Amoureuse');
        const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.model.pseudonym + '&meetingName=' + this.model.meetingName
          + '&fatherName=' + this.model.fatherName + '&motherName=' + this.model.motherName
          + '&countryName=' + this.model.fatherName + '&regionName=' + this.model.regionName
          + '&departmentName=' + this.model.departmentName + '&boroughName=' + this.model.boroughName + '&townName=' + this.model.townName + '&concessionName=' + this.model.concessionName;

        this.http.get(url).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log(this.collectionJson);
          //  set success message and pass true paramater to persist the message after redirecting to the login page
          console.log(typeof (resp));
          console.log(resp.status);
          // this.collectionJson.push(resp);
          if ((resp.status == 500) || (resp.status == 404)) {
            this.alertService.error('Registration failed', true);
            this.router.navigate(['/member-registration']);
          }
          else {
            //  if(resp.status==1){
              this.alertService.success('Registration successful for Amoureuse, please check your email to confirm your account before login', true);
              alert("Ravi de vous compter parmi nous consulter votre boite e-mail pour confirmer votre inscription");
            // this.router.navigate(['/confimr-account/' + this.pseudonym]);
            this.router.navigate(['/rencotre']);

            //  }
          }

        }
        );

      }

    }
  }
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
