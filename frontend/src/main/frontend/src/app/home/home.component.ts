import { Component, OnInit } from '@angular/core';

import { Member } from '../_models/index';
import { MemberService } from '../_services/index';

@Component({
   moduleId: module.id,
   templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
   currentMember: Member;
   members: Member[] = [];

   constructor(private memberService: MemberService) {
       this.currentMember = JSON.parse(localStorage.getItem('currentMember'));
       console.log("heooooohomets",this.currentMember.pseudonym);
   }

   ngOnInit() {
       this.loadAllMembers();
   }

   deleteMember(id: number) {
       this.memberService.delete(id).subscribe(() => { this.loadAllMembers() });
   }

   private loadAllMembers() {
       this.memberService.getAll().subscribe(members => { this.members = members; });
   }
}