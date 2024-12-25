import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { Category } from '../models/category.model';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { User } from '../models/user.model';


@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [FormsModule, NgFor, NgIf],
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  categories: Category[] = [];
  user: User = new User(
    5,                        // ID of the administrator
    'John',                // first name
    'Doe',                 // last name
    'john.doe@example.com', // Email
    'password123',            // password
    1,                        // ID of the address
    4.5                    // rating
  );

  newCategory: Category = new Category(0, '');
  editingCategory: Category | null = null;
  message: string = '';

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe(
      data => this.categories = data,
      error => console.error('Error loading categories', error)
    );
  }

  onSubmit(): void {
    this.categoryService.createCategory(this.newCategory).subscribe(
      () => {
        this.newCategory = new Category(0, ''); // Reset the form
        this.loadCategories();
        this.message = 'Category added successfully.';
      },
      error => {
        this.message = 'Failed to add category.';
        console.error('Error creating category', error);
      }
    );
  }

  startEditing(category: Category): void {
    this.editingCategory = { ...category };
  }

  updateCategoryName(): void {
    if (this.editingCategory && this.editingCategory.id_category) {
      this.categoryService.modifyCategory(this.editingCategory.id_category, this.editingCategory).subscribe(
        () => {
          this.loadCategories();
          this.editingCategory = null;
          this.message = 'Category updated successfully.';
        },
        error => {
          this.message = 'Failed to update category.';
          console.error('Error modifying category', error);
        }
      );
    } else {
      console.error('Category ID is missing');
      this.message = 'Category ID is missing, update failed.';
    }
  }

  cancelEditing(): void {
    this.editingCategory = null;
  }


 deleteCategory(categoryId: number): void {
    console.log('Deleting category with ID:', categoryId); // Log the ID being sent
    this.categoryService.deleteCategory(categoryId).subscribe(
      () => {
        this.loadCategories();
        this.message = 'Category deleted successfully.';
      },
      error => {
        this.message = 'Failed to delete category.';
        console.error('Error deleting category', error);
      }
    );
  }

}