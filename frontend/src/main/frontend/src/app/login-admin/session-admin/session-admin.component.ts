import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Administrator } from '../../_models/administrator';


@Component({
  selector: 'app-session-admin',
  moduleId: module.id,
  templateUrl: './session-admin.component.html',
  styleUrls: ['./session-admin.component.css'],

})
export class SessionAdminComponent implements OnInit {
  currentAdministrator: Administrator;
  administrators: Administrator[] = [];

  constructor(private router: Router, ) {

  }

  ngOnInit() {
    this.currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));

    if (this.currentAdministrator == null) {
      this.router.navigate(['/login-admin']);
    }
    else {
      console.log("heooooocountrys", this.currentAdministrator.loginAdmin);
    }
  }

}
