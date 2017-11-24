import { NgModule }             							from '@angular/core';
import { RouterModule, Routes } 							from '@angular/router';

import { AuthGuard }                   			from '../routing/auth-guard.service';
import { PaymentAuthGuard }                   	from '../routing/payment-auth-guard.service';

import { ActaCompletaComponent }                   from '../pages/acta-completa/acta-completa.component';

import { ConsultasComponent }                   from '../pages/consultas/consultas.component';
import { LoginComponent }                   	from '../pages/login/login.component';
import { HomeComponent }                   		from '../pages/home/home.component';

const routes: Routes = [
	{ path: 'login',  				component: LoginComponent, 				data: {title: 'Login'} },
	{ path: 'consultas',			component: ConsultasComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'acta-completa',			component: ActaCompletaComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard]},

	{ path: 'home',  				component: HomeComponent, 				data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: '**',  					redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
