import { Component, OnInit, Input } from '@angular/core';
import { RouterModule,Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confimr-account',
  templateUrl: './confimr-account.component.html',
  inputs: ['item'],
  styleUrls: ['./confimr-account.component.css', '../../../bootstrap/css/bootstrap.css']
})
export class ConfimrAccountComponent implements OnInit {
  @Input() item: any;
  private results: [any];
  emailAdress: any;
  constructor( private route: ActivatedRoute) {
    this.emailAdress = route.snapshot.params['emailAdress'];
   }

  ngOnInit() {
  }

}
