import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PLATFORM_ID } from '@angular/core';

@Component({
  selector: 'app-empresa-produtos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './empresa-produtos.component.html',
  styleUrls: ['./empresa-produtos.component.scss']
})
export class EmpresaProdutosComponent implements OnInit {
  empresaId: string | null = null;
  produtos: any[] = [];
  modalAberta = false;

  menuProdutoAberto: number | null = null;
  modoEdicao = false;          // true quando editando
  produtoEditandoId: number | null = null;

  toggleMenuProduto(id: number) {
    this.menuProdutoAberto = this.menuProdutoAberto === id ? null : id;
  }

  novoProduto = {
    nome: '',
    preco: 0,
    disponivel: true,
    qtdEstoque: 0,
    descricao: ''
  };

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return;

    this.empresaId = this.route.snapshot.paramMap.get('id');
    this.carregarProdutos();
  }

  carregarProdutos() {
    if (this.empresaId) {
      this.http.get(`http://localhost:8080/petsalus/api/produto/empresa/${this.empresaId}`)
        .subscribe({
          next: (res: any) => this.produtos = res,
          error: (err) => console.error('Erro ao carregar produtos:', err)
        });
    }
  }

  abrirModal() {
    this.modalAberta = true;
    this.novoProduto = {
      nome: '',
      preco: 0,
      disponivel: true,
      qtdEstoque: 0,
      descricao: ''
    };
  }

  addEstoque(produtoId: number) {
    const qtd = prompt('Quantidade a adicionar:', '1');
    const qtdNum = Number(qtd);
    if (!qtd || isNaN(qtdNum) || qtdNum <= 0) return;

    this.http.patch(
      `http://localhost:8080/petsalus/api/produto/add-estoque/${produtoId}?qtdAddEstoque=${qtdNum}`, {}
    ).subscribe(() => this.carregarProdutos());
  }

  disponibilizar(id: number) {
    this.http.patch(`http://localhost:8080/petsalus/api/produto/disponibilizar/${id}`, {})
      .subscribe(() => this.carregarProdutos());
  }

  indisponibilizar(id: number) {
    this.http.patch(`http://localhost:8080/petsalus/api/produto/indisponibilizar/${id}`, {})
      .subscribe(() => this.carregarProdutos());
  }

  excluirProduto(id: number) {
    if (!confirm('Excluir produto?')) return;
    this.http.delete(`http://localhost:8080/petsalus/api/produto/deletar/${id}`)
      .subscribe(() => this.carregarProdutos());
  }

  abrirEditar(p: any) {
    this.modoEdicao = true;
    this.produtoEditandoId = p.id;
    this.novoProduto = {
      nome: p.nome,
      preco: parseFloat(String(p.preco).replace(/[^\d.]/g, '')),
      disponivel: p.disponivel,
      qtdEstoque: p.qtdEstoque,
      descricao: p.descricao
    };
    this.modalAberta = true;
  }

  salvarProduto() {
    if (!this.empresaId) return;

    const url = this.modoEdicao
      ? `http://localhost:8080/petsalus/api/produto/editar/${this.produtoEditandoId}`
      : `http://localhost:8080/petsalus/api/produto/registrar/${this.empresaId}`;

    const request = this.modoEdicao
      ? this.http.put(url, this.novoProduto)
      : this.http.post(url, this.novoProduto);

    request.subscribe({
      next: () => {
        alert(this.modoEdicao ? 'Produto atualizado!' : 'Produto cadastrado!');
        this.modalAberta = false;
        this.modoEdicao = false;
        this.produtoEditandoId = null;
        this.carregarProdutos();
      },
      error: (err) => {
        console.error('Erro:', err);
        alert(err?.error?.message || 'Erro ao salvar.');
      }
    });
  }

  uploadFoto(produtoId: number) {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';

    input.onchange = () => {
      const file = input.files?.[0];
      if (!file) return;

      const formData = new FormData();
      formData.append('foto', file);

      this.http.post(
        `http://localhost:8080/petsalus/api/produto/upload-foto/${produtoId}`,
        formData,
        { responseType: 'text' }
      )
        .subscribe({
          next: () => {
            alert('Foto enviada com sucesso!');
            this.carregarProdutos();
          },
          error: (err) => {
            console.error('Erro ao fazer upload da foto:', err);
            alert(err?.error?.message || 'Erro ao enviar foto.');
          }
        });
    };

    input.click();
  }
}