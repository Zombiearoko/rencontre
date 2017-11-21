
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService, AuthenticationService } from '../_services/index';
import { MemberSessionComponent } from '../member-session/member-session.component';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';



@Component({
    selector: 'app-login-admin',
    moduleId: module.id,
    templateUrl: './login-admin.component.html',
    styleUrls: ['./login-admin.component.css', '../../bootstrap/css/bootstrap.css'],
    providers: [AlertService, AuthenticationService],
    entryComponents: [MemberSessionComponent]
})

export class LoginAdminComponent implements OnInit {
    public model: any = {};
    loading = false;
    returnUrl: string;
    clientForm: FormGroup;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        public fb: FormBuilder,
        private authenticationService: AuthenticationService,
        private alertService: AlertService) {
        this.clientForm = this.fb.group({
            'loginAdmin': [null, Validators.compose([Validators.required, Validators.minLength(4)])],
            'passwordAdmin': [null, Validators.compose([Validators.required, Validators.minLength(6)])]
        });
    }

    ngOnInit() {
        // reset login status
        this.authenticationService.logoutAdmin();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/session-admin';
        //    this.router.navigateByUrl('/login-form');
    }

    loginAdmin() {
        this.loading = true;
        this.authenticationService.loginAdmin(this.model.loginAdmin, this.model.passwordAdmin)
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

    onSubmit(post) {
        //ceci aussi gerer session
        this.loading = true;

        this.model.loginAdmin = post.loginAdmin;
        this.model.passwordAdmin = post.passwordAdmin;

        this.authenticationService.loginAdmin(this.model.loginAdmin, this.model.passwordAdmin)
            .subscribe(
            data => {
                this.router.navigate(['/session-admin']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }


}