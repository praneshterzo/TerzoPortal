import { Component,OnInit,Input } from "@angular/core";
import {MatSidenavModule} from '@angular/material/sidenav';
@Component({
    selector:'app-navbar',
    templateUrl: './LeftNavbar.component.html',
    styleUrls:['./Leftnavbar.component.css']
})

export class leftnavbarComponent implements OnInit{
    title:string="Lists";
    @Input()
    fromParent!: string;
    parentmessage:string="Message from child"

    ngOnInit(): void {
        throw new Error("Method not implemented.");
    }
    
}