import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { EditProfilComponent } from './edit-profil/edit-profil.component';
import { RatingCommentComponent } from './rating-comment/rating-comment.component';


export const routes: Routes = [

    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    { path: 'edit-profil', component: EditProfilComponent, /*canActivate: [authGuard]*/ },
    { path: 'rating-comment', component: RatingCommentComponent, /*canActivate: [authGuard]*/ },
    
];
