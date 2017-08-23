//
//  QRCodeViewController.swift
//  QRCodeReader
//
//  Created by Simon Ng on 13/10/2016.
//  Copyright © 2016 AppCoda. All rights reserved.
//

import UIKit

class QRCodeViewController: UIViewController, UITextFieldDelegate {
    
    let defaults = UserDefaults.standard
    @IBOutlet weak var scanButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        scanButton.adjustsImageWhenHighlighted = false;
        
        defaults.set("N/A", forKey: "firstName")
        defaults.set("N/A", forKey: "lastName")
        defaults.set("N/A", forKey: "email")
        defaults.set("N/A", forKey: "division")

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
    // MARK: - Navigation

    @IBAction func unwindToHomeScreen(segue: UIStoryboardSegue) {
        dismiss(animated: true, completion: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        UIApplication.shared.statusBarStyle = .default
    }
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return .default
    }
    

}
