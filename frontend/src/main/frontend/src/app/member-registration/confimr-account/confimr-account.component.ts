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
  pseudo: any;
  constructor( private route: ActivatedRoute) {
    this.pseudo = route.snapshot.params['pseudo'];
   }

  ngOnInit() {
  }

}
