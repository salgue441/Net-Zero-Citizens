//
//  FilterView.swift
//  GreenCircle
//
//  Created by Dan FuPo on 10/10/23.
//

import SwiftUI

struct FilterView: View {
  @StateObject var vm: CompanyViewModel
  
  var orderBy = ["Cercanía", "Rating"]
  var products = ["Paneles Solares", "Celdas Fotovoltaicas"]
  
  @Environment(\.dismiss) var dismiss
  
  var body: some View {
    VStack {
      Text("Filtros")
        .font(.largeTitle)
        .foregroundStyle(Color.gray)
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.bottom)
        
      PickerFormView(
        selectedOption: $vm.order,
        label: "Ordena por",
        options: orderBy)
      .foregroundColor(Color.gray)
      .padding(.bottom)
      
      PickerFormView(
        selectedOption: $vm.product,
        label: "Elige un producto",
        options: products)
      .foregroundStyle(Color.gray)
      .padding(.bottom)
      
      PickerFormView(
        selectedOption: $vm.state,
        label: "Estados de la república",
        options: Constants.states)
      .foregroundColor(Color.gray)
      .padding(.bottom)
      
      HStack(spacing: 10) {
        MainButton("Cancelar",
                   action: {})
        MainButton("Aplicar",
                   action: {
          Task {
            await vm.fetchFilteredCompanies()
          }
        })
      }.padding(.top)
    }.padding()
    
    Button("Cerrar") {
      dismiss()
    } .foregroundColor(/*@START_MENU_TOKEN@*/.blue/*@END_MENU_TOKEN@*/)
  }
}

#Preview {
  FilterView(vm: CompanyViewModel())
}
