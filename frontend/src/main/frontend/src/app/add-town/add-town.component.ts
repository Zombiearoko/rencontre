import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, BoroughService, TownService } from '../_services/index';
import { Borough } from '../_models/borough';
import { Town } from '../_models/town';
import { Administrator } from '../_models/administrator';



@Component({
  selector: 'app-add-town',
  templateUrl: './add-town.component.html',
  styleUrls: ['./add-town.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddTownComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public boroughName: string;
  public townName: string;
  public collectionJson = Object;

  submitted = false;
  public currentBorough: Borough;
  public currentTown: Town;
  currentAdministrator: Administrator;
  public borough: Borough;
  public boroughs: Borough[] = [];
  public towns: Town[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a town';
  private results: [any];


  constructor(private boroughService: BoroughService,
    private townService: TownService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'boroughNameN': [null, Validators.compose([Validators.required])],
      'townName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(20)])]

    });

    this.currentBorough = JSON.parse(localStorage.getItem('currentBorough'));
    this.currentTown = JSON.parse(localStorage.getItem('currentTown'));
  }

  //getting country when selected
  public Filter(value: Borough) {
    console.log("borough vaue", value);
    this.borough = value;
  }

  public FilterT(value: string) {
    console.log("borough vaue", value);
    this.townName = value;
  }
  onSubmit(post) {
    this.loadAllTowns();
    var j = 0;
    if (this.towns == null) {
      this.townName = post.townName;
      const urlT = 'http://localhost:8091/rencontre/Administrator/addTown?townName=' + this.townName + '&countryName=' + this.borough;

      this.http.get(urlT).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection town", this.collectionJson);
        this.loadAllTowns();
      });
    }
    else {
      for (var i = 0; i < this.towns.length; i++) {
        if (this.towns[i].townName == post.townName)
          j++;
      }

      if (j == 0) {

        const urlT = 'http://localhost:8091/rencontre/Administrator/addTown?townName=' + post.townName + '&countryName=' + this.borough;

        this.http.get(urlT).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log("pour la collection town", this.collectionJson);
          this.loadAllTowns();
        });
      }



      else {
        alert("désolé! Cet Arrondissemnt existe déja ");

      }
    }
  }






  ngOnInit() {
    this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));

    if (this.currentAdministrator == null) {
      this.router.navigate(['/login-admin']);
    }
    else {
      console.log("heoooootowns", this.currentAdministrator.loginAdmin);
    }
    this.loadAllBoroughs();
    // this.getAll;

  }


  private loadAllBoroughs() {
    this.boroughService.getAll().subscribe(boroughs => { this.boroughs = boroughs; });
  }
  private loadAllTowns() {
    this.townService.getAll().subscribe(towns => { this.towns = towns; });
  }

}

