import { Routes } from '@angular/router';
import { HomeComponent } from "./pages/home/home.component";
import { CadastroComponent } from "./pages/cadastro/cadastro.component";
import { LoginComponent } from './pages/login/login.component';
import { TutorHomeComponent } from './pages/tutor/tutor-home/tutor-home.component';
import { DonoHomeComponent } from './pages/dono/dono-home/dono-home.component';
import { VeterinarioHomeComponent } from './pages/veterinario/veterinario-home/veterinario-home.component';
import { ColaboradorHomeComponent } from './pages/colaborador/colaborador-home/colaborador-home.component';
import { EmpresaDonoComponent } from './pages/dono/empresa-dono/empresa-dono.component';
import { EmpresaEmpregadosComponent } from './pages/dono/empresa-empregados/empresa-empregados.component';
import { EmpresaProdutosComponent } from './pages/dono/empresa-produtos/empresa-produtos.component';
import { EmpresaComprasComponent } from './pages/dono/empresa-compras/empresa-compras.component';
import { EmpresaServicosComponent } from './pages/dono/empresa-servicos/empresa-servicos.component';
import { EmpresaColaboradorComponent } from './pages/colaborador/empresa-colaborador/empresa-colaborador.component';
import { ColaboradorProdutosComponent } from './pages/colaborador/colaborador-produtos/colaborador-produtos.component';
import { ColaboradorComprasComponent } from './pages/colaborador/colaborador-compras/colaborador-compras.component';
import { ColaboradorServicosComponent } from './pages/colaborador/colaborador-servicos/colaborador-servicos.component';
import { ColaboradorAgendamentosComponent } from './pages/colaborador/colaborador-agendamentos/colaborador-agendamentos.component';

export const routes: Routes = [

  // Compartilhado
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: CadastroComponent },

  // TUTOR
  { path: 'tutor', component: TutorHomeComponent },

  // DONO
  { path: 'dono', component: DonoHomeComponent },
  { path: 'dono/empresa/:id', component: EmpresaDonoComponent },
  { path: 'dono/empresa/empregados/:id', component: EmpresaEmpregadosComponent },
  { path: 'dono/empresa/produtos/:id', component: EmpresaProdutosComponent },
  { path: 'dono/empresa/compras/:id', component: EmpresaComprasComponent },
  { path: 'dono/empresa/servicos/:id', component: EmpresaServicosComponent },

  // VETERINARIO
  { path: 'veterinario', component: VeterinarioHomeComponent },

  // COLABORADOR 
  { path: 'colaborador', component: ColaboradorHomeComponent },
  { path: 'colaborador/empresa/:id', component: EmpresaColaboradorComponent },
  { path: 'colaborador/empresa/produtos/:id', component: ColaboradorProdutosComponent },
  { path: 'colaborador/empresa/compras/:id', component: ColaboradorComprasComponent },
  { path: 'colaborador/empresa/servicos/:id', component: ColaboradorServicosComponent },
  { path: 'colaborador/empresa/agendamentos/:id', component: ColaboradorAgendamentosComponent },
];