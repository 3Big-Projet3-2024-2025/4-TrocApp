import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ItemService } from '../services/item.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
import { Category } from '../models/category.model';
import { AsyncPipe } from '@angular/common';
import { CategoryService } from '../services/category.service';
import { UsersService } from '../services/users.service';
import { User } from '../user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-item-form',
  standalone: true,
  imports: [FormsModule, AsyncPipe],
  templateUrl: './item-form.component.html',
  styleUrl: './item-form.component.css'
})
export class ItemFormComponent implements OnInit {
  
  constructor(private itemService: ItemService, private route: ActivatedRoute, private router: Router, private categoryService: CategoryService, private userService: UsersService, private authService: AuthService) {}
  
  userId!: number | null;

  ngOnInit(): void {
    this.userId = this.authService.getIDUserConnected();
    this.categoryList = this.categoryService.getCategories() as Observable<Category[]>;
    this.route.params.subscribe(params=> {
      if(params["id"]) {
        const subscr = this.itemService.getItem(params["id"]).subscribe(item => {
          this.itemToSave = item as Item;
          this.imagePreview = this.itemToSave.photo;
          console.log(this.itemToSave);
          subscr.unsubscribe();
        })
      }
    })
  }


  availableItems!: Observable<Item[]>;

  categoryList!: Observable<Category[]>;

  errorMessage = "";

  imagePreview: string | ArrayBuffer | null = null;

  itemToSave: Item = {
    id:-1,
    name: "",
    description: "",
    photo: "",
    available: true,
    category: {
      id_category: 1,
      name: "",
      items: undefined,
      user: undefined
    },
    owner: {
      id: -1,
      firstName: "",
      lastName: "",
      email: "lalalalma",
      password: "",
      rating: -1,
      address: null,
      roles: [] ,
      username: ""
    }
  }

  /*   
  captures the file from the input and uses FileReader to convert it to a Base64 string
  The Base64 string is saved in itemToSave.photo for upload and shown in the imagePreview.
 */
  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
        this.itemToSave.photo = reader.result as string; // Store base64 for upload
      };
      reader.readAsDataURL(file); // Convert file to base64
    }
  }


  saveItem() {
    this.itemToSave.owner.id = this.userId as number;
    const sub = this.itemService.saveItem(this.itemToSave).subscribe({
      next: (item) => {
        console.log(this.itemToSave);
        console.log(item);
        this.router.navigate(["/my-items"]);
        sub.unsubscribe();
      },
      error: (error) => {
        this.errorMessage = error.error;
        console.log(this.itemToSave);
        sub.unsubscribe();
      }
    })
  }

}