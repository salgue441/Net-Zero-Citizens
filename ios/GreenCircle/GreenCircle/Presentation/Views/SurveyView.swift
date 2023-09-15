//
//  SurveyView.swift
//  GreenCircle
//
//  Created by Dan FuPo on 13/09/23.
//

import SwiftUI

struct SurveyView: View {
  let survey: SurveyModel
  
  var body: some View {
    NavigationView {
      ScrollView {
        VStack(alignment: .leading) {
          Text(survey.title)
            .font(.headline)
            .padding(.bottom, 1)
          Text(survey.description)
            .font(.subheadline)
            .padding(.bottom)
          
          ForEach(survey.questions, id: \.self) { question in
            switch question.questionType {
            case .open:
              OpenQuestion(question: question)
            case .scale:
              ScaleQuestion(question: question)
            case .multipleChoice:
              MultipleChoice(question: question)
            }
          }
        } .padding(8)
      }
      .navigationBarTitle(Text(survey.title))
    }
  }
}

struct Previews_SurveyView_Previews: PreviewProvider {
  static var previews: some View {
    SurveyView(survey: sampleSurvey)
  }
}
