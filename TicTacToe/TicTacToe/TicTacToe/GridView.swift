//
//  GridView.swift
//  TicTacToe
//
//  Created by Nishant Badal on 2/12/17.
//  Copyright © 2017 Nishant Badal. All rights reserved.
//

import Foundation
import UIKit

class GridView: UIView {
    /// Vars for what is placed on each slot
    var placed: Bool = false
    var placedX: Bool = false
    var placedO: Bool = false
    
    /// Parses Array of Dictionary objects.
    /// - Parameter aDecoder:
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
}
