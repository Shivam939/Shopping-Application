import { Component, inject, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { CommonModule } from '@angular/common'; // 👈 add this

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule], // 👈 include CommonModule here
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'] // 👈 fix: should be `styleUrls`, not `styleUrl`
})
export class HeaderComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  isAuthenticated = false;
  username = "";

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({ isAuthenticated }) => {
      this.isAuthenticated = isAuthenticated;
    });

    this.oidcSecurityService.userData$.subscribe(({ userData }) => {
      this.username = userData?.preferred_username || '';
    });
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff().subscribe((result) => console.log(result));
  }

}
