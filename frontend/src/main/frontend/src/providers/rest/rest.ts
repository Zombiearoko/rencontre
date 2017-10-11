import { Injectable } from '@angular/core';
    import { Response,HttpModule,RequestOptions,RequestMethod,RequestOptionsArgs,Http,Headers } from '@angular/http';
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
      public memberJson:any;
     
       
        constructor(public http: Http) {
         
        }
      
        public getHello(title, body) {
          let headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
          let options = new RequestOptions({ headers: headers1 });
      
         /*  let url = 'http://localhost:8091/hello' + '?name=' + name + '&surname=' + surname;
          let url2 = '/api/hello' + '?name=' + name + '&surname=' + surname; */
          let url3 = "https://jsonplaceholder.typicode.com/post";
          //alert("toto 2"); 
          // return this.http.post(url3, options)
          //   .do((res: Response) => console.log(res.json()))
          //   .map((res: Response) => res.json());
        }
        public getUser() {
          let headers1 = new Headers({ 'Access-Control-Allow-Origin': '*' });
          let options = new RequestOptions({ headers: headers1 });
      
          // let url= "http://jsonplaceholder.typicode.com/posts";
          // return  this.http.post(url, options)
          //  .do((res: Response) => console.log(res.json()))
          //   .map((res: Response) => res.json());
          let url= "http://jsonplaceholder.typicode.com/posts";
          
          // return  this.http.get(url)
          //  .do((res: Response) => console.log(res.json()))
          //   .map((res: Response) => res.json());
          
            
        }
    
        public postAccount(pseudonym, birthDate,  gender, emailAdress, phoneNumber, password, confirmPassword, picture) {
          const headers1 =  new Headers({ 'Access-Control-Allow-Origin': '*' });
    const options = new RequestOptions({  headers: headers1 });
          const object = {
           pseudonym: pseudonym,
           birthDate: birthDate,
           gender: gender,
           emailAdress: emailAdress,
           phoneNumber: phoneNumber,
           password: password,
           confirmPassWord: confirmPassword,
           picture: picture
         
          
          };
          const url = 'http://localhost:8091/rencontre/InternetSurfer/registration?pseudonym=' + pseudonym +'&birthDate=' + birthDate + '&gender=' +gender + '&emailAdress=' + emailAdress + '&phoneNumber=' + phoneNumber + '&password=' + password + '&confirmPassword=' + confirmPassword + picture;
          const url2 = 'https://jsonplaceholder.typicode.com/posts';
          const urlSaph = 'http://192.168.8.105:8091/rencontre/Member/registration';
          const urlInno = 'http://localhost:8092/customer/addCustomer';
        return  this.http.post(url, object, options)
                  .do((res: Response ) => console.log(res.json()))
                  .map((res: Response ) => res.json());
    }

    //add country

    public postCountry(countryName) {
      const headers1 =  new Headers({ 'Access-Control-Allow-Origin': '*' });
const options = new RequestOptions({  headers: headers1 });
      const object = {
       countryName: countryName,
      };
      const urlC = 'http://localhost:8091/rencontre/Administrator/addCountry?countryName='+countryName;
      const urlR = 'http://localhost:8091/rencontre/Administrator/addRegion';
      const urlD = 'http://localhost:8091/rencontre/Administrator/addDepartment';
      const urlB = 'http://localhost:8091/rencontre/Administrator/addBorough';
    return  this.http.post(urlC, object, options)
              .do((res: Response ) => console.log(res.json()))
              .map((res: Response ) => res.json());
}
             


public   getAccount() {
    
        }
      
  //       //for login
  //       public postLoginMember(pseudonym, password) {
  //         const headers1 =  new Headers({ 'Access-Control-Allow-Origin': '*' });
  //   const options = new RequestOptions({  headers: headers1 });
    
  //         const object = {
  //           pseudonym: pseudonym,
  //           password: password,
  //          };
           
           
  //         const url ='http://localhost:8091/rencontre/Member/Connexion?pseudonym='+pseudonym+'&password='+password;
  //         const url2 = 'https://jsonplaceholder.typicode.com/posts';
  //       return  this.http.post(url, object, options)
  //                 .do((res: Response ) => console.log(res.json()))
  //                  //.map((res: Response ) => res.json());
  //                   //ajouté a partir dici
  //                 .map((res: Response) => {
  //                   // login successful if there's a jwt token in the response
  //                   let member = res.json();
  //                   if (member && member.token) {
  //                       // store member details and jwt token in local storage to keep user logged in between page refreshes
  //                     localStorage.setItem('currentMember', JSON.stringify(member));
  //                     //let value: string = localStorage.getItem("currentMember");
  //                     this.memberData = localStorage.getItem("currentMember");
                    
  //                         this.memberToken= this.memberData.token;
  //                         alert(this.memberToken);
  //                         console.log("for rest",this.memberToken.token);
                     
                    
  //                   }
     
  //                   return member;
  //               });
  //               //jusquici
                 
  //   }

  //   //for logout

  //   logout() {
  //     // remove member from local storage to log user out
  //     localStorage.removeItem('currentMember');
  // }

    }
      
       
          
      
  
