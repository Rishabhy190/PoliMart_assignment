import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" routerLink="/">Polimart Media</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item" *ngIf="isLoggedIn()">
              <a class="nav-link" routerLink="/upload">Upload</a>
            </li>
            <li class="nav-item" *ngIf="isLoggedIn()">
              <a class="nav-link" routerLink="/list">My Media</a>
            </li>
          </ul>
          <ul class="navbar-nav">
             <li class="nav-item" *ngIf="!isLoggedIn()">
              <a class="nav-link" routerLink="/login">Login</a>
            </li>
             <li class="nav-item" *ngIf="!isLoggedIn()">
              <a class="nav-link" routerLink="/register">Register</a>
            </li>
            <li class="nav-item" *ngIf="isLoggedIn()">
              <button class="btn btn-link nav-link" (click)="logout()">Logout</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <main class="container mt-4">
      <router-outlet></router-outlet>
    </main>
  `,
  styles: []
})
export class AppComponent {
  constructor(private authService: AuthService, private router: Router) {}

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
} 