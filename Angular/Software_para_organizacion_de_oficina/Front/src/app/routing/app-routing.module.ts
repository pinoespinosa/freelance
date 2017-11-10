import { NgModule }             							from '@angular/core';
import { RouterModule, Routes } 							from '@angular/router';

import { HomeComponent }                   		from '../pages/home/home.component';
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
	{ path: 'register',  			component: RegisterComponent, 			data: {title: 'Home'} },
	{ path: 'register-work',  		component: RegisterWorkComponent, 		data: {title: 'Home'} },
	{ path: 'register-payment',		component: RegisterPaymentComponent, 	data: {title: 'Home'} },
	{ path: 'more-details',			component: MoreDetailsComponent, 		data: {title: 'Home'} },
	{ path: 'change-date',			component: ChangeDateComponent, 		data: {title: 'Home'} },
	{ path: 'home',  				component: HomeComponent, 				data: {title: 'Home'} },
	{ path: 'reports/to-do',  		component: ReportsToDoComponent, 		data: {title: 'Home'} },
	{ path: 'reports/incomes',  	component: ReportsIncomesComponent, 	data: {title: 'Home'} },
	{ path: 'reports/new-clients',  component: ReportsNewClientsComponent, 	data: {title: 'Home'} },
	{ path: 'reports/old-clients',  component: ReportsOldClientsComponent, 	data: {title: 'Home'} },
	{ path: 'reports/indicators',  	component: ReportsIndicatorsComponent, 	data: {title: 'Home'} },
	{ path: '**',  				redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
