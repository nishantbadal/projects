//
//  Line.swift
//  TicTacToe
//
//  Created by Nishant Badal on 2/13/17.
//  Copyright © 2017 Nishant Badal. All rights reserved.
//

import Foundation
import UIKit

class Line: UIBezierPath {
    
    
    func draw(_ rect: CGRect) {
        let aPath = UIBezierPath()
        
        aPath.move(to: CGPoint(x:20, y:50))
        
        aPath.addLine(to: CGPoint(x:300, y:50))
        
        //Keep using the method addLineToPoint until you get to the one where about to close the path
        
        aPath.close()
        
        //If you want to stroke it with a red color
        UIColor.red.set()
        aPath.stroke()
        //If you want to fill it as well
        aPath.fill()
    }
}
