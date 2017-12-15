
import { ViewChild, Component, Input, ElementRef, OnInit } from '@angular/core';
import { Response, HttpModule, Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

import { Router } from '@angular/router';

import { AlertService, StatusService } from '../_services/index';
import { Status } from '../_models/status';
import { Administrator} from '../_models/administrator';

@Component({
  selector: 'app-add-status',
  templateUrl: './add-status.component.html',
  styleUrls: ['./add-status.component.css', '../../bootstrap/css/bootstrap.css']
})
export class AddStatusComponent implements OnInit {
  @Input('group')
  public clientForm: FormGroup;
  public statusName: string;
  public collectionJson = Object;
  submitted = false;
  public currentStatus: Status;
  currentAdministrator:Administrator;
  public status: Status;
  public statuss: Status[] = [];
  loading = false;
  post: any;
  titleAlert: string = 'You must specify a status';
  private results: [any];


  constructor(private statusService: StatusService,
    public fb: FormBuilder, private http: Http,
    private router: Router,
    private alertService: AlertService) {


    this.clientForm = this.fb.group({
      'statusName': [null, Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(55)])]

    });
    this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));
    console.log("heooooostatus",this.currentAdministrator.loginAdmin);
    this.currentStatus = JSON.parse(localStorage.getItem('currentStatus'));
  }

  onSubmit(post) {
    this.loadAllStatuss();
    var j = 0;
    if (this.statuss == null) {
      // this.statusName = post.statusName;
      const urlD = 'http://localhost:8091/rencontre/Administrator/addStatus?statusName=' + post.statusName;

      this.http.get(urlD).subscribe((resp) => {
        this.results = resp['results'];
        this.collectionJson = resp.json();
        console.log("pour la collection status", this.collectionJson);
        this.loadAllStatuss();
      });
    }
    else {
      for (var i = 0; i < this.statuss.length; i++) {
        if (this.statuss[i].statusName == post.statusName)
          j++;
      }

      if (j == 0) {
        this.statusName = post.statusName;
        const urlD = 'http://localhost:8091/rencontre/Administrator/addStatus?statusName=' + this.statusName;

        this.http.get(urlD).subscribe((resp) => {
          this.results = resp['results'];
          this.collectionJson = resp.json();
          console.log("pour la collection status", this.collectionJson);

          this.loadAllStatuss();
        });


      }


      else {
        alert("désolé! Ce status existe déja ");

      }
    }




  }
  ngOnInit() {
    this.loadAllStatuss();

  }

  private loadAllStatuss() {
    this.statusService.getAll().subscribe(statuss => { this.statuss = statuss; });
    console.log("statuss", this.statuss);
  }

}

