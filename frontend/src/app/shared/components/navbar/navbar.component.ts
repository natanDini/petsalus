import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  @Input() appName = 'PetSalus';
  @Input() color: 'primary' | 'accent' | 'warn' = 'primary';
  @Input() menuItems: { path: string, title: string, icon?: string }[] = [
    { path: '/', title: 'Home', icon: 'home' },
    { path: '/about', title: 'Sobre', icon: 'info' },
    { path: '/contact', title: 'Contato', icon: 'mail' }
  ];
}
