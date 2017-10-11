"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var router_1 = require("@angular/router");
var index_1 = require("./home/index");
var index_2 = require("./login-form/index");
// var index_3 = require("./member-registration/index");
var index_4 = require("./_guards/index");
var appRoutes = [
    { path: '', component: index_1.HomeComponent, canActivate: [index_4.AuthGuard] },
    { path: 'login-form', component: index_2.LoginFormComponent },
    // { path: 'member-registration', component: index_3.MemberRegistrationComponent },
    
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes);
//# sourceMappingURL=app.routing.js.map