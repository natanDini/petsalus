import { Routes } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {CadastroComponent} from "./pages/cadastro/cadastro.component";

export const routes: Routes = [
  {path:'', component: HomeComponent},
  {path:'cadastro', component: CadastroComponent}
];
