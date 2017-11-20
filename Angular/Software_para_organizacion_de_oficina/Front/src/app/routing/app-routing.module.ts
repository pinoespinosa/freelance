import { NgModule }             							from '@angular/core';
import { RouterModule, Routes } 							from '@angular/router';

import { AuthGuard }                   			from '../routing/auth-guard.service';
import { PaymentAuthGuard }                   			from '../routing/payment-auth-guard.service';

import { LoginComponent }                   	from '../pages/login/login.component';
import { HomeComponent }                   		from '../pages/home/home.component';
import { RegisterUserComponent }                from '../pages/register/register-user.component';

import { RegisterComponent }                   	from '../pages/register/register.component';
import { RegisterWorkComponent }               	from '../pages/register-work/register-work.component';
import { RegisterPaymentComponent }             from '../pages/register-payment/register-payment.component';
import { MoreDetailsComponent }             	from '../pages/more-details/more-details.component';
import { ChangeDateComponent }             		from '../pages/change-date/change-date.component';
import { ReportsToDoComponent }             	from '../pages/reports/to-do/to-do.component';
import { ReportsIncomesComponent }             	from '../pages/reports/incomes/incomes.component';
import { ReportsNewClientsComponent }           from '../pages/reports/new-clients/new-clients.component';
import { ReportsOldClientsComponent }           from '../pages/reports/old-clients/old-clients.component';
import { ReportsIndicatorsComponent }           from '../pages/reports/indicators/indicators.component';

const routes: Routes = [
	{ path: 'login',  				component: LoginComponent, 				data: {title: 'Login'} },
	{ path: 'register',  			component: RegisterComponent, 			data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'register-work',  		component: RegisterWorkComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'register-user',  		component: RegisterUserComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'register-payment',		component: RegisterPaymentComponent, 	data: {title: 'Home'}, canActivate: [PaymentAuthGuard]},
	{ path: 'more-details',			component: MoreDetailsComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'change-date',			component: ChangeDateComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'home',  				component: HomeComponent, 				data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reports/to-do',  		component: ReportsToDoComponent, 		data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reports/incomes',  	component: ReportsIncomesComponent, 	data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reports/new-clients',  component: ReportsNewClientsComponent, 	data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reports/old-clients',  component: ReportsOldClientsComponent, 	data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: 'reports/indicators',  	component: ReportsIndicatorsComponent, 	data: {title: 'Home'}, canActivate: [AuthGuard]},
	{ path: '**',  					redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
