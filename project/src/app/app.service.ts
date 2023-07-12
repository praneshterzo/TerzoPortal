import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from './EmployeeDetails';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  
  constructor(private http: HttpClient) { }
  private url="http://localhost:8080/";

    getEmployee(): Observable<any[]>{
      console.log("In get employee service");
      return this.http.get<any[]>(this.url+'view')
    }

    addEmployee(employee:Employee){
      return this.http.post<Employee>(`${this.url}add`, employee)
    }
    
    getUserById(id: number): Observable<Employee>{
      return this.http.get<Employee>(`${this.url}employee/${id}`)
    }

    updateUser(id?: number ,Employee?: any): Observable<any>{
      return this.http.put<any>(`${this.url}update/${id}`, Employee)
    }

    deleteUser(id: number): Observable<any>{
      return this.http.delete<any>(`${this.url}delete/${id}`)
    }
  }

