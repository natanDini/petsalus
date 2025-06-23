import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    NgxMaskDirective,
    NgxMaskPipe
  ]
})
export class CadastroComponent {
  nome = '';
  email = '';
  senha = '';
  confirmarSenha = '';
  cpf = '';
  telefone = '';
  username = '';
  userRole = '';
  ufs = ['AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA',
    'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN',
    'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'];

  roles = ['TUTOR', 'DONO', 'VETERINARIO', 'COLABORADOR'];

  endereco = {
    cep: '',
    endereco: '',
    numero: '',
    bairro: '',
    cidade: '',
    estado: '',
    complemento: ''
  };

  constructor(private http: HttpClient, private router: Router) { }

  private formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, ''); // remove tudo que não for número
    return numeros.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  private formatarTelefone(telefone: string): string {
    const numeros = telefone.replace(/\D/g, '');
    return numeros.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
  }

  onSubmit() {
    if (this.senha !== this.confirmarSenha) {
      alert('As senhas não coincidem!');
      return;
    }

    const payload = {
      cpf: this.formatarCpf(this.cpf),
      nome: this.nome,
      email: this.email,
      senha: this.senha,
      username: this.username,
      telefone: this.formatarTelefone(this.telefone),
      userRole: this.userRole,
      endereco: this.endereco
    };

    this.http.post('http://localhost:8080/petsalus/api/user/registrar', payload).subscribe({
      next: () => {
        alert('Conta criada com sucesso!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(err);
        const mensagemErro = err?.error?.message || 'Erro ao criar conta.';
        alert(mensagemErro);
      }
    });
  }
}