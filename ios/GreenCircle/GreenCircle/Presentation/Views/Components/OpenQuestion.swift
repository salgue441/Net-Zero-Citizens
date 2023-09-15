//
//  OpenQuestion.swift
//  GreenCircle
//
//  Created by Dan FuPo on 13/09/23.
//

import SwiftUI

struct OpenQuestion: View {
  @State private var text = ""
  private let characterLimit = 250
  
  let question: SurveyQuestion
  
  var body: some View {
    VStack {
      Text(question.questionText)
      TextField("Refrigerador", text: $text, axis: .vertical)
        .lineLimit(5, reservesSpace: true)
        .textFieldStyle(.roundedBorder)
        .padding()
    }
  }
}
