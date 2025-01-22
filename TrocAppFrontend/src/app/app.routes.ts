import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { EditProfilComponent } from './edit-profil/edit-profil.component';
import { ViewingItemsMapComponent } from './viewing-items-map/viewing-items-map.component';
import { DetailedViewItemComponent } from './detailed-view-item/detailed-view-item.component';
import { ItemFormComponent } from './item-form/item-form.component';
import { ExchangeListComponent } from './exchange-list/exchange-list.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { MyItemsListComponent } from './my-items-list/my-items-list.component';
import { GdprFormComponent } from './gdpr-form/gdpr-form.component';
import { GdprAdminComponent } from './gdpr-admin/gdpr-admin.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { AuthComponent } from './auth/auth.component';
import { RegistrationFormComponent } from './registration-form/registration-form.component';

import { authGuard } from './auth.guard';
export const routes: Routes = [
    { path: '', component: ViewingItemsMapComponent }, // Default Path (Home Page)
    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, canActivate: [authGuard] },
    //Path for viewing items on a map
    { path: "viewing-items-map", component: ViewingItemsMapComponent },
    //Path for viewing a detailed item
    { path: "detailed-view-item", component: DetailedViewItemComponent },
    //{ path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    {path: 'category-list', component: CategoryListComponent, canActivate: [authGuard]},
    //{ path: '**', redirectTo: '/category-list', pathMatch: 'full' },
    
    { path: "detailed-view-item/:id", component: DetailedViewItemComponent , canActivate: [authGuard]},
    //path for adding an item
    { path: "item", component: ItemFormComponent, canActivate: [authGuard]  },
    //path for updating an item
    { path: "item/:id", component: ItemFormComponent, canActivate: [authGuard]  },
    //path by default 
    { path: "", redirectTo: "/viewing-items-map", pathMatch: "full" },

    {path: "exchanges", component: ExchangeListComponent, canActivate: [authGuard]  },
    { path: "my-items", component: MyItemsListComponent, canActivate: [authGuard]  },

    // Path for editing a user
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    { path: 'user-profile/:id', component: UserProfileComponent, canActivate: [authGuard] },

    // Path for the GDPR form
    {path : 'gdpr-form', component: GdprFormComponent, canActivate: [authGuard]},
    // Path for the GDPR admin page
    {path : 'gdpr-admin', component: GdprAdminComponent, canActivate: [authGuard]},

    
    { path: "users-management", component: UsersManagementComponent, canActivate: [authGuard] },
    { path: 'edit/:id', component: UserEditComponent, canActivate: [authGuard] },
    { path: 'edit-profil', component: EditProfilComponent, canActivate: [authGuard] },
    { path: 'auth/login', component: AuthComponent},
    { path: 'inscription', component: RegistrationFormComponent},
    { path: '**', redirectTo: 'viewing-items-map', pathMatch: 'full' }, // Wildcard route
    
];