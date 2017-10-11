import { ViewChild, Component,Input,ElementRef, OnInit } from '@angular/core';

import { Response,HttpModule,Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, BoroughService, TownService} from '../_services/index';
import {Borough} from '../_models/borough';
import {Town} from '../_models/town';
// import {Country, Region} from '../_models/index';



@Component({
  selector: 'app-add-town',
  templateUrl: './add-town.component.html',
  styleUrls: ['./add-town.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddTownComponent implements OnInit {
  @Input('group')
  public clientForm:FormGroup;
  public boroughName: string;
  public townName: string;
  public collectionJson= Object;
  
  submitted = false; 
  public currentBorough: Borough;
  public currentTown: Town;
  public borough:Borough;
  public boroughs: Borough[] = [];
  loading = false;
  post: any; 
  titleAlert:string = 'You must specify a town';
private results: [any];


  constructor(private boroughService: BoroughService, 
    public fb: FormBuilder, private http: Http, 
    private router: Router, 
    private alertService: AlertService) { 
  
    
    this.clientForm = this.fb.group({
      'boroughNameN': [null, Validators.compose([Validators.required])],
      'townName': [null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(10)])]
      
    });
    
    this.currentBorough = JSON.parse(localStorage.getItem('currentBorough'));
    this.currentTown = JSON.parse(localStorage.getItem('currentTown'));
  }
  
//getting country when selected
public Filter(value: Borough)
  {
    alert(value);
     this.borough=value;
  }
  
onSubmit(post){

  this.townName = post.townName;
  const urlR = 'http://localhost:8091/rencontre/Administrator/addTown?townName='+this.townName+'&countryName='+ this.borough;
  
  this.http.get(urlR).subscribe((resp)=>{
    this.results = resp['results'];
    this.collectionJson = resp.json();
  console.log("pour la collection town",this.collectionJson);
});

}
  ngOnInit() {
    this.loadAllBoroughs();
    // this.getAll;
    
  }


private loadAllBoroughs() {
    this.boroughService.getAll().subscribe(boroughs => { this.boroughs = boroughs; });
}

}

 