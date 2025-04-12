// export interface Order {
//   id?: number;
//   orderNumber?: string;
//   skuCode: string;
//   price: number;
//   quantity: number;
//   userDetails: UserDetails
// }
//
// export interface UserDetails {
//   email: string;
//   firstName: string;
//   lastName: string;
// }

// src/app/model/order.ts

export interface Order {
  skuCode: string;
  price: string;
  quantity: number;
}
