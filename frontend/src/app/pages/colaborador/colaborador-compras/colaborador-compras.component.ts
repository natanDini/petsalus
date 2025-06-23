import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-colaborador-compras',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './colaborador-compras.component.html',
  styleUrl: './colaborador-compras.component.scss'
})
export class ColaboradorComprasComponent {
  empresaId: string | null = null;
  compras: any[] = [];
  compraExpandida: { [id: number]: boolean } = {};

  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit(): void {
    this.empresaId = this.route.snapshot.paramMap.get('id');
    if (this.empresaId) this.carregarCompras();
  }

  carregarCompras() {
    this.http.get<any[]>(`http://localhost:8080/petsalus/api/compra/${this.empresaId}/compras`)
      .subscribe({
        next: (res) => this.compras = res,
        error: (err) => console.error('Erro ao carregar compras:', err)
      });
  }

  toggleProdutos(compraId: number) {
    this.compraExpandida[compraId] = !this.compraExpandida[compraId];
  }
}