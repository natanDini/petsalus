import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-empresa-colaborador',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empresa-colaborador.component.html',
  styleUrl: './empresa-colaborador.component.scss'
})
export class EmpresaColaboradorComponent {
  empresa: any;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  irParaServicos() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate([`/colaborador/empresa/servicos/${id}`]);
  }

  irParaProdutos() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate([`/colaborador/empresa/produtos/${id}`]);
  }

  irParaCompras() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate([`/colaborador/empresa/compras/${id}`]);
  }

  irParaAgendamentos() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate([`/colaborador/empresa/agendamentos/${id}`]);
  }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return;

    const id = this.route.snapshot.paramMap.get('id');
    this.http.get(`http://localhost:8080/petsalus/api/empresa/${id}`)
      .subscribe({
        next: (res) => this.empresa = res,
        error: (err) => console.error('Erro ao buscar empresa:', err)
      });
  }
}
