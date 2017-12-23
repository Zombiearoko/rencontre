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
  styleUrls: ['./member-session.component.css', '../../bootstrap/css/bootstrap.css', '../../font-awesome-4.7.0/css/font-awesome.css']
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
    if (this.countries == null) {
      const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName=' + post.countryName;

      this.http.get(urlC).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection test", this.collectionJson);
        this.loadAllCountries();
      });
    }
    else {
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

    }

    
  }

  ngOnInit() {
    this.loadAllCountries();
    // this.getAll;

  }

  deleteCountry(id: number) {
    this.countryService.delete(id).subscribe(() => { this.loadAllCountries() });
  }
  private loadAllCountries() {
    this.countryService.getAll().subscribe(countries => { this.countries = countries; });
  }

}

