//
//  CardView.swift
//  catalogo
//
//  Created by Diego Iturbe on 18/09/23.
//  Modified by Daniel Gutiérrez Gómez 26/09/23

import SwiftUI

struct CardCatalogView: View {
  @StateObject var viewModel: CompanyViewModel
  @StateObject var favouriteViewModel: FavouriteViewModel
  @State var emptyHeartFill: Bool = false
  @State private var showAlert = false
  @State private var messageAlert = ""
  @State private var deleteOperation = false
  var companyId: UUID
  var companyName: String
  var city: String
  var state: String
  
  init(companyId: UUID,
       companyName: String,
       city: String,
       state: String) {
    
    _viewModel = StateObject(wrappedValue: CompanyViewModel())
    _favouriteViewModel = StateObject(wrappedValue: FavouriteViewModel())
    self.companyId = companyId
    self.companyName = companyName
    self.city = city
    self.state = state
  }
  
  var body: some View {
    NavigationLink(destination: ContactCompanyView(idCompany: companyId, favouriteViewModel: favouriteViewModel, emptyHeartFill: $emptyHeartFill)){
      ZStack {
        RoundedRectangle(cornerRadius: 10, style:.continuous)
          .fill(.white)
          .frame(width: 335, height: 150)
          .shadow(color: Color("BlueCustom"), radius: 1)
        HStack {
          VStack (alignment: .leading) {
            if let imageURL = URL(string: viewModel.contentCompany.files?.first?.fileUrl ?? "") {
              AsyncImage(url: imageURL) { phase in
                switch phase {
                  case .empty:
                    LoadingScreenView().frame(width: 100, height: 100)
                  case .success(let image):
                    image
                      .resizable()
                      .scaledToFit()
                      .cornerRadius(10, corners: [.bottomLeft, .bottomRight, .topLeft, .topRight])
                      .frame(width: 100, height: 100)
                  case .failure:
                    Text("Failed to load Image!!")
                  @unknown default:
                    fatalError()
                }
              }
            } else {
              LoadingScreenView().frame(width: 100, height: 100)
            }
          }
          Spacer()
          VStack(alignment: .leading, spacing: 7) {
            HStack(alignment: .top) {
              Text(companyName)
                .font(.system(size: 17))
                .lineLimit(2)
                .foregroundColor(Color("MainText"))
                .fontWeight(.bold)
            }
            HStack {
              Image(systemName: "location.fill")
                .foregroundColor(Color("BlueCustom"))
              Text("\(city), \(state)")
                .font(.system(size: 13))
                .lineSpacing(2)
            }.foregroundColor(Color("MainText"))
              .padding(.bottom, 3)
            
            HStack {
              if Int(viewModel.contentCompany.score!) > 0 {
                ForEach(0..<5, id: \.self) { index in
                  Image(systemName: index < Int(viewModel.contentCompany.score!) ? "star.fill" : "star")
                }.foregroundColor(Color("GreenCustom"))
                Text("\(Int(viewModel.contentCompany.score!))")
              } else {
                Text("No hay rating").foregroundColor(Color("GreenCustom"))
              }
            }.font(.system(size: 13))
          }
          .frame(maxWidth: 180, maxHeight: 120)
          .multilineTextAlignment(.leading)
          Spacer()
          VStack {
            Button(action: {
              Task {
                if favouriteViewModel.existsFavourite(companyId: companyId) && emptyHeartFill {
                    emptyHeartFill = true
                    deleteOperation = true
                    showAlert = true
                    messageAlert = "¿Eliminar a: " + companyName + " de tus favoritos?"
                } else if !favouriteViewModel.existsFavourite(companyId: companyId) {
                    showAlert = true
                    await favouriteViewModel.postFavouriteById(companyId: companyId)
                    if favouriteViewModel.contentFavourite.message ==
                        "Favourite created" {
                      messageAlert = "Se ha agregado a: " + companyName + " a tus favoritos!"
                      emptyHeartFill = true
                      deleteOperation = false
                    }
                  }
              }
            }, label: {
              Image(systemName: emptyHeartFill ? "heart.fill" : "heart")
                .foregroundColor(Color("BlueCustom"))
                .font(.system(size: 24))
                .padding(.top, 20)
            })
            .alert(isPresented: $showAlert) {
              if !deleteOperation {
                return Alert(title: Text("Éxito"), message: Text(messageAlert))
              }
              else {
                return Alert(title: Text("Confirmar borrar favoritos"), message: Text(messageAlert),
                   primaryButton: .destructive(Text("Borrar")) {
                  Task {
                    emptyHeartFill = false
                    try await favouriteViewModel.deleteFavouriteById( companyId: companyId)
                  }
                   },
                   secondaryButton: .cancel())
              }
            }
              Spacer()
            }.frame(maxWidth: 25)
          }
          .frame(maxWidth: 300, maxHeight: 140)
        }
      }.onAppear {
        
        Task {
        
          await viewModel.fetchCompanyById(idCompany: companyId)
          if favouriteViewModel.existsFavourite(companyId: companyId) {
            emptyHeartFill = true
          } else {
            emptyHeartFill = false
          }
        }
      }
      .navigationTitle("Proveedores")
      .navigationBarTitleDisplayMode(.inline)
    }
  }

struct CatalogView: View {
  @StateObject var viewModel = CompanyViewModel()

  var body: some View {
    ZStack {
      NavigationStack {
        ScrollView {
          LazyVStack{
            ForEach(viewModel.companies, id: \.id) { company in
              CardCatalogView(companyId: company.companyId,
                              companyName: company.name, city: company.city,
                              state: company.state)
            }.padding(.top, 5)
          }.padding(.top, 10)
        }
        .onAppear {
          Task {
            try await viewModel.fetchAllCompanies()
          }
        }
      }
      .accentColor(.white)
    }
  }
}
    


