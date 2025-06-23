import { Component, Input, OnInit, Inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule,
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  @Input() appName = 'PetSalus';
  @Input() menuItems: { path: string; title: string; icon?: string }[] = [
    { path: '/', title: 'Home', icon: 'home' }
  ];

  user: any = null;
  isBrowser = false;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    if (!this.isBrowser) return;

    // Verifica se já está logado
    this.loadUserIfLoggedIn();

    // Escuta eventos de login/logout
    window.addEventListener('userChanged', this.onUserChanged);
  }

  ngOnDestroy(): void {
    if (this.isBrowser) {
      window.removeEventListener('userChanged', this.onUserChanged);
    }
  }

  onUserChanged = () => {
    this.loadUserIfLoggedIn();
  };

  loadUserIfLoggedIn(): void {
    const token = localStorage.getItem('access_token');
    if (token) {
      this.http.get<any>('http://localhost:8080/petsalus/api/user/perfil-pequeno')
        .subscribe({
          next: (res) => this.user = res,
          error: () => this.user = null
        });
    } else {
      this.user = null;
    }
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.clear();
      this.user = null;
      window.dispatchEvent(new Event('userChanged'));
      this.router.navigate(['/login']);
    }
  }
}