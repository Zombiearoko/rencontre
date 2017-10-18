import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';


import { LoginFormComponent } from '../login-form/index';
import { AlertService, MemberService, MeetingService, CountryService, RegionService, DepartmentService, BoroughService, TownService } from '../_services/index';
import { Meeting } from '../_models/meeting';
import { Town } from '../_models/town';
import { Country, Region, Borough, Department} from '../_models/index';

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
  public countries: Country[] = [];
  public regions: Region[] = [];
  public departments: Department[] = [];
  
  public boroughs: Borough[] = [];
  public towns: Town[] = [];
  customerPictureFile: File;
  @ViewChild('customerPicture') customer_picture;
  post: any;
  titleAlert: string = 'You must specify a pseudo thats between 3 and 5 characters';
  private birthDate: Date;
  private meetingName: string;
  public currentMeeting: Meeting;
  public currentCountry: Country;
  public currentRegion: Region;
  public currentDepartment: Department;
  public currentBorough: Borough;
  public currentTown: Town;
  private pseudonym: string;
  private name: string = '';
  private firstName: string = '';
  private lastName: string = '';
  private schoolName: string = '';
  private levelStudy: string = '';
  private profession: string = '';
  private fatherName: string = '';
  private motherName: string = '';
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
  submitted = false;

  constructor(public rest: RestProvider,
    private meetingService: MeetingService,
    private countryService: CountryService,
    private regionService: RegionService,
    private departmentService: DepartmentService,
    private boroughService: BoroughService,
    private townService: TownService,
    public fb: FormBuilder, private http: Http, private router: Router, private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'birthDate': [null, Validators.compose([Validators.required])],
      'meetingName': [null, Validators.compose([Validators.required])],
      'pseudonym': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(5)])],
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

    this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
    this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
    this.currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
    this.currentDepartment = JSON.parse(localStorage.getItem('currentDepartment'));
    this.currentBorough = JSON.parse(localStorage.getItem('currentBorough'));
    this.currentTown = JSON.parse(localStorage.getItem('currentTown'));


  }

  //getting meeting type when selected
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

  public FilterC(value: string) {
    alert(value);
    this.countryName = value;
  }
  public FilterR(value: string) {
    alert(value);
    this.regionName = value;
  }
  public FilterD(value: string) {
    alert(value);
    this.departmentName = value;
  }
  public FilterB(value: string) {
    alert(value);
    this.boroughName = value;
  }
  public FilterT(value: string) {
    alert(value);
    this.townName = value;
  }

  onSubmit(post) {
    this.birthDate = post.birthDate;
    this.meetingName = post.meetingName;
    this.pseudonym = post.pseudonym;
    this.name = post.name;
    this.firstName = post.firstName;
    this.lastName = post.lastName;
    this.schoolName = post.schoolName;
    this.levelStudy = post.levelStudy;
    this.profession = post.profession;
    this.concessionName = post.concessionName;
    this.gender = post.gender;
    this.emailAdress = post.emailAdress;
    this.phoneNumber = post.phoneNumber;
    this.password = post.password;
    this.confirmPassword = post.confirmPassword;
    this.picture = post.picture;
    const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + this.pseudonym + '&birthDate=' + this.birthDate + '&gender=' + this.gender + '&emailAdress=' + this.emailAdress
      + '&phoneNumber=' + this.phoneNumber + '&password=' + this.password + '&confirmPassWord='
      + this.confirmPassword + '&name=' + this.name + '&meetingName=' + this.meetingName
      + '&firstName=' + this.firstName + '&lastName=' + this.lastName + '&schoolName='
      + this.schoolName + '&levelStudy=' + this.levelStudy + '&profession=' + this.profession
      + '&country=' + this.countryName + '&region=' + this.regionName + '&department=' + this.departmentName + '&borough=' + this.boroughName + '&town=' + this.townName + '&concessionName=' + this.concessionName;
    console.log(this.pseudonym);
    console.log('meetingNAme', this.meetingName);
    this.rest.postAccount(this.pseudonym, this.birthDate, this.gender, this.emailAdress, this.phoneNumber, this.password, this.confirmPassword, this.name, this.meetingName, this.firstName, this.lastName,
      this.schoolName, this.levelStudy, this.profession, this.countryName, this.regionName, this.departmentName,
      this.boroughName, this.townName, this.concessionName)
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
this.loadAllCountries();
this.loadAllRegions();
this.loadAllDepartments();
this.loadAllBoroughs();
this.loadAllTowns();

  }
  private loadAllCountries() {
    this.countryService.getAll().subscribe(countries => { this.countries = countries; });
  }
  private loadAllRegions() {
    this.regionService.getAll().subscribe(regions => { this.regions = regions; });
    console.log("regions", this.regions);
  }
  private loadAllDepartments() {
    this.departmentService.getAll().subscribe(departments => { this.departments = departments; });
    console.log("departments", this.departments);
}
  private loadAllBoroughs() {
    this.boroughService.getAll().subscribe(boroughs => { this.boroughs = boroughs; });
}
private loadAllTowns() {
  this.townService.getAll().subscribe(towns => { this.towns = towns; });
  console.log("departments", this.departments);
}

}
