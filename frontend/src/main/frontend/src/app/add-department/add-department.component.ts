import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, RegionService } from '../_services/index';
import { Region } from '../_models/region';
import { Department } from '../_models/department';


@Component({
  selector: 'app-add-department',
  templateUrl: './add-department.component.html',
  styleUrls: ['./add-department.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddDepartmentComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public regionName: string;
  public departmentName: string;
  public collectionJson = Object;
  // {
  //   countryName:'test',
  // }
  submitted = false;
  public currentRegion: Region;
  public currentDepartment: Department;
  public region: Region;
  public regions: Region[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a country';
  private results: [any];


  constructor(private regionService: RegionService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'regionNameN': [null, Validators.compose([Validators.required])],
      'departmentName': [null, Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(10)])]

    });

    this.currentRegion = JSON.parse(localStorage.getItem('currentRegion'));
    this.currentDepartment = JSON.parse(localStorage.getItem('currentDepartment'));
    // console.log("heoooooaddcountryts",this.currentCountry.countryName);
  }

  //getting region when selected
  public Filter(value: Region) {
    alert(value);
    this.region = value;
  }

  onSubmit(post) {

    this.departmentName = post.departmentName;
    const urlD = 'http://localhost:8091/rencontre/Administrator/addDepartment?departmentName=' + this.departmentName + '&regionName=' + this.region;

    this.http.get(urlD).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();
      console.log("pour la collection department", this.collectionJson);
    });

  }
  ngOnInit() {
    this.loadAllRegions();

  }

  private loadAllRegions() {
    this.regionService.getAll().subscribe(regions => { this.regions = regions; });
    console.log("regions", this.regions);
  }

}

