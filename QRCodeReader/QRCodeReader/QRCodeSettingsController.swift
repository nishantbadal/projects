//
//  QRCodeSettingsController.swift
//  QRCodeReader
//
//  Created by Nishant Badal on 8/18/17.
//  Copyright © 2017 AppCoda. All rights reserved.
//

import Foundation
import UIKit

class QRCodeSettingsController: UIViewController, UITextFieldDelegate {
    
    let defaults = UserDefaults.standard
    
    @IBOutlet weak var firstName: UITextField!
    @IBOutlet weak var lastName: UITextField!
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var division: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        firstName.delegate = self
        lastName.delegate = self
        email.delegate = self
        division.delegate = self
        
        let firstNameValue = defaults.object(forKey: "firstName") as! String
        let lastNameValue = defaults.object(forKey: "lastName") as! String
        let emailValue = defaults.object(forKey: "email") as! String
        let divisionValue = defaults.object(forKey: "division") as! String
        
        firstName.text = firstNameValue
        lastName.text = lastNameValue
        email.text = emailValue
        division.text = divisionValue
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == firstName {
            firstName.resignFirstResponder()
        } else if textField == lastName {
            lastName.resignFirstResponder()
        } else if textField == email {
            email.resignFirstResponder()
        } else if textField == division {
            division.resignFirstResponder()
        }
        return true
    }
    
    @IBAction func backButtonPressed(_ sender: Any) {
        defaults.set(firstName.text, forKey: "firstName")
        defaults.set(lastName.text, forKey: "lastName")
        defaults.set(email.text, forKey: "email")
        defaults.set(division.text, forKey: "division")
        
        self.dismiss(animated: true, completion: nil)
    }
    
    

}
