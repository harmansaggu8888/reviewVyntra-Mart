import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AboutusComponent} from './aboutus/aboutus.component';
import { RegisterComponent } from './register/register.component';
import { AdminComponent } from './admin/admin.component';
import { CartComponent } from './cart/cart.component';
import { PaymentComponent } from './payment/payment.component';
import { AddressComponent } from './address/address.component';
import { TrackOrderComponent } from './track-order/track-order.component';



const routes: Routes = [
{ path: '', redirectTo: '/Home', pathMatch: 'full' },
{ path: 'Home', component: HomeComponent },
{ path: 'AboutUs', component: AboutusComponent },
{ path: 'register_user', component: RegisterComponent },
{ path: 'admin', component: AdminComponent },
{ path: 'cart', component: CartComponent },
{ path: 'payment', component: PaymentComponent },
{ path: 'address', component: AddressComponent },
{ path: 'track_order', component: TrackOrderComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
