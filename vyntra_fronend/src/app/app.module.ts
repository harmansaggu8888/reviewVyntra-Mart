import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { ShopComponent } from './shop/shop.component';
import { RegisterComponent } from './register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AdminComponent } from './admin/admin.component';
import { CartComponent } from './cart/cart.component';
import { PaymentComponent } from './payment/payment.component';
import { AddressComponent } from './address/address.component';
import { TrackOrderComponent } from './track-order/track-order.component';







@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutusComponent,
    ShopComponent,
    RegisterComponent,
    AdminComponent,
    CartComponent,
    PaymentComponent,
    AddressComponent,
    TrackOrderComponent
  ],
  imports: [
    BrowserModule,
 
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
