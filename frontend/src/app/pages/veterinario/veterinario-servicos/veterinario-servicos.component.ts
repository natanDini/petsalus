import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-veterinario-servicos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './veterinario-servicos.component.html',
  styleUrl: './veterinario-servicos.component.scss'
})
export class VeterinarioServicosComponent {
  empresaId: string | null = null;
  servicos: any[] = [];
  modalAberta: boolean = false;
  dropdownAberto: { [key: number]: boolean } = {};
  editandoServico: boolean = false;

  toggleDropdown(servicoId: number) {
    this.dropdownAberto[servicoId] = !this.dropdownAberto[servicoId];
  }

  novoServico: {
    id?: number;
    nome: string;
    preco: number;
    descricao: string;
    isPublico: boolean;
    tempoServicoMin: number;
    tipoServico: string;
  } = {
      nome: '',
      preco: 0,
      descricao: '',
      isPublico: false,
      tempoServicoMin: 0,
      tipoServico: 'PET_SHOP'
    };


  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit(): void {
    this.empresaId = this.route.snapshot.paramMap.get('id');
    if (this.empresaId) this.carregarServicos();
  }

  carregarServicos(): void {
    this.http.get<any[]>(`http://localhost:8080/petsalus/api/servico/${this.empresaId}`)
      .subscribe({
        next: (res) => this.servicos = res,
        error: (err) => console.error('Erro ao carregar serviços:', err)
      });
  }

  abrirModal(): void {
    this.modalAberta = true;
  }

  fecharModal(): void {
    this.modalAberta = false;
    this.editandoServico = false;
    this.novoServico = {
      nome: '',
      preco: 0,
      descricao: '',
      isPublico: false,
      tempoServicoMin: 0,
      tipoServico: 'PET_SHOP'
    };
  }

  confirmarExclusao(servicoId: number) {
    if (confirm('Tem certeza que deseja excluir este serviço?')) {
      this.http.delete(`http://localhost:8080/petsalus/api/servico/deletar/${servicoId}`)
        .subscribe({
          next: () => this.carregarServicos(),
          error: err => console.error('Erro ao excluir serviço:', err)
        });
    }
  }

  editarServico(servico: any) {
    this.novoServico = {
      ...servico,
      isPublico: servico.isPublico ?? false  // força boolean
    };
    this.editandoServico = true;
    this.abrirModal();
  }


  salvarServico() {
    if (!this.empresaId) return;

    const metodo = this.editandoServico ? 'put' : 'post';
    const url = this.editandoServico
      ? `http://localhost:8080/petsalus/api/servico/editar/${this.novoServico.id}`
      : `http://localhost:8080/petsalus/api/servico/${this.empresaId}/registrar`;

    this.http[metodo](url, this.novoServico)
      .subscribe({
        next: () => {
          this.fecharModal();
          this.carregarServicos();
        },
        error: err => console.error('Erro ao salvar serviço:', err)
      });
  }

  modalResponsaveisAberta = false;
  responsaveis: any[] = [];
  candidatos: any[] = [];
  empregadoSelecionadoId: number | null = null;
  servicoEmFocoId!: number;
  servicoEmFocoTipo!: string;

  /** abre a modal — agora carrega responsáveis *e* candidatos */
  abrirModalResponsaveis(servicoId: number): void {
    // 1- guarda info do serviço em foco
    this.servicoEmFocoId = servicoId;
    this.servicoEmFocoTipo = this.servicos.find(s => s.id === servicoId)?.tipoServico ?? '';

    // 2- responsáveis já vinculados
    const urlResp = `http://localhost:8080/petsalus/api/empresa-empregado/empregados/servico/${servicoId}`;
    // 3- possíveis candidatos
    const urlCand = this.servicoEmFocoTipo === 'Serviço Clínico/Médico'
      ? `http://localhost:8080/petsalus/api/empresa-empregado/veterinarios/${this.empresaId}`
      : `http://localhost:8080/petsalus/api/empresa-empregado/colaboradores/${this.empresaId}`;

    // dispara ambas as requisições em paralelo
    Promise.all([
      this.http.get<any[]>(urlResp).toPromise(),
      this.http.get<any[]>(urlCand).toPromise()
    ])
      .then(([resp, cand]) => {
        this.responsaveis = resp ?? [];
        this.candidatos = cand ?? [];
        this.empregadoSelecionadoId = null;
        this.modalResponsaveisAberta = true;
      })
      .catch(err => {
        console.error('Erro ao buscar responsáveis / candidatos:', err);
        this.responsaveis = [];
        this.candidatos = [];
        this.modalResponsaveisAberta = true;
      });
  }

  fecharModalResponsaveis(): void {
    this.modalResponsaveisAberta = false;
    this.responsaveis = this.candidatos = [];
    this.empregadoSelecionadoId = null;
  }

  /** vincula o selecionado ao serviço em foco */
  vincularResponsavel(): void {
    if (!this.empregadoSelecionadoId) return;

    // ajuste a URL se o seu backend usar outro path 👇
    const url = `http://localhost:8080/petsalus/api/empresa-empregado/servico/`
      + `${this.empregadoSelecionadoId}/${this.servicoEmFocoId}`;

    this.http.post(url, {}).subscribe({
      next: () => this.abrirModalResponsaveis(this.servicoEmFocoId), // recarrega listas
      error: err => console.error('Erro ao vincular responsável:', err)
    });
  }

  /** desvincula um responsável já listado */
  desvincularResponsavel(empregadoId: number): void {
    if (!confirm('Remover este responsável do serviço?')) return;

    const url = `http://localhost:8080/petsalus/api/empresa-empregado/servico/deletar`
      + `/${this.servicoEmFocoId}/${empregadoId}`;

    this.http.delete(url).subscribe({
      next: () => this.abrirModalResponsaveis(this.servicoEmFocoId), // recarrega listas
      error: err => console.error('Erro ao desvincular responsável:', err)
    });
  }
}