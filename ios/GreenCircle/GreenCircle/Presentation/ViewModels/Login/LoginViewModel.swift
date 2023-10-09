//
//  LoginViewModel.swift
//  GreenCircle
//
//  Created by Ricardo Adolfo Fernández Alvarado on 17/09/23.
//

import Foundation

/// Un enum representando el estado de la operación de login
enum SignInState {
  case newUser
  case success
  case fail
}

class LoginViewModel: ObservableObject {
  var googleUseCase = GoogleSignInUseCase.shared
  var signInUseCase = UserSignInUseCase.shared
  var appleUseCase = AppleSignInUseCase.shared
  
  var lService = LocalService.shared
  @Published var user = ""
  @Published var password = ""
  @Published var showAlert = false
  @Published var alertMessage = ""
  @Published var email = ""
  @Published var fullName = ""
  @Published var userId = ""
  @Published var loadingGoogle = false
  
  /// Función encargada de realizar el SignIn con Google y actualizar la información de entorno
  /// - Parameter userData: objeto de entorno
  /// - Returns: un enum indicando el estado de la operación
  @MainActor
  func handleGoogleSignIn() async -> SignInState {
    let res = await googleUseCase.handleSignInButton()
    
    if res == .fail {
      showAlert = true
      alertMessage = "Intenta de nuevo más tarde."
    }
    
    return res
  }
  
  @MainActor
  func handleAppleSignIn() async -> SignInState {
    let res = await appleUseCase.signIn(userId: "001602.c8d6835d52754fe59710ebb7430ebb7430eeca4.0939", fullName: "Denisse Maldonado", email: "denissem@icloud.com")
    
    if res == .fail {
      showAlert = true
      alertMessage = "Intenta de nuevo más tarde"
    }
    
    return res
  }
  
  @MainActor
  func handleSignIn() async -> SignInState {
    let res = await signInUseCase.signIn(user: user, password: password)
    
    if res == .fail {
      showAlert = true
      alertMessage = "Credenciales inválidas."
    }
    
    return res
  }
}
