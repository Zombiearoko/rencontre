import { Injectable } from '@angular/core';
import { Response, HttpModule, RequestOptions, RequestMethod, RequestOptionsArgs, Http, Headers } from '@angular/http';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

/*
  Generated class for the RestProvider provider.
 
  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class RestProvider {
  public startUrl = 'http://localhost:8091';
  public memberData: any;
  public memberToken: any;
  public memberJson: any;


  constructor(public http: Http) {

  }

  public getHello(title, body) {
    let headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: headers1 });
    let url3 = "https://jsonplaceholder.typicode.com/post";

  }
  public getUser() {
    let headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: headers1 });

    let url = "http://jsonplaceholder.typicode.com/posts";
    return this.http.post(url, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }

  public postAccount(pseudonym, birthDate, gender, emailAdress, phoneNumber, password,
    confirmPassword, name, meetingName, firstName,
    lastName, schoolName, levelStudy, profession,
    countryName, regionName, departmentName, boroughName, townName, concessionName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {

      pseudonym: pseudonym,
      birthDate: birthDate,
      gender: gender,
      emailAdress: emailAdress,
      phoneNumber: phoneNumber,
      password: password,
      confirmPassWord: confirmPassword,
      //  picture: picture,
      name: name,
      meetingName: meetingName,
      firstName: firstName,
      lastName: lastName,
      schoolName: schoolName,
      levelStudy: levelStudy,
      profession: profession,
      countryName: countryName,
      regionName: regionName,
      departmentName: departmentName,
      boroughName: boroughName,
      townName: townName,
      concessionName: concessionName,

    };
    const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + pseudonym + '&birthDate=' + birthDate + '&gender=' + gender
      + '&emailAdress=' + emailAdress + '&phoneNumber='
      + phoneNumber + '&password=' + password + '&confirmPassword=' + confirmPassword + '&name=' + name
      + '&meetingName=' + meetingName + '&firstName=' + firstName + '&lastName=' + lastName + '&schoolName='
      + schoolName + '&levelStudy=' + levelStudy + '&profession=' + profession + '&countryName=' + countryName
      + '&regionName=' + regionName + '&departmentName=' + departmentName + '&boroughName=' + boroughName + '&townName=' + townName + '&concessionName=' + concessionName;
    const url2 = 'https://jsonplaceholder.typicode.com/posts';
    const urlSaph = 'http://192.168.8.105:8091/rencontre/Member/registration';
    const urlInno = 'http://localhost:8092/customer/addCustomer';
    return this.http.post(url, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }

  //add country

  public postCountry(countryName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      countryName: countryName,
    };
    const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName=' + countryName;
    const urlR = 'http://localhost:8091/rencontre/Administrator/addRegion';
    const urlD = 'http://localhost:8091/rencontre/Administrator/addDepartment';
    const urlB = 'http://localhost:8091/rencontre/Administrator/addBorough';
    return this.http.post(urlC, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }



  public getAccount() {

  }
  // get meeting by birthdate
  public getAllByDate(date: Date) {
    // return this.http.get('http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + date, this.jwt()).map((response: Response) => response.json());
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });

    const object = {
      bithDate: date,
    };
    const url = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=' + date;

    return this.http.post(url, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }

  // for meeting name when login
  public getAll() {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });

    const urlD = 'http://localhost:8091/rencontre/Administrator/listTypeMeeting?bithDate=1987-08-22';

    return this.http.post(urlD, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }

  // for meeting name when login
  public getAllByPeudo(pseudonym) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      pseudonym: pseudonym,
    };
    const urlD = 'http://localhost:8091/rencontre/Member/returnTypeMeeting?pseudonym=' + pseudonym;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log('de rest:', res.json()))
      .map((res: Response) => res.json());
  }

  // for status name when login
  public getAllStatus() {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });

    const urlD = 'http://localhost:8091/rencontre/Administrator/listAllStatus';

    return this.http.post(urlD, options)
      .do((res: Response) => console.log('de rest:', res.json()))
      .map((res: Response) => res.json());
  }
  // liste les region dun pays

  public getAllRegionByCountry(countryName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      countryName: countryName,
    };
    const urlD = 'http://localhost:8091/rencontre/Administrator/listRegion?countryName=' + countryName;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }
  // liste les departement dune region
  public getAllDepartmentByRegion(regionName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      regionName: regionName,
    };
    const urlD = 'http://localhost:8091/rencontre/Administrator/listDepartment?regionName=' + regionName;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }
  // liste les arrondissement dun departement
  public getAllBoroughByDepartment(departmentName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      department: departmentName,
    };
    const urlD = 'http://localhost:8091/rencontre/Administrator/listBorough?departmentName=' + departmentName;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }
  // liste les villes dun arrondissemnt

  public getAllTownByBorough(boroughName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      boroughName: boroughName,
    };
    const urlD = 'http://localhost:8091/rencontre/Administrator/listTown?BoroughName=' + boroughName;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }
  // liste les concessins dune vill
  public getAllConcessionByTown(townName) {
    const headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({ headers: headers1 });
    const object = {
      townName: townName,
    };
    const urlD = 'http://localhost:8091/rencontre/Administrator/listConcession?townName=' + townName;

    return this.http.post(urlD, object, options)
      .do((res: Response) => console.log(res.json()))
      .map((res: Response) => res.json());
  }



}





