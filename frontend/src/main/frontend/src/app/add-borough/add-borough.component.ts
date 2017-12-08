import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';

import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, DepartmentService, BoroughService } from '../_services/index';
import { Department } from '../_models/department';
import { Borough } from '../_models/borough';



@Component({
  selector: 'app-add-borough',
  templateUrl: './add-borough.component.html',
  styleUrls: ['./add-borough.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddBoroughComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public departmentName: string;
  public boroughName: string;
  public collectionJson = Object;
  // {
  //   countryName:'test',
  // }
  submitted = false;
  public currentDepartment: Department;
  public currentBorough: Borough;
  public department: Department;
  public departments: Department[] = [];
  public boroughs: Borough[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a borough';
  private results: [any];


  constructor(private departmentService: DepartmentService,
    private boroughService: BoroughService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'departmentNameN': [null, Validators.compose([Validators.required])],
      'boroughName': [null, Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(20)])]

    });

    this.currentDepartment = JSON.parse(localStorage.getItem('currentDepartment'));
    this.currentBorough = JSON.parse(localStorage.getItem('currentBorough'));
  }

  //getting departement when selected
  public Filter(value: Department) {
    console.log(value);
    this.department = value;
  }

  onSubmit(post) {
    this.loadAllBoroughs();
    var j = 0;
    if (this.departments == null) {
      this.boroughName = post.boroughName;
      const urlB = 'http://localhost:8091/rencontre/Administrator/addBorough?boroughName=' + this.boroughName + '&departmentName=' + this.department;

      this.http.get(urlB).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection borough", this.collectionJson);
        this.loadAllBoroughs();
      });
    }
    else {
      for (var i = 0; i < this.boroughs.length; i++) {
        if (this.boroughs[i].boroughName == post.boroughName)
          j++;
      }

      if (j == 0) {
        this.boroughName = post.boroughName;
        const urlB = 'http://localhost:8091/rencontre/Administrator/addBorough?boroughName=' + this.boroughName + '&departmentName=' + this.department;

        this.http.get(urlB).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log("pour la collection borough", this.collectionJson);
          this.loadAllBoroughs();
        });


      }


      else {
        alert("désolé! Cet Arrondissemnt existe déja ");

      }
    }

  }
  ngOnInit() {
    this.loadAllDepartments();
    // this.getAll;

  }


  private loadAllDepartments() {
    this.departmentService.getAll().subscribe(departments => { this.departments = departments; });
    console.log("departments", this.departments);
  }
  private loadAllBoroughs() {
    this.boroughService.getAll().subscribe(boroughs => { this.boroughs = boroughs; });
    console.log("boroughs", this.boroughs);
  }

}

