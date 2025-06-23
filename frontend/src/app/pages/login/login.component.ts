import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  currentSlide = 0;

  username = '';
  senha = '';

  slides = [
    {
      imagem: 'assets/images/tutores.jpg',
      titulo: 'Para Tutores',
      descricao: 'Um ambiente completo para cuidar da saúde, bem-estar e felicidade do seu pet.',
    },
    {
      imagem: 'assets/images/dono-pet.jpg',
      titulo: 'Para Empresários e MEIs',
      descricao: 'Impulsione seu negócio com visibilidade, agendamentos e gestão facilitada.',
    },
    {
      imagem: 'assets/images/veterinario.jpg',
      titulo: 'Para Veterinários',
      descricao: 'Conecte-se a novos clientes, amplie sua atuação e ofereça um atendimento de excelência.',
    },
  ];

  constructor(private http: HttpClient, private router: Router) { }

  get totalSlides(): number {
    return this.slides.length;
  }

  nextSlide() {
    this.currentSlide = (this.currentSlide + 1) % this.totalSlides;
  }

  prevSlide() {
    this.currentSlide = (this.currentSlide - 1 + this.totalSlides) % this.totalSlides;
  }

  goToSlide(index: number) {
    this.currentSlide = index;
  }

  onSubmit() {
    const loginPayload = {
      username: this.username,
      senha: this.senha,
    };

    this.http.post<any>('http://localhost:8080/petsalus/api/auth/login', loginPayload)
      .subscribe({
        next: (response) => {
          localStorage.setItem('access_token', response.token);
          localStorage.setItem('user_role', response.userRole);

          // 🔔 Notifica a Navbar
          window.dispatchEvent(new Event('userChanged'));

          switch (response.userRole) {
            case 'DONO': this.router.navigate(['/dono']); break;
            case 'TUTOR': this.router.navigate(['/tutor']); break;
            case 'VETERINARIO': this.router.navigate(['/veterinario']); break;
            case 'COLABORADOR': this.router.navigate(['/colaborador']); break;
          }
        },
        error: (err) => {
          console.error('Erro no login', err);
          alert('Usuário ou senha inválidos!');
        }
      });
  }
}