import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-empresa-empregados',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxMaskDirective],
  templateUrl: './empresa-empregados.component.html',
  styleUrls: ['./empresa-empregados.component.scss']
})
export class EmpresaEmpregadosComponent implements OnInit {
  empregados: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  funcionario: any = {
    cpf: '',
    nome: '',
    email: '',
    senha: '',
    username: '',
    telefone: '',
    userRole: 'COLABORADOR',
    endereco: {
      cep: '',
      bairro: '',
      cidade: '',
      numero: '',
      estado: '',
      endereco: '',
      complemento: ''
    }
  };

  ufs: string[] = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO',
    'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI',
    'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
  ];

  mostrarMenu = false;
  modalNovo = false;
  empresaId: string | null = null;
  menuAberto: number | null = null;

  toggleMenuEmpregado(empregadoId: number) {
    this.menuAberto = this.menuAberto === empregadoId ? null : empregadoId;
  }

  toggleMenu() {
    this.mostrarMenu = !this.mostrarMenu;
  }

  abrirCadastroNovo() {
    this.mostrarMenu = false;
    this.modalNovo = true;
  }

  abrirVincularExistente() {
    this.mostrarMenu = false;
    const cpf = prompt('Digite o CPF do funcionário a vincular:');
    if (cpf && this.empresaId) {
      this.http.post(`http://localhost:8080/petsalus/api/empresa-empregado/${this.empresaId}?cpf=${cpf}`, {})
        .subscribe({
          next: () => {
            alert('Funcionário vinculado!')
            this.carregarEmpregados();
          },
          error: (err) => {
            console.error('Erro ao vincular funcionário:', err);
            alert(`Erro ao vincular funcionário: ${err?.error?.message || 'Verifique o console para detalhes.'}`);
          }
        });
    }
  }

  confirmarRemocao(empregadoId: number) {
    const confirmado = confirm('Deseja realmente retirar este empregado da equipe?');
    if (confirmado && this.empresaId) {
      this.http.delete(`http://localhost:8080/petsalus/api/empresa-empregado/deletar/${this.empresaId}/${empregadoId}`)
        .subscribe({
          next: () => {
            alert('Funcionário removido com sucesso!');
            this.carregarEmpregados();
          },
          error: (err) => {
            console.error('Erro ao remover funcionário:', err);
            alert('Erro ao remover funcionário.');
          }
        });
    }
  }

  salvarNovoFuncionario() {
    this.http.post(`http://localhost:8080/petsalus/api/user/registrar/${this.empresaId}`, this.funcionario)
      .subscribe({
        next: () => {
          alert('Funcionário cadastrado com sucesso!');
          this.modalNovo = false;
          this.funcionario = { ...this.funcionario, cpf: '', nome: '', email: '', senha: '', username: '', telefone: '' };
          this.carregarEmpregados();
        },
        error: (err) => {
          console.error('Erro ao cadastrar funcionário:', err);
          alert(`Erro ao cadastrar funcionário: ${err?.error?.message || 'Verifique o console para detalhes.'}`);
        }
      });
  }

  carregarEmpregados() {
    if (!this.empresaId) return;

    this.http.get(`http://localhost:8080/petsalus/api/empresa-empregado/empregados/${this.empresaId}`)
      .subscribe({
        next: (res: any) => this.empregados = res,
        error: (err) => console.error('Erro ao buscar empregados:', err)
      });
  }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return;

    this.empresaId = this.route.snapshot.paramMap.get('id');
    this.carregarEmpregados();
  }
}