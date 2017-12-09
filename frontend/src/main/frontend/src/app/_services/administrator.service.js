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
var AdministratorService = /** @class */ (function () {
    function AdministratorService(http) {
        this.http = http;
    }
    AdministratorService.prototype.getAll = function () {
        return this.http.get('/api/Administrators', this.jwt()).map(function (response) { return response.json(); });
    };
    AdministratorService.prototype.getById = function (id) {
        return this.http.get('/api/administrators/' + id, this.jwt()).map(function (response) { return response.json(); });
    };
    AdministratorService.prototype.create = function (administrator) {
        return this.http.post('/api/administrators', administrator, this.jwt()).map(function (response) { return response.json(); });
    };
    AdministratorService.prototype.update = function (administrator) {
        return this.http.put('/api/administrators/' + administrator.id, administrator, this.jwt()).map(function (response) { return response.json(); });
    };
    AdministratorService.prototype.delete = function (id) {
        return this.http.delete('/api/Administrators/' + id, this.jwt()).map(function (response) { return response.json(); });
    };
    // private helper methods
    AdministratorService.prototype.jwt = function () {
        // create authorization header with jwt token
        var currentAdministrator = JSON.parse(localStorage.getItem('currentAdministrator'));
        if (currentAdministrator && currentAdministrator.token) {
            var headers = new http_1.Headers({ 'Authorization': 'Bearer ' + currentAdministrator.token });
            return new http_1.RequestOptions({ headers: headers });
        }
    };
    AdministratorService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [http_1.Http])
    ], AdministratorService);
    return AdministratorService;
}());
exports.AdministratorService = AdministratorService;
//# sourceMappingURL=administrator.service.js.map