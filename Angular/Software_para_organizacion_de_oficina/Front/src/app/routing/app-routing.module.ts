import { NgModule }             							from '@angular/core';
import { RouterModule, Routes } 							from '@angular/router';

import { HomeComponent }                   		from '../pages/home/home.component';
import { RegisterComponent }                   	from '../pages/register/register.component';
import { RegisterWorkComponent }               	from '../pages/register-work/register-work.component';
import { RegisterPaymentComponent }             from '../pages/register-payment/register-payment.component';
import { MoreDetailsComponent }             	from '../pages/more-details/more-details.component';


const routes: Routes = [
	{ path: 'register',  		component: RegisterComponent, 			data: {title: 'Home'} },
	{ path: 'register-work',  	component: RegisterWorkComponent, 		data: {title: 'Home'} },
	{ path: 'register-payment',	component: RegisterPaymentComponent, 	data: {title: 'Home'} },
	{ path: 'more-details',		component: MoreDetailsComponent, 		data: {title: 'Home'} },
	{ path: 'home',  			component: HomeComponent, 				data: {title: 'Home'} },
	{ path: '**',  				redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
