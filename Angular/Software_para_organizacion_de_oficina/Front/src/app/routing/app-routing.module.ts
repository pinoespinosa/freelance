import { NgModule }             							from '@angular/core';
import { RouterModule, Routes } 							from '@angular/router';

import { HomeComponent }                   		from '../pages/home/home.component';
import { RegisterComponent }                   	from '../pages/register/register.component';


const routes: Routes = [
	{ path: 'register',  		component: RegisterComponent, 		data: {title: 'Home'} },
	{ path: 'home',  			component: HomeComponent, 			data: {title: 'Home'} },
	{ path: '**',  				redirectTo: '/home'}


];

@NgModule({
	imports: [ RouterModule.forRoot(routes)],
	exports: [ RouterModule ]
})

export class AppRoutingModule {}
