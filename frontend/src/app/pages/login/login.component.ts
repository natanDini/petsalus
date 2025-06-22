import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
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
      imagem: 'assets/images/pet_shop.jpg',
      titulo: 'Cuidados com seu Pet',
      descricao: 'Serviços veterinários e produtos com qualidade.',
    },
    {
      imagem: 'assets/images/banho-e-tosa.png',
      titulo: 'Banho e Tosa',
      descricao: 'Seu pet limpo, feliz e saudável.',
    },
    {
      imagem: 'assets/images/vacinacao.jpg',
      titulo: 'Vacinação em Dia',
      descricao: 'Proteja quem você ama com nossas vacinas.',
    },
  ];

  constructor(private http: HttpClient, private router: Router) {}

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

          if (response.userRole === 'DONO') {
            this.router.navigate(['/dono']);
          } 
          
          if (response.userRole === 'TUTOR') {
            this.router.navigate(['/tutor']);
          }

          if (response.userRole === 'VETERINARIO') {
            this.router.navigate(['/veterinario']);
          }

          if (response.userRole === 'COLABORADOR') {
            this.router.navigate(['/colaborador']);
          }
        },
        error: (err) => {
          console.error('Erro no login', err);
          alert('Usuário ou senha inválidos!');
        }
      });
  }
}