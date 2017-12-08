
// import { ViewChild, Component,Input,ElementRef, OnInit } from '@angular/core';

// import { Response,HttpModule,Http } from '@angular/http';
// import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
// //import { RegisterPage } from '../register/register';
// import { RestProvider } from '../../providers/rest/rest';

// import { Router } from '@angular/router';

// import { AlertService, RegionService, CountryService } from '../_services/index';
// import {Region} from '../_models/region';

// import {Country} from '../_models/country';


// @Component({
//   selector: 'app-add-region',
//   moduleId: module.id,
//   templateUrl: './add-region.component.html',
//   styleUrls: ['./add-region.component.css', '../../bootstrap/css/bootstrap.css']
// })
// export class AddRegionComponent implements OnInit {
//   @Input('group')
//   public clientForm:FormGroup;
//   public regionName: string;
//   public countryName: string;
//   public collectionCountry={
//     countryName:'cepays',
//   }
//   public collectionJson={
//     regionName:'test',
//   }
//   submitted = false; 
//   public currentRegion: Region;
//   public currentCountry: Country; 
//   public regions: Region[] = [];
//   public selectedCountry: Country;
//  public country : Country;
//   public countries: Country[] = [];

//   loading = false;

//   post: any; 
//   titleAlert:string = 'You must specify a region';
// private results: [any];


//   constructor(
//     private regionService: RegionService, 
//     private countryService: CountryService, 
//     public rest: RestProvider, 
//     public fb: FormBuilder, private http: Http, 
//     private router: Router, 
//     private alertService: AlertService) { 


//     this.clientForm = this.fb.group({
//       'regionName': [null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(10)])],
//       'category': [null, Validators.compose([Validators.required])]

//     });

//     this.currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
//     // console.log("heoooooaddregionts",this.currentregion.regionName);
//     this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
//     // console.log("heoooooaddr country",this.currentCountry.countryName);
//   }

// onSubmit(post){

//   this.regionName = post.regionName;

//   const urlR = 'http://localhost:8091/rencontre/Administrator/addregion?regionName='+this.regionName;

//   this.http.get(urlR).subscribe((resp)=>{
//     this.results = resp['results'];
//     this.collectionJson = resp.json();
//   console.log("pour la collection test",this.collectionJson);
// });

// }
//   ngOnInit() {
//     this.loadAllcountries;

//     this.getAllCountry();


//   }

//   deleteRegion(id: number) {
//     this.regionService.delete(id).subscribe(() => { this.loadAllregions() });
// }

// private getAll() {
//   return this.http.get('http://localhost:8091/rencontre/Administrator/listRegion')
//    .do((res: Response) => console.log("les pays",res.json()))
//     .map((res: Response) => {

// // login successful if there's a jwt token in the response
// this.regions= res.json();
// //test
// console.log("hey les regions",this.regions);
// return this.regions;
// });
// }
// private loadAllregions() {
//     this.regionService.getAll().subscribe(regions => { this.regions = regions; });
// }

// //for country

// deleteCountry(id: number) {
//   this.countryService.delete(id).subscribe(() => { this.loadAllcountries() });
// }

// private getAllCountry() {
// return this.http.get('http://localhost:8091/rencontre/Administrator/listAllCountry')
//  .do((res: Response) => console.log("les pays",res.json()))
//   .map((res: Response) => {

//                               // login successful if there's a jwt token in the response
//                               this.countries= res.json();
//                               //test
//                               console.log("hey les payss",this.countries);
//                               return this.countries;
//                               });
//                             }
// private loadAllcountries() {
//   this.countryService.getAll().subscribe(countries => { this.countries = countries; });
// }


// }



import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, CountryService, RegionService } from '../_services/index';
import { Country } from '../_models/country';
import { Region } from '../_models/region';
// import {Country, Region} from '../_models/index';



@Component({
  selector: 'app-add-region',
  moduleId: module.id,
  templateUrl: './add-region.component.html',
  styleUrls: ['./add-region.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddRegionComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public countryName: string;
  public regionName: string;
  public collectionJson = Object;
  // {
  //   countryName:'test',
  // }
  submitted = false;
  public currentCountry: Country;
  public currentRegion: Region;
  public country: Country;
  public countries: Country[] = [];
  public regions: Region[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a region';
  private results: [any];


  constructor(private countryService: CountryService,
    private regionService: RegionService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'countryNameN': [null, Validators.compose([Validators.required])],
      'regionName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(10)])]

    });

    this.currentCountry = JSON.parse(localStorage.getItem('currentCountry'));
    this.currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
    // console.log("heoooooaddcountryts",this.currentCountry.countryName);
  }

  //getting country when selected
  public Filter(value: Country) {
    console.log("****pays***:", value);
    this.country = value;
  }

  onSubmit(post) {
    this.loadAllRegions();
    var j = 0;
    if (this.regions == null) {
      this.regionName = post.regionName;
      const urlR = 'http://localhost:8091/rencontre/Administrator/addRegion?regionName=' + this.regionName + '&countryName=' + this.country;

      this.http.get(urlR).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection region", this.collectionJson);
        this.loadAllRegions();
      });

    }
    else {

      for (var i = 0; i < this.regions.length; i++) {
        if (this.regions[i].regionName == post.regionName)
          j++;
      }

      if (j == 0) {
        this.regionName = post.regionName;
        const urlR = 'http://localhost:8091/rencontre/Administrator/addRegion?regionName=' + this.regionName + '&countryName=' + this.country;

        this.http.get(urlR).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log("pour la collection region", this.collectionJson);
          this.loadAllRegions();
        });


      }


      else {
        alert("désolé! Cette Region existe déja ");

      }


    }
  }
  ngOnInit() {
    this.loadAllCountries();
    // this.loadAllRegions()
    // this.getAll;

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
  private loadAllRegions() {
    this.regionService.getAll().subscribe(regions => { this.regions = regions; });
  }
  deleteCountry(id: number) {
    this.countryService.delete(id).subscribe(() => { this.loadAllCountries() });
  }

}

