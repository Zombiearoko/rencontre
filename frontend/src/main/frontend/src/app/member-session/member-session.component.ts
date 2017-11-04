import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, CountryService, } from '../_services/index';
import { Country } from '../_models/country';


@Component({
  selector: 'app-member-session',
  moduleId: module.id,
  templateUrl: './member-session.component.html',
  styleUrls: ['./member-session.component.css', '../../bootstrap/css/bootstrap.css']
})
export class MemberSessionComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public countryName: string;
  public surname: string;
  public collectionJson = {
    countryName: 'test',
  }
  submitted = false;
  public currentCountry: Country;
  public countries: Country[] = [];

  loading = false;

  post: any;
  titleAlert: string = 'You must specify a country';
  private results: [any];


  constructor(private countryService: CountryService,
    public rest: RestProvider,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'countryName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(20)])]
    });

    this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
    // console.log("heoooooaddcountryts",this.currentCountry.countryName);
  }
  //getting countryName for unicity
  // public FilterC(value: any) {

  //   console.log('pays entré:', value);


  // }

  onSubmit(post) {
    this.loadAllCountries();
    var j = 0;

    for (var i = 0; i < this.countries.length; i++) {
      if (this.countries[i].countryName == post.countryName)
        j++;
    }

    if (j == 0) {
      this.countryName = post.countryName;

      const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName=' + this.countryName;

      this.http.get(urlC).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection test", this.collectionJson);
        this.loadAllCountries();
      });
     
    }


    else {
      alert("Ce pays existe déja Entrez un pays non existant");

    }



    // console.log("look here please",this.countryName);
    // this.rest.postCountry(this.countryName)
    // .subscribe((data) => {
    //   console.log("pour errorooooo",data);
    //     // set success message and pass true paramater to persist the message after redirecting to the login page
    //     this.alertService.success('addsuccessful', true);
    //     this.router.navigateByUrl('/member-session');
    //     console.log("pour error",data);
    // },
    // error => {
    //     this.alertService.error(error);
    //     this.loading = false;
    //       console.log("pour error",this.countryName);

    //       this.submitted = true;
    //      });

    }

  ngOnInit() {
    this.loadAllCountries();
    // this.getAll;

  }

  deleteCountry(id: number) {
    this.countryService.delete(id).subscribe(() => { this.loadAllCountries() });
  }

  // private getAll() {
  //   return this.http.get('http://localhost:8091/rencontre/Administrator/listAllCountry')
  //    .do((res: Response) => console.log("les pays",res.json()))
  //     .map((res: Response) => {

  // // login successful if there's a jwt token in the response
  // this.countries= res.json();
  // //test
  // console.log("hey les pays",this.countries);
  // return this.countries;
  // });
  // }
  private loadAllCountries() {
    this.countryService.getAll().subscribe(countries => { this.countries = countries; });
  }

}

