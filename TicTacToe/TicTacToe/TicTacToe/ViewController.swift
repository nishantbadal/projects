//
//  ViewController.swift
//  TicTacToe
//
//  Created by Nishant Badal on 2/12/17.
//  Copyright © 2017 Nishant Badal. All rights reserved.
//

import UIKit
import AVFoundation

class ViewController: UIViewController {
    
    /// Resets Game and animates marks off
    /// - Parameter sender: Button clicked ot trigger this
    @IBAction func popUpButton(_ sender: UIButton) {
        
        if (win == true) {
            layer.lineWidth = 0
        }
        
        UIImageView.animate(withDuration: 2.0, delay: 0.0, options: UIViewAnimationOptions.curveLinear, animations: {
            var i = 0
            while i < self.crosses.count {
            self.crosses[i].transform = CGAffineTransform(translationX: -400, y: -400)
                i += 1
            }
            
            i = 0
            while i < self.circles.count {
                self.circles[i].transform = CGAffineTransform(translationX: -400, y: -400)
                i += 1
            }
            
        } , completion: {(finished:Bool) in
            self.reset()
        })
    }
    
    /// Pop up references
    @IBOutlet weak var popUpLabel: UILabel!
    @IBOutlet weak var popUp: UIView!
    @IBOutlet weak var blackFilter: UIView!
    @IBOutlet weak var button: UIButton!
    
    /// Line vars
    var line: UIBezierPath!
    var layer: CAShapeLayer!
    
    /// Tracks if player has won
    var win: Bool!
    
    /// Tracks which mark is being moved
    var crossCounter = 0
    var circleCounter = 0
    
    /// Tracks whose turn it is
    var crossTurn = true
    
    /// These record our circle's center for use as an offset while dragging
    var crossCenter: CGPoint!
    var circleCenter: CGPoint!
    
    /// Declare sound player
    var soundPlayer = AVAudioPlayer()
    var selectSound: NSURL?
    var errorSound: NSURL?
    var winSound: NSURL?
    
    /// Circle references
    @IBOutlet weak var circle: XandO!
    @IBOutlet weak var circle4: XandO!
    @IBOutlet weak var circle3: XandO!
    @IBOutlet weak var circle2: XandO!
    
    /// Cross references
    @IBOutlet weak var cross5: XandO!
    @IBOutlet weak var cross4: XandO!
    @IBOutlet weak var cross3: XandO!
    @IBOutlet weak var cross2: XandO!
    @IBOutlet weak var cross: XandO!
    
    /// Mark center locations
    var crossCenterLoc: CGPoint!
    var circleCenterLoc: CGPoint!
    
    /// Grid views
    @IBOutlet weak var bottomRight: GridView!
    @IBOutlet weak var bottomMiddle: GridView!
    @IBOutlet weak var bottomLeft: GridView!
    
    @IBOutlet weak var middleRight: GridView!
    @IBOutlet weak var middleCenter: GridView!
    @IBOutlet weak var middleLeft: GridView!
    
    @IBOutlet weak var topRight: GridView!
    @IBOutlet weak var topLeft: GridView!
    @IBOutlet weak var topCenter: GridView!
    
    /// Arrays of marks and slots
    var gridSlots = [GridView]()
    var crosses = [XandO]()
    var circles = [XandO]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Load resources
        selectSound = NSURL(fileURLWithPath: Bundle.main.path(forResource: "click", ofType: "mp3")!)
        errorSound = NSURL(fileURLWithPath: Bundle.main.path(forResource: "error", ofType: "mp3")!)
        winSound = NSURL(fileURLWithPath: Bundle.main.path(forResource: "win", ofType: "mp3")!)
        
        crossCenterLoc = cross.center
        circleCenterLoc = circle.center
        
        gridSlots.append(bottomRight)
        gridSlots.append(bottomMiddle)
        gridSlots.append(bottomLeft)
        gridSlots.append(middleRight)
        gridSlots.append(middleCenter)
        gridSlots.append(middleLeft)
        gridSlots.append(topRight);
        gridSlots.append(topCenter);
        gridSlots.append(topLeft);
        
        crosses.append(cross)
        crosses.append(cross2)
        crosses.append(cross3)
        crosses.append(cross4)
        crosses.append(cross5)
        
        circles.append(circle)
        circles.append(circle2)
        circles.append(circle3)
        circles.append(circle4)
        
        // Set gestures
        setCross()
        setCircle()
        
        // Starts game
        crosses[crossCounter].startTurn()
        circle.endTurn()
        
        win = false
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    /// Resets game.
    func reset() {
        popUp.isHidden = true
        blackFilter.isHidden = true
        
        // reset counters
        crossCounter = 0
        circleCounter = 0
        
        // Set locations
        self.crosses[0].center = self.crossCenterLoc
        self.circles[0].center = self.circleCenterLoc
        
        // Remove saved placements
        var i = 0
        
        while i < gridSlots.count {
            gridSlots[i].placed = false
            gridSlots[i].placedO = false
            gridSlots[i].placedX = false
            
            i += 1
        }
        
        
        // Start turn
        crosses[crossCounter].startTurn()
        circle.endTurn()
        
        win = false
        
    }
    
    /// Adds pan gesture recognizer to all cross symbols
    func setCross() {
        var i = 0
        
        while i < crosses.count {
            crosses[i].isCross = true
            crosses[i].addGestureRecognizer(UIPanGestureRecognizer(target: self, action: #selector(self.dragCross)))
            i += 1
        }
    }
    
    /// Adds pan gesture recognizer to all circle symbols
    func setCircle() {
        var i = 0
        
        while i < circles.count {
            circles[i].addGestureRecognizer(UIPanGestureRecognizer(target: self, action: #selector(self.dragCircle)))
            i += 1
        }
    }

    /// Tracks when drag starts, stops, and changes.
    /// - Parameter gesture: The type of gesture
    func dragCross(gesture: UIPanGestureRecognizer) {
        let target = gesture.view!
        
        switch gesture.state {
        case .began:
            crossCenter = target.center
            // Play sound
            do {
                soundPlayer = try AVAudioPlayer(contentsOf: selectSound as! URL)
                soundPlayer.prepareToPlay()
            } catch {
                print("File not found.")
            }
            soundPlayer.play()
            
        case .ended:
            crossCenter = target.center
            checkCrossPlacement(mark: crosses[crossCounter])
        case .changed:
            let translation = gesture.translation(in: self.view)
            target.center = CGPoint(x: crossCenter!.x + translation.x, y: crossCenter!.y + translation.y)
        default: break
        }
    }
    
    /// Tracks gestures
    /// - Parameter gesture: The pan gesture
    func dragCircle(gesture: UIPanGestureRecognizer) {
        let target = gesture.view!
        
        switch gesture.state {
        case .began:
            circleCenter = target.center
            // Play sound
            do {
                soundPlayer = try AVAudioPlayer(contentsOf: selectSound as! URL)
                soundPlayer.prepareToPlay()
            } catch {
                print("File not found.")
            }
            soundPlayer.play()
            
        case .ended:
            circleCenter = target.center
            checkCrossPlacement(mark: circles[circleCounter])
        case .changed:
            let translation = gesture.translation(in: self.view)
            target.center = CGPoint(x: circleCenter!.x + translation.x, y: circleCenter!.y + translation.y)
        default: break
        }
    }
    
    /// Checks where the mark was placed, if it can be placed there, game logic, and pop up management
    /// - Parameter mark: The X or O being moved
    func checkCrossPlacement(mark: XandO) {
        
        var i = 0
        
        while i < gridSlots.count {
            if (gridSlots[i].frame.intersects(mark.frame) && gridSlots[i].placed == false) {
                // Center the mark in the slot
                mark.center = gridSlots[i].center
                
                // Mark as placed
                gridSlots[i].placed = true
                
                // Go to next mark
                if (mark.isCross == true) {
                    crossCounter += 1
                    
                    if (crossCounter == 5) {
                        // Stalemate Pop up
                        popUp.isHidden = false
                        blackFilter.isHidden = false
                        popUpLabel.text = "Stalemate!"
                        break
                    }
                    
                    gridSlots[i].placedX = true
                } else {
                    circleCounter += 1
                    gridSlots[i].placedO = true
                }
                
                // Check for win
                if (checkForWin() == true) {
                    // Celebration noise
                    // Play sound
                    do {
                        soundPlayer = try AVAudioPlayer(contentsOf: winSound as! URL)
                        soundPlayer.prepareToPlay()
                    } catch {
                        print("File not found.")
                    }
                    soundPlayer.play()
                    
                    // Pop up
                    popUp.isHidden = false
                    blackFilter.isHidden = false
                    popUpLabel.text = "Win! 3 in a row!"
                    break
                    
                }
                
                mark.isUserInteractionEnabled = false
                
                // Manage next turn
                if (crossTurn) {
                // Set next cross to location
                crosses[crossCounter].center = crossCenterLoc
                crosses[crossCounter].endTurn()
                
                // Start circle player's turn
                circles[circleCounter].center = circleCenterLoc
                circles[circleCounter].startTurn()
                    
                    crossTurn = false
                } else {
                    
                    if (circleCounter != 4) {
                        // Set next circle to location
                        circles[circleCounter].center = circleCenterLoc
                        circles[circleCounter].endTurn()
                    }
                    
                    
                    // Start circle player's turn
                    crosses[crossCounter].center = crossCenterLoc
                    crosses[crossCounter].startTurn()
                    
                    crossTurn = true
                }
                break
                
            } else if (gridSlots[i].frame.intersects(mark.frame) && gridSlots[i].placed == true) {
                
                // Play error sound
                do {
                    soundPlayer = try AVAudioPlayer(contentsOf: errorSound as! URL)
                    soundPlayer.prepareToPlay()
                } catch {
                    print("File not found.")
                }
                soundPlayer.play()
                
                // Place the mark back to starting position
                print(circleCounter)
                if (mark.isCross == true) {
                   mark.center = crossCenterLoc
                } else {
                    mark.center = circleCenterLoc
                }
                
                
            }
            i += 1
        }
    }
    
    /// Checks if a player has won and draws line
    /// - Returns: Bool of whether there is a win
    func checkForWin() -> Bool {
        var x1: CGFloat!
        var x2: CGFloat!
        var y1: CGFloat!
        var y2: CGFloat!
        
        if (gridSlots[0].placedX == true && gridSlots[1].placedX == true && gridSlots[2].placedX == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[2].center.x
            y2 = gridSlots[2].center.y
            win = true
        } else if (gridSlots[3].placedX == true && gridSlots[4].placedX == true && gridSlots[5].placedX == true) {
            x1 = gridSlots[3].center.x 
            y1 = gridSlots[3].center.y
            x2 = gridSlots[5].center.x
            y2 = gridSlots[5].center.y
            win = true
        } else if (gridSlots[6].placedX == true && gridSlots[7].placedX == true && gridSlots[8].placedX == true) {
            x1 = gridSlots[6].center.x
            y1 = gridSlots[6].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[0].placedX == true && gridSlots[3].placedX == true && gridSlots[6].placedX == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[6].center.x
            y2 = gridSlots[6].center.y
            win = true
        } else if (gridSlots[1].placedX == true && gridSlots[4].placedX == true && gridSlots[7].placedX == true) {
            x1 = gridSlots[1].center.x
            y1 = gridSlots[1].center.y
            x2 = gridSlots[7].center.x
            y2 = gridSlots[7].center.y
            win = true
        } else if (gridSlots[2].placedX == true && gridSlots[5].placedX == true && gridSlots[8].placedX == true) {
            x1 = gridSlots[2].center.x
            y1 = gridSlots[2].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[0].placedX == true && gridSlots[4].placedX == true && gridSlots[8].placedX == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[2].placedX == true && gridSlots[4].placedX == true && gridSlots[6].placedX == true) {
            x1 = gridSlots[2].center.x
            y1 = gridSlots[2].center.y
            x2 = gridSlots[6].center.x
            y2 = gridSlots[6].center.y
            win = true
        }
        
        if (gridSlots[0].placedO == true && gridSlots[1].placedO == true && gridSlots[2].placedO == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[2].center.x
            y2 = gridSlots[2].center.y
            win = true
        } else if (gridSlots[3].placedO == true && gridSlots[4].placedO == true && gridSlots[5].placedO == true) {
            x1 = gridSlots[3].center.x
            y1 = gridSlots[3].center.y
            x2 = gridSlots[5].center.x
            y2 = gridSlots[5].center.y
            win = true
        } else if (gridSlots[6].placedO == true && gridSlots[7].placedO == true && gridSlots[8].placedO == true) {
            x1 = gridSlots[6].center.x
            y1 = gridSlots[6].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[0].placedO == true && gridSlots[3].placedO == true && gridSlots[6].placedO == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[6].center.x
            y2 = gridSlots[6].center.y
            win = true
        } else if (gridSlots[1].placedO == true && gridSlots[4].placedO == true && gridSlots[7].placedO == true) {
            x1 = gridSlots[1].center.x
            y1 = gridSlots[1].center.y
            x2 = gridSlots[7].center.x
            y2 = gridSlots[7].center.y
            win = true
        } else if (gridSlots[2].placedO == true && gridSlots[5].placedO == true && gridSlots[8].placedO == true) {
            x1 = gridSlots[2].center.x
            y1 = gridSlots[2].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[0].placedO == true && gridSlots[4].placedO == true && gridSlots[8].placedO == true) {
            x1 = gridSlots[0].center.x
            y1 = gridSlots[0].center.y
            x2 = gridSlots[8].center.x
            y2 = gridSlots[8].center.y
            win = true
        } else if (gridSlots[2].placedO == true && gridSlots[4].placedO == true && gridSlots[6].placedO == true) {
            x1 = gridSlots[2].center.x
            y1 = gridSlots[2].center.y
            x2 = gridSlots[6].center.x
            y2 = gridSlots[6].center.y
            win = true
        }
        
        
        if (win == true) {
        line = UIBezierPath(rect: CGRect(x: x1, y: y1, width: 1, height: 1))
        line.addLine(to: CGPoint(x: x2, y: y2))
        line.close()
        layer = CAShapeLayer()
        layer.path = line.cgPath
        layer.lineWidth = 10
        layer.strokeColor = UIColor.blue.cgColor
        layer.fillColor = UIColor.blue.cgColor
        self.view.layer.addSublayer(layer)
            
            return true
        }
        
        return false
    }
    
}

