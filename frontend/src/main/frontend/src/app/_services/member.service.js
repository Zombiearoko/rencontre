"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var MemberService = /** @class */ (function () {
    function MemberService(http) {
        this.http = http;
    }
    MemberService.prototype.getAll = function () {
        return this.http.get('/api/Members', this.jwt()).map(function (response) { return response.json(); });
    };
    MemberService.prototype.getById = function (id) {
        return this.http.get('/api/members/' + id, this.jwt()).map(function (response) { return response.json(); });
    };
    MemberService.prototype.create = function (member) {
        return this.http.post('/api/members', member, this.jwt()).map(function (response) { return response.json(); });
    };
    MemberService.prototype.update = function (member) {
        return this.http.put('/api/members/' + member.id, member, this.jwt()).map(function (response) { return response.json(); });
    };
    MemberService.prototype.delete = function (id) {
        return this.http.delete('/api/members/' + id, this.jwt()).map(function (response) { return response.json(); });
    };
    // private helper methods
    MemberService.prototype.jwt = function () {
        // create authorization header with jwt token
        var currentMember = JSON.parse(localStorage.getItem('currentMember'));
        if (currentMember && currentMember.token) {
            var headers = new http_1.Headers({ 'Authorization': 'Bearer ' + currentMember.token });
            return new http_1.RequestOptions({ headers: headers });
        }
    };
    MemberService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [http_1.Http])
    ], MemberService);
    return MemberService;
}());
exports.MemberService = MemberService;
//# sourceMappingURL=member.service.js.map