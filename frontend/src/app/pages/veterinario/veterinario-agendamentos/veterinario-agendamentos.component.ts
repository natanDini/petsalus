import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-veterinario-agendamentos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './veterinario-agendamentos.component.html',
  styleUrl: './veterinario-agendamentos.component.scss'
})
export class VeterinarioAgendamentosComponent {
  agendamentos: any[] = [];
  dataInput: string = ''; // usado no input type="date"
  dropdownAberto: { [id: number]: boolean } = {};

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object // ✅ INJETAR
  ) { }

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return; // ✅ BLOQUEIA SSR/PRELOAD

    const hoje = new Date();
    this.dataInput = this.formatarDataInput(hoje);
    const dataFormatada = this.formatarDataAPI(hoje);
    this.buscarAgendamentos(dataFormatada);
  }

  toggleDropdown(id: number): void {
    this.dropdownAberto[id] = !this.dropdownAberto[id];
  }

  efetivarAgendamento(id: number): void {
    const confirmar = window.confirm('Tem certeza que deseja EFETIVAR este agendamento?');
    if (!confirmar) return;

    this.http.patch(`http://localhost:8080/petsalus/api/servico_agenda/efetivar/${id}`, null)
      .subscribe({
        next: () => this.recarregar(),
        error: (err) => console.error('Erro ao efetivar agendamento', err)
      });
  }

  cancelarAgendamento(id: number): void {
    const confirmar = window.confirm('Tem certeza que deseja CANCELAR este agendamento?');
    if (!confirmar) return;

    this.http.patch(`http://localhost:8080/petsalus/api/servico_agenda/cancelar/${id}`, null)
      .subscribe({
        next: () => this.recarregar(),
        error: (err) => console.error('Erro ao cancelar agendamento', err)
      });
  }

  recarregar(): void {
    const partes = this.dataInput.split('-');
    const dataAPI = `${partes[2]}/${partes[1]}/${partes[0]}`;
    this.buscarAgendamentos(dataAPI);
  }

  buscarPorData(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.dataInput = valor;
    const partes = valor.split('-');
    const dataAPI = `${partes[2]}/${partes[1]}/${partes[0]}`; // yyyy-mm-dd -> dd/MM/yyyy
    this.buscarAgendamentos(dataAPI);
  }

  buscarAgendamentos(dataFormatada: string) {
    this.http.get<any[]>(`http://localhost:8080/petsalus/api/empresa-empregado/agenda?data=${dataFormatada}`)
      .subscribe({
        next: (res) => this.agendamentos = res,
        error: (err) => console.error('Erro ao buscar agendamentos', err)
      });
  }

  private formatarDataAPI(data: Date): string {
    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const ano = data.getFullYear();
    return `${dia}/${mes}/${ano}`;
  }

  private formatarDataInput(data: Date): string {
    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const ano = data.getFullYear();
    return `${ano}-${mes}-${dia}`; // formato para <input type="date">
  }
}
