import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-veterinario-home',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule
  ],
  templateUrl: './veterinario-home.component.html',
  styleUrl: './veterinario-home.component.scss'
})
export class VeterinarioHomeComponent {
  empresas: any[] = [];

  constructor(
    private cdr: ChangeDetectorRef,
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  verEmpresa(id: number) {
    this.router.navigate([`/veterinario/empresa/${id}`]);
  }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      // Evita que SSR ou preload dispare a requisição
      return;
    }

    const token = localStorage.getItem('access_token');
    if (!token) {
      console.warn('Token ausente. Redirecionando...');
      window.location.href = '/login'; // ou use this.router.navigate(['/login']);
      return;
    }

    this.http.get('http://localhost:8080/petsalus/api/empresa/empregado')
      .subscribe({
        next: (res: any) => {
          this.empresas = [res];
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error(err);
          if (err.status === 401 || err.status === 403) {
            localStorage.clear();
            window.location.href = '/login';
          }
        }
      });
  }
}
