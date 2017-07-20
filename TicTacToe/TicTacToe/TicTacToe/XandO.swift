//
//  XandO.swift
//  TicTacToe
//
//  Created by Nishant Badal on 2/12/17.
//  Copyright © 2017 Nishant Badal. All rights reserved.
//

import Foundation
import UIKit

class XandO: UIImageView {
    
    /// Animator vars
    var XOAnimator: UIViewPropertyAnimator!
    let animDuration = 1.5
    
    /// Is the mark a X or O
    var isCross: Bool!
    
    /// Initializes mark
    /// - Parameter aDecoder: NSCoder object passed to super class
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.isUserInteractionEnabled = false
        self.alpha = 0
        isCross = false
    }
    
    /// Starts a mark's turn
    func startTurn() {
        self.isUserInteractionEnabled = true
        self.alpha = 1.0
        startTurnAnim()
    }
    
    /// Ends mark's turn
    func endTurn() {
        self.isUserInteractionEnabled = false
        self.alpha = 0.5
    }
    
    /// Animates growing and shrinking
    /// -Attributions: http://jamesonquave.com/blog/category/swift-3/
    func startTurnAnim() {
        UIImageView.animate(withDuration: animDuration, delay: 0.0, options: UIViewAnimationOptions.curveLinear, animations: {
            // put here the code you would like to animate
            self.transform = CGAffineTransform(scaleX: 2.0, y: 2.0)
            
        }, completion: {(finished:Bool) in
            // the code you put here will be compiled once the animation finishes
            UIImageView.animate(withDuration: self.animDuration, delay: 0.0, options: UIViewAnimationOptions.curveLinear, animations: {
                // put here the code you would like to animate
                self.transform = CGAffineTransform(scaleX: 1.0, y: 1.0)
                
            })
        })
    }
    
    /// Animates move off board
    /// -Attributions: http://jamesonquave.com/blog/category/swift-3/
    func moveOffBoard() {
        UIImageView.animate(withDuration: self.animDuration, delay: 0.0, options: UIViewAnimationOptions.curveLinear, animations: {
            // put here the code you would like to animate
            self.transform = CGAffineTransform(translationX: -256, y: -256)
            
        })
    }
    
    
    
}
    

