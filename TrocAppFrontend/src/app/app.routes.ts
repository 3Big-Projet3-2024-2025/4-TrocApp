import { Routes } from '@angular/router';
import { RouterModule} from '@angular/router';
import { NgModule } from '@angular/core';
import { UsersManagementComponent } from './users-management/users-management.component';
import { ViewingItemsMapComponent } from './viewing-items-map/viewing-items-map.component';
import { DetailedViewItemComponent } from './detailed-view-item/detailed-view-item.component';
import { CategoryListComponent} from './category-list/category-list.component';
import { GdprResquestComponent } from './gdpr-resquest/gdpr-resquest.component';
export const routes: Routes = [

    //Path for user management as admin
   { path: "admin/users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    //Path for viewing items on a map
   { path: "viewing-items-map", component: ViewingItemsMapComponent },
    //Path for viewing a detailed item
   {path: "detailed-view-item", component: DetailedViewItemComponent },
    //Path for viewing a list of categories
    {path: "category-list", component: CategoryListComponent},
   // { path: '**', redirectTo: '/category-list', pathMatch: 'full' }
     {path : "gdpr-request", component:GdprResquestComponent},
     {path : '**', redirectTo: '/gdpr-request', pathMatch: 'full'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }