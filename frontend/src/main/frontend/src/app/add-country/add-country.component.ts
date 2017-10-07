


import { ViewChild, Component,Input,ElementRef, OnInit } from '@angular/core';

import { Response,HttpModule,Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, CountryService } from '../_services/index';
import {Country} from '../_models/country';


@Component({
  selector: 'app-add-country',
  moduleId: module.id,
  templateUrl: './add-country.component.html',
  providers:[AlertService, RestProvider],
  styleUrls: ['./add-country.component.css','../../bootstrap/css/bootstrap.css'],
  
})
export class AddCountryComponent implements OnInit {
  currentCountry: Country;
  countries: Country[] = [];
  
  loading = false;
  clientForm: FormGroup;
  post: any; 
  titleAlert:string = 'You must specify a country';
private countryName: string;
private results: [any];
private collectionJson: object;
submitted = false;

  constructor(private countryService: CountryService, 
    public rest: RestProvider, 
    public fb: FormBuilder, private http: Http, 
    private router: Router, 
    private alertService: AlertService) { 
  
    
    this.clientForm = this.fb.group({
      'countryName': [null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(10)])]
    });
    
    this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
    // console.log("heoooooaddcountryts",this.currentCountry.countryName);
  }
onSubmit(post){

  this.countryName = post.countryName;
  const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName='+this.countryName;
  // console.log(this.countryName);
  this.rest.postCountry(this.countryName)
  .subscribe((data) => {
      // set success message and pass true paramater to persist the message after redirecting to the login page
      this.alertService.success('addsuccessful', true);
      this.router.navigateByUrl('/add-country');
  },
  error => {
      this.alertService.error(error);
      this.loading = false;
        // console.log(this.countryName);
        
        this.submitted = true;
       });
  this.http.get(urlC).subscribe((resp)=>{
    this.results = resp['results'];
    this.collectionJson = resp.json();
  // console.log(this.collectionJson);
});

}
  ngOnInit() {
    this.loadAllCountries();
    
  }

  deleteCountry(id: number) {
    this.countryService.delete(id).subscribe(() => { this.loadAllCountries() });
}

private loadAllCountries() {
    this.countryService.getAll().subscribe(countries => { this.countries = countries; });
}

}
