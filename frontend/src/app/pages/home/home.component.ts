import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Slide { title: string; text: string; img: string; }
interface Service { title: string; text: string; img: string; }
interface Product { name: string; img: string; }
interface Category { name: string; img: string; }

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule, RouterModule],
})
export class HomeComponent {

  /* ---------- Carrossel de destaques ---------- */
  highlightSlides: Slide[] = [
    {
      title: 'Produtos Premium',
      text: 'Rações, acessórios e muito mais com entrega rápida.',
      img: 'assets/home/carrossel-one.jpg'
    },
    {
      title: 'Serviços Veterinários',
      text: 'Consultas presenciais ou online com especialistas.',
      img: 'assets/home/carrossel-two.jpg'
    },
    {
      title: 'Registro Médico Digital',
      text: 'Todos os exames e vacinas do seu pet em um só lugar.',
      img: 'assets/home/carrossel-three.jpg'
    },
  ];

  currentSlide = 0;

  nextSlide(): void {
    this.currentSlide = (this.currentSlide + 1) % this.highlightSlides.length;
  }

  prevSlide(): void {
    this.currentSlide =
      (this.currentSlide - 1 + this.highlightSlides.length) % this.highlightSlides.length;
  }

  goToSlide(index: number): void {
    this.currentSlide = index;
  }

  /* ---------- Conteúdo estático ---------- */
  categories: Category[] = [
    { name: 'Cães', img: 'assets/home/caes.jpeg' },
    { name: 'Gatos', img: 'assets/home/gato.jpeg' },
    { name: 'Pássaros', img: 'assets/home/calopsita.jpg' },
    { name: 'Roedores', img: 'assets/home/roedores.png' },
    { name: 'Peixes', img: 'assets/home/peixes.jpeg' },
    { name: 'Répteis', img: 'assets/home/repteis.jpg' },
  ];

  services: Service[] = [
    {
      title: 'Banho & Tosa',
      text: 'Profissionais qualificados e produtos hipoalergênicos.',
      img: 'assets/home/banho-tosa.jpg'
    },
    {
      title: 'Consulta Veterinária',
      text: 'Atendimento clínico completo para todas as espécies.',
      img: 'assets/home/consulta.jpg'
    },
    {
      title: 'Vacinação',
      text: 'Protocolos atualizados e controle de carteira vacinal.',
      img: 'assets/home/vacinas.png'
    },
    {
      title: 'Registro Médico Digital',
      text: 'Histórico de exames, prescrições e laudos a qualquer momento.',
      img: 'assets/home/registro.jpg'
    },
  ];

  products: Product[] = [
    { name: 'Rações Super Premium', img: 'assets/home/racao-premium.png' },
    { name: 'Brinquedos Inteligentes', img: 'assets/home/brinquedos-inteligentes.jpg' },
    { name: 'Caminhas Conforto Plus', img: 'assets/home/caminhas.jpg' },
    { name: 'Areia Higiênica Natural', img: 'assets/home/areia.jpg' },
    { name: 'Arranhadores Compactos', img: 'assets/home/arranhadores.jpg' },
    { name: 'Caixa Transporte Airline-Safe', img: 'assets/home/caixa.png' },
  ];
}