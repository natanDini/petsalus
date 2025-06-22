import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideClientHydration } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { routes } from './app.routes';
import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideNgxMask } from 'ngx-mask';

export const appConfig: ApplicationConfig = {
  providers: [
    provideNgxMask(),
    provideRouter(routes),
    provideClientHydration(),
    provideAnimationsAsync(),
    provideHttpClient(
      withFetch(), // ✅ Adicionado
      withInterceptors([
        (req, next) => {
          const platformId = inject(PLATFORM_ID);
          if (isPlatformBrowser(platformId)) {
            const token = localStorage.getItem('access_token');
            if (token) {
              req = req.clone({
                setHeaders: {
                  Authorization: `Bearer ${token}`
                }
              });
            }
          }
          return next(req);
        }
      ])
    )
  ]
};