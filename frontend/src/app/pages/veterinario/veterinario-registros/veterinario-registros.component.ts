import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgxMaskDirective } from 'ngx-mask';

interface PetBasic {
  id: number;
  nome: string;
  fotoPerfil: string | null;
}

interface PetResumo {
  id: number;
  peso: number;
  nome: string;
  idade: number;
  sexo: string;
  raca: string;
  especie: string;
  fotoPerfil: string | null;
}

interface RegistroMedico {
  empresa: string;
  empresaCnpj: string;
  veterinario: string;
  dataHora: string;
  tituloServico: string;
  descricao: string;
  tipoServico: string;
  empresaFotoPerfil: string | null;
}

interface Servico {
  id: number;
  nome: string;
}

@Component({
  selector: 'app-veterinario-registros',
  standalone: true,
  templateUrl: './veterinario-registros.component.html',
  styleUrls: ['./veterinario-registros.component.scss'],
  imports: [CommonModule, FormsModule, NgxMaskDirective]
})
export class VeterinarioRegistrosComponent implements OnInit {

  cpf = '';
  pets: PetBasic[] = [];
  carregandoPets = false;

  petSelecionado?: PetResumo;
  registros: RegistroMedico[] = [];

  // —— modal “Cadastro de Emergência”
  modalAberta = false;
  servicos: Servico[] = [];
  novoRegistro = {
    servicoId: null as number | null,
    descricao: '',
    dataHora: ''
  };

  private empresaId: number;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute
  ) {
    this.empresaId = Number(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void { }

  /* ---------- Pets ---------- */

  buscarPets(): void {
    if (!this.cpf) { return; }
    const cpfFormatado = this.formatarCpf(this.cpf); // aplica pontos e hífen
    this.carregandoPets = true;
    this.http
      .get<PetBasic[]>(`http://localhost:8080/petsalus/api/pet/cpf/${cpfFormatado}`)
      .subscribe({
        next: data => this.pets = data,
        error: err => console.error('Erro ao buscar pets', err),
        complete: () => this.carregandoPets = false
      });
  }

  private formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, ''); // remove tudo que não for número
    return numeros.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  selecionarPet(pet: PetBasic): void {
    this.http
      .get<PetResumo>(`http://localhost:8080/petsalus/api/pet/${pet.id}`)
      .subscribe({
        next: dados => {
          this.petSelecionado = dados;
          this.carregarRegistros(dados.id);
        },
        error: err => console.error('Erro ao buscar detalhes do pet', err)
      });
  }

  /* ---------- Registros Médicos ---------- */

  private carregarRegistros(petId: number): void {
    this.http
      .get<RegistroMedico[]>(`http://localhost:8080/petsalus/api/registro-medico/pet/${petId}`)
      .subscribe({
        next: data => this.registros = data,
        error: err => console.error('Erro ao buscar registros médicos', err)
      });
  }

  /* ---------- Cadastro de Emergência ---------- */

  abrirModal(): void {
    this.modalAberta = true;
    this.novoRegistro = { servicoId: null, descricao: '', dataHora: '' };

    // carrega opções de serviços dos veterinários da empresa
    this.http
      .get<Servico[]>(`http://localhost:8080/petsalus/api/servico/veterinarios/${this.empresaId}`)
      .subscribe({
        next: s => this.servicos = s,
        error: err => console.error('Erro ao buscar serviços', err)
      });
  }

  fecharModal(): void {
    this.modalAberta = false;
  }

  salvarRegistro(): void {
    if (!this.petSelecionado || !this.novoRegistro.servicoId) { return; }

    const body = {
      petId: this.petSelecionado.id,
      servicoId: this.novoRegistro.servicoId,
      descricao: this.novoRegistro.descricao,
      dataHora: this.novoRegistro.dataHora.replace('T', ' ')
    };

    this.http
      .post('http://localhost:8080/petsalus/api/registro-medico/registrar', body)
      .subscribe({
        next: () => {
          this.fecharModal();
          this.carregarRegistros(this.petSelecionado!.id); // recarrega lista
        },
        error: err => console.error('Erro ao salvar registro', err)
      });
  }
}