import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {
  perfil: any = null;
  modalAberta = false;
  novoPerfil: any = {};

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return; // impede erro no SSR
    this.carregarPerfil();
  }

  private formatarTelefone(telefone: string): string {
    const numeros = telefone.replace(/\D/g, '');
    return numeros.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
  }

  carregarPerfil() {
    const token = localStorage.getItem('access_token');
    if (!token) {
      window.location.href = '/login';
      return;
    }

    const headers = { Authorization: `Bearer ${token}` };

    this.http.get('http://localhost:8080/petsalus/api/user/perfil-completo', { headers })
      .subscribe({
        next: (data: any) => {
          this.perfil = data;
          this.novoPerfil = JSON.parse(JSON.stringify(data));
        },
        error: (err) => {
          console.error(err);
          if (err.status === 401 || err.status === 403) {
            localStorage.clear();
            window.location.href = '/login';
          }
        }
      });
  }

  salvarEdicao() {
    const token = localStorage.getItem('access_token');
    if (!token) {
      window.location.href = '/login';
      return;
    }

    const headers = {
      Authorization: `Bearer ${token}`
    };

    const body = {
      nome: this.novoPerfil.nome,
      email: this.novoPerfil.email,
      telefone: this.formatarTelefone(this.novoPerfil.telefone),
      endereco: this.novoPerfil.endereco
    };

    this.http.put('http://localhost:8080/petsalus/api/user/editar', body, { headers })
      .subscribe({
        next: () => {
          this.modalAberta = false;
          this.carregarPerfil();
        },
        error: (err) => {
          console.error('Erro ao salvar edição:', err);
          if (err.status === 401 || err.status === 403) {
            localStorage.clear();
            window.location.href = '/login';
          }
        }
      });
  }

  trocarFoto(event: any) {
    const token = localStorage.getItem('access_token');
    if (!token) {
      window.location.href = '/login';
      return;
    }

    const headers = {
      Authorization: `Bearer ${token}`
    };

    const formData = new FormData();
    formData.append('foto', event.target.files[0]);

    this.http.post('http://localhost:8080/petsalus/api/user/upload-foto', formData, {
      headers,
      responseType: 'text' // <-- ESSENCIAL
    }).subscribe({
      next: () => this.carregarPerfil(),
      error: (err) => console.error('Erro ao trocar foto:', err)
    });
  }

  excluirConta() {
    if (!confirm('Tem certeza que deseja excluir sua conta?')) return;

    const token = localStorage.getItem('access_token');
    if (!token) {
      window.location.href = '/login';
      return;
    }

    const headers = {
      Authorization: `Bearer ${token}`
    };

    this.http.delete('http://localhost:8080/petsalus/api/user/deletar', { headers })
      .subscribe({
        next: () => {
          alert('Conta excluída com sucesso!');
          localStorage.clear();
          window.location.href = '/';
        },
        error: (err) => {
          console.error('Erro ao excluir conta:', err);
          if (err.status === 401 || err.status === 403) {
            localStorage.clear();
            window.location.href = '/login';
          }
        }
      });
  }
}