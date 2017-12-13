import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';


import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, AuthenticationService } from '../_services/index';
import { AdministratorService } from '../_services/administrator.services';
import { Administrator } from '../_models/administrator';


@Component({
  selector: 'app-admin-registration',
  templateUrl: './admin-registration.component.html',
  styleUrls: ['./admin-registration.component.css', '../../bootstrap/css/bootstrap.css', '../../font-awesome-4.7.0/css/font-awesome.css']
})
export class AdminRegistrationComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public loginAdmin: string;
  public passwordAdmin: string;
  model: any = {};
  loading = false;
  public administrators: Administrator[] = [];

  @ViewChild('customerPicture') customer_picture;
  post: any;
  titleAlert: string = 'You must specify an unused login ! characters between 3 and 6 ';

  public currentAdministrator: Administrator;
  // public currentTown: Town;

  private picture: string;
  private results: [any];
  private collectionJson: object;
  submitted = false;
  constructor(private administratorService: AdministratorService,
    private authentificationService: AuthenticationService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'loginAdmin': [null, Validators.compose([Validators.required])],
      'passwordAdmin': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(6)])]

    });

    this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));
  }

  onSubmit(post) {
    // this.loadAllAdministrators();
    var j = 0;
    // if (this.administrators == null) {
    const urlD = 'http://localhost:8091/rencontre/Administrator/addAdmin?login=' + post.loginAdmin + '&password=' + post.passwordAdmin;

    this.http.get(urlD).subscribe((resp) => {
      this.results = resp['results'];
      this.collectionJson = resp.json();
      console.log("pour la collection admin", this.collectionJson);
      // pour actualiser la liste des admin
      // this.loadAllAdministrators();
    });
    // }
    // else {
    // for (var i = 0; i < this.administrators.length; i++) {
    //   if (this.administrators[i].loginAdmin == post.loginAdmin)
    //     j++;
    // }

    // if (j == 0) {
    //   const urlD = 'http://localhost:8091/rencontre/Administrator/addAdmin?login=' + post.loginAdmin + '&password=' + post.passwordAdmin;

    //         this.http.get(urlD).subscribe((resp) => {
    //           this.results = resp['results'];
    //           this.collectionJson = resp.json();
    //           console.log("pour la collection admin", this.collectionJson);
    //           // pour actualiser la liste des admin
    //           // this.loadAllAdministrators();
    //         });


    // }

    // else {
    //   alert("désolé! Ce Departement existe déja ");

    // }
    // }

  }
  ngOnInit() {
    // this.loadAllAdministrators();

  }

  // private loadAllAdministrators() {
  //   this.administratorService.getAll().subscribe(administrators => { this.administrators = administrators; });
  //   console.log("administrators", this.administrators);
  // }


}


