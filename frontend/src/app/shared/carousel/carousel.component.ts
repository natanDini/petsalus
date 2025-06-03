import { Component, Input, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-material-carousel',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss']
})
export class MaterialCarouselComponent implements AfterViewInit {
  @Input() slides: any[] = [];
  @Input() autoPlay = false;
  @Input() interval = 3000;
  @Input() showControls = true;
  @Input() showIndicators = true;

  @ViewChild('carouselContainer') carouselContainer!: ElementRef;

  currentIndex = 0;
  private autoPlayInterval: any;

  ngAfterViewInit(): void {
    if (this.autoPlay) {
      this.startAutoPlay();
    }
  }

  ngOnDestroy(): void {
    this.stopAutoPlay();
  }

  goToSlide(index: number): void {
    this.currentIndex = index;
    this.scrollToCurrentSlide();
    this.resetAutoPlay();
  }

  nextSlide(): void {
    this.currentIndex = (this.currentIndex + 1) % this.slides.length;
    this.scrollToCurrentSlide();
    this.resetAutoPlay();
  }

  prevSlide(): void {
    this.currentIndex = (this.currentIndex - 1 + this.slides.length) % this.slides.length;
    this.scrollToCurrentSlide();
    this.resetAutoPlay();
  }

  private scrollToCurrentSlide(): void {
    const element = this.carouselContainer.nativeElement;
    const slideWidth = element.offsetWidth;
    element.scrollTo({
      left: this.currentIndex * slideWidth,
      behavior: 'smooth'
    });
  }

  private startAutoPlay(): void {
    this.autoPlayInterval = setInterval(() => {
      this.nextSlide();
    }, this.interval);
  }

  private stopAutoPlay(): void {
    if (this.autoPlayInterval) {
      clearInterval(this.autoPlayInterval);
    }
  }

  private resetAutoPlay(): void {
    if (this.autoPlay) {
      this.stopAutoPlay();
      this.startAutoPlay();
    }
  }
}
