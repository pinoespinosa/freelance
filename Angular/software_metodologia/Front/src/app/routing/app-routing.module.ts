import { NgModule }             				from '@angular/core';
import { RouterModule, Routes } 				from '@angular/router';

import { AuthGuard }                   			from '../routing/auth-guard.service';
import { CanDeactivateGuard }               	from '../routing/CanDeactivateGuard';

import { PaymentAuthGuard }                   	from '../routing/payment-auth-guard.service';

import { ActaCompletaComponent }                from '../pages/acta-completa/acta-completa.component';

import { ConsultasComponent }                   from '../pages/consultas/consultas.component';
import { ReunionesComponent }                   from '../pages/reuniones/reuniones.component';
import { SesionComponent }                   	from '../pages/sesion/sesion.component';
import { Sesion2Component }                   	from '../pages/sesion-2/sesion-2.component';

import { CreateUserComponent }              	from '../pages/create-user/create-user.component';
import { MantenimientoComponent }               from '../pages/mantenimiento/mantenimiento.component';

import { LoginComponent }                   	from '../pages/login/login.component';
import { HomeComponent }                   		from '../pages/home/home.component';



const routes: Routes = [
	{ path: 'login',  				component: LoginComponent, 				data: {title: 'Login'} },
	{ path: 'consultas',			component: ConsultasComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reuniones',			component: ReunionesComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'sesion',				component: SesionComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard] },
	{ path: 'sesion-2',				component: Sesion2Component, 			data: {title: 'Home'}, canActivate: [AuthGuard] },

	{ path: 'create-user',			component: CreateUserComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard] },
	{ path: 'mantenimiento',		component: MantenimientoComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard] },

	{ path: 'acta-completa',		component: ActaCompletaComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},

	{ path: 'home',  				component: HomeComponent, 				data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: '**',  					redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
export const APP_ROUTER_PROVIDERS = [
    CanDeactivateGuard,
    AuthGuard
];