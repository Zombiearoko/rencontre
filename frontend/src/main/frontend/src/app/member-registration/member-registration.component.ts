import { ViewChild, Component,Input,ElementRef, OnInit } from '@angular/core';

import { Response,HttpModule,Http } from '@angular/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
//import { RegisterPage } from '../register/register';
import { RestProvider } from '../../providers/rest/rest';

@Component({
  selector: 'app-member-registration',
  templateUrl: './member-registration.component.html',
  styleUrls: ['./member-registration.component.css']
})
export class MemberRegistrationComponent implements OnInit {
  

  clientForm: FormGroup;
  customerPictureFile: File;
  @ViewChild('customerPicture') customer_picture;
  post: any; 
  titleAlert:string = 'You must specify a pseudo thats between 3 and 5 characters';
private pseudonym: string;
private password: string;
private confirmPassword: string;
private birthDate: string;
private emailAdress: string;
private gender: string;
private phoneNumber: string;
private picture: string;
private results: [any];
private collectionJson: object;
submitted = false;

  constructor(public rest: RestProvider, public fb: FormBuilder, private http: Http) { 
    this.clientForm = this.fb.group({
      'pseudonym': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(5)])],
      'birthDate': [null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(45)])],
    'emailAdress': [null, Validators.compose([Validators.required, Validators.email])],
    'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
    'confirmPassword': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
    'phoneNumber': [null, Validators.compose([Validators.required])],
    'gender': [null, Validators.compose([Validators.required])],
    // 'customerPicture': [null, Validators.required]
    'customerPicture': ''
    });
    
  }

  
  
onsubmit(post){

  this.pseudonym = post.pseudonym;
  this.gender = post.gender;
  this.birthDate = post.birthDate;
  this.phoneNumber = post.phoneNumber;
  this.emailAdress = post.emailAdress;
  this.password = post.password;
  this.confirmPassword = post.confirmPassword;
  this.picture = post.picture;
  const Image = this.customer_picture.nativeElement;
  
 
 
  const url = 'http://localhost:8091/rencontre/Member/registration' +'?pseudonym='
  + this.pseudonym + '&gender=' +this.gender +'&birthDate=' + this.birthDate + '&phoneNumber='
  + this.phoneNumber + '&emailAdress=' + this.emailAdress + '&password=' + this.password + '&confirmPassWord=' 
  + this.confirmPassword + '&picture=' + this.picture;
  console.log(this.pseudonym);
  this.rest.postAccount(this.pseudonym , this.gender,  this.emailAdress, this.password, this.phoneNumber, this.birthDate, this.picture )
  .subscribe((data) => {
        console.log(this.pseudonym);
        this.submitted = true;
       });
  this.http.get(url).subscribe((resp)=>{
    this.results = resp['results'];
    this.collectionJson = resp.json();
  console.log(this.collectionJson);
});



  
}
  ngOnInit() {
    
    
  }

}