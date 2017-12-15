"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var router_1 = require("@angular/router");
var index_1 = require("./rencotre/index");
var index_2 = require("./login-form/index");
var index_3 = require("./member-registration/member-registration.component");
var index_4 = require("./home/index");
var index_5 = require("./confimr-account/confimr-account.component");
var index_6 = require("./member-register1/member-register1.component");
var index_7 = require("./add-regio/add-regio.component");
var index_8 = require("./add-department/add-department.component");
var index_9 = require("./add-borough/add-borough.component");
var index_10 = require("./add-town/add-town.component");
var index_11 = require("./add-meeting/add-meeting.component");
var index_12 = require("./add-status/add-status.component");
var index_13 = require("./member-session/member-session.component");
var index_14 = require("./login-admin/login-admin.component");
var index_15 = require("./about/about.component");
var index_16 = require("./session-admin/session-admin.component");
var index_17 = require("./_guards/index");


var appRoutes = [
    { path: 'confimr-account', component:index_5.ConfimrAccountComponent },
    { path: 'rencotre', component:index_1.RencotreComponent },
    { path: '', component: index_4.HomeComponent, canActivate: [index_17.AuthGuard] },
    { path: 'login-form', component: index_2.LoginFormComponent },
    { path: 'member-registration', component: index_3.MemberRegistionComponent },
    // { path: 'home', component: index_4.HomeComponent },
    { path: 'member-register1', component: index_6.MemberRegister1Component },
    // { path: 'add-country', component: AddCountryComponent },add country=member-session
    { path: 'add-region', component: index_7.AddRegionComponent },
    { path: 'add-department', component: index_8.AddDepartmentComponent },
    { path: 'add-borough', component: index_9.AddBoroughComponent },
    { path: 'add-town', component: index_10.AddTownComponent },
    { path: 'add-meeting', component: index_11.AddMeetingComponent },
    { path: 'add-status', component: index_12.AddStatusComponent },
    { path: 'member-session', component: index_13.MemberSessionComponent },
    { path: 'login-admin', component: index_14.LoginAdminComponent },
    { path: 'about', component: index_15.AboutComponent },
    { path: 'session-admin', component: index_16.SessionAdminComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes);
//# sourceMappingURL=app.routing.js.map