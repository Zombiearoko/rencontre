
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
    styleUrls: ['./login-form.component.css', '../../bootstrap/css/bootstrap.css', '../../font-awesome-4.7.0/css/font-awesome.css'],
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
        private rest: RestProvider, ) {

        this.currentMeeting = JSON.parse(localStorage.getItem('currentMeeting'));
    }


    //getting meeting type when selected pseudo
    public Filter(value: string) {

        console.log('pseudo donne', value, );

        this.loadAllMeetings(value);

    }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        //    this.router.navigateByUrl('/login-form');

        // this.getAll;


    }


    private loadAllMeetings(pseudonym: string) {
        // this.meetingService.getAll().subscribe(meetings => { this.meetings = meetings; });
        //  this.rest.getAll().subscribe(meetings => { this.meetings = meetings; });
        this.rest.getAllByPeudo(pseudonym).subscribe(meetings => { this.meetings = meetings; });


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

                // this.router.navigate(['this.returnUrl']);
                this.router.navigate(['/home']);

                // this.router.navigateByUrl('/home');
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}