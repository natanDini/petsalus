import { Component } from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatCardTitle} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";


@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  standalone: true,
  styleUrls: ['./cadastro.component.scss'],
  imports: [CommonModule, MatIcon, FormsModule, MatFormField, MatCardTitle, MatLabel, MatFormFieldModule, MatInputModule, MatButton, RouterLink]
})
export class CadastroComponent {
  nome: string = '';
  email: string = '';
  senha: string = '';
  confirmarSenha: string = '';

  onSubmit() {
    if (this.senha !== this.confirmarSenha) {
      alert('As senhas não coincidem!');
      return;
    }

    // Aqui você pode chamar um serviço para cadastrar o usuário
    console.log('Cadastro enviado:', {
      nome: this.nome,
      email: this.email,
      senha: this.senha
    });

    alert('Conta criada com sucesso!');
  }
}
