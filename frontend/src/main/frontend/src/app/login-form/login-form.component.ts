

// import { HttpModule } from '@angular/http';
// import { Component, OnInit, Input } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
// import { RestProvider } from '../../providers/rest/rest';
// import { HttpClientModule} from '@angular/common/http';
// import { Http } from '@angular/http';

// import {MemberSessionComponent } from '../member-session/member-session.component';

// //ajouter pour gerer session
// import { Router, ActivatedRoute } from '@angular/router';
// import { AlertService } from '../_services/index';


// @Component({
//     selector: 'app-login-form',
//     moduleId: module.id, //ajouter pour gerer session
//     templateUrl: 'login-form.component.html',
//     styleUrls: ['./login-form.component.css', '../../bootstrap/css/bootstrap.css'],
//     entryComponents: [MemberSessionComponent]
// })

// export class LoginFormComponent implements OnInit {
//     clientForm: FormGroup;
//     post: any;
//     // private pseudonym: string;
//     // private password: string;
//     private results: [any];
//      private collectionJson: Object;
//      submitted = false;
//      collection: any[] = [];

//      //ajouter pour gerer session
//      model: any = {};
//     returnUrl: string;
//     loading = false;
//     public memberData: any;
//     public memberToken: any;
//     public memberJson:any;

//     constructor(public rest: RestProvider, public fb: FormBuilder, private http: Http,
//          private route: ActivatedRoute,
//          private router: Router,
//          private alertService: AlertService) { 

//         this.clientForm = this.fb.group({
//           'pseudonym': [null, Validators.compose([Validators.required,  Validators.minLength(4)])],
//           'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])]
//           });

//     }

//     ngOnInit() {
//         // reset login status
//         this.rest.logout();

//         // get return url from route parameters or default to '/'
//         this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

//     }

//     onSubmit(post) {
//             //ceci aussi gerer session
//             this.loading = true;

//               this.model.pseudonym = post.pseudonym;
//               this.model.password = post.password;

//         this.rest.postLoginMember( this.model.pseudonym, this.model.password)
//         .subscribe((data) => {

//                console.log("you have submitted",data.pseudonym);
//                 this.submitted = true;
//                 // let value: string = localStorage.getItem("currentMember");
//                   // store member details and jwt token in local storage to keep user logged in between page refreshes

//                   //let value: string = localStorage.getItem("currentMember");
//                   this.memberData = localStorage.getItem("currentMember");
//                 localStorage.setItem('currentMember', JSON.stringify(this.memberData));
//                 this.memberData = localStorage.getItem("currentMember");
//                       this.memberToken= this.memberData.token;
//                       //alert(this.memberToken);
//                       console.log("forlogin",this.memberToken.token);

//         },
//         error => {
//             this.alertService.error(error);
//             this.loading = false;
//         });

//              let currentMember=localStorage.getItem('currentMemberser');
//              console.log("current member submitted", currentMember);

//             //  this.router.navigate([this.returnUrl]);
//             this.router.navigateByUrl('/member-session',this.model.pseudonym );

//           }



//}
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RestProvider } from '../../providers/rest/rest';

import { AlertService, AuthenticationService, MeetingService } from '../_services/index';
import { HomeComponent } from '../home/home.component';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Meeting } from '../_models/meeting';



@Component({
    moduleId: module.id,
    templateUrl: 'login-form.component.html',
    styleUrls: ['./login-form.component.css', '../../bootstrap/css/bootstrap.css'],
    providers: [AlertService, AuthenticationService],
    entryComponents: [HomeComponent]
})

export class LoginFormComponent implements OnInit {
    model: any = {};
    loading = false;
    returnUrl: string;
    clientForm: FormGroup;
    private meetingName: string;
    public currentMeeting: Meeting;
    public meetings: Meeting[] = [];
    

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        // public fb: FormBuilder,
        private authenticationService: AuthenticationService,
        private alertService: AlertService,
        private rest : RestProvider, ) {
        //  this.clientForm = this.fb.group({
        //           'pseudonym': [null, Validators.compose([Validators.required,  Validators.minLength(4)])],
        //           'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])]
        //           });
        this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
    }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        //    this.router.navigateByUrl('/login-form');
        this.loadAllMeetings();
        // this.getAll;

    }


    private loadAllMeetings() {
        // this.meetingService.getAll().subscribe(meetings => { this.meetings = meetings; });
     this.rest.getAll().subscribe(meetings => { this.meetings = meetings; });

        
    }


    // onSubmit(post) {
    //                 //ceci aussi gerer session
    //                 this.loading = true;

    //                   this.model.pseudonym = post.pseudonym;
    //                   this.model.password = post.password;

    //                 this.authenticationService.login( this.model.pseudonym, this.model.password)
    //                 .subscribe(
    //                     data => {
    //                         this.router.navigate(['/']);
    //                     },
    //                     error => {
    //                         this.alertService.error(error);
    //                         this.loading = false;
    //                     });
    //         }
    login() {
        this.loading = true;
        this.authenticationService.login(this.model.pseudonym, this.model.password, this.model.meetingName)
            .subscribe(
            data => {

                this.router.navigate(['this.returnUrl']);
                // this.router.navigateByUrl('/home');
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}