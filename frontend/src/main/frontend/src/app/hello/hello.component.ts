import { Component, OnInit,Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestProvider } from '../../providers/rest/rest';
@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent implements OnInit {
  @Input('group')
  public helloForm:FormGroup;
  public name: string;
  public surname: string;
  public valrecu={
    name:'test',
    surname:'test',
    application_type:'test'
  }
  submitted = false;  
  constructor( public rest:RestProvider,public formBuilder: FormBuilder, ) {
    this.createForm1();
     }

     createForm1() {
      let name='';
      let surname='';
      this.helloForm = this.formBuilder.group({
        name: [name, Validators.compose([Validators.required])],
        surname: [surname, Validators.compose([Validators.required,Validators.maxLength(45),Validators.minLength(3)])]
      });
      }

     onSubmit() { 
       this.rest.getHello(this.helloForm.value.name,this.helloForm.value.surname).subscribe((data)=>{
        this.valrecu=data;
        this.submitted = true;
       });
      }
  ngOnInit() {
  }

}
