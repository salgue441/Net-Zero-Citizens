//
//  Tutorial.swift
//  GreenCircle
//
//  Created by Dan FuPo on 06/10/23.
//

import SwiftUI

struct Tutorial: View {
  
  private let tutoImgs = ["foto1", "foto2", "foto3", "foto4", "foto5"]
  
  var body: some View {
    NavigationView {
      
      VStack(alignment: .leading) {
        Text("¿Cómo uso la App?").font(.title)
          .padding(.leading)
          .padding(.top, 30)
        ScrollView(.horizontal) {
          LazyHStack(spacing: 0) {
            ForEach(tutoImgs.indices, id: \.self) { index in
              let tutorial = tutoImgs[index]
              VStack {
                Text(tutorial).font(.headline)
                Image(tutorial)
                  .resizable()
                  .scaledToFit()
                  .frame(height: 450)
                  .clipShape(RoundedRectangle(cornerRadius: /*@START_MENU_TOKEN@*/25.0/*@END_MENU_TOKEN@*/))
                  .padding(.horizontal, 20)
              }
            }
          } .padding(.bottom, 100)
        }
      }
    } .navigationBarTitle("Tutoriales")
      .navigationBarTitleDisplayMode(.inline)
  }
}

#Preview {
  Tutorial()
}
