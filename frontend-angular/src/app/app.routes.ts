import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MediaListComponent } from './components/media-list/media-list.component';
import { MediaUploadComponent } from './components/media-upload/media-upload.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'list', component: MediaListComponent, canActivate: [authGuard] },
    { path: 'upload', component: MediaUploadComponent, canActivate: [authGuard] },
    { path: '', redirectTo: '/list', pathMatch: 'full' },
    { path: '**', redirectTo: '/list' }
]; 