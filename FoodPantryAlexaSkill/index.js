'use strict';

var AlexaSkill = require('./AlexaSkill'),
    floorOne = require('./floorOne'),
    floorTwo = require('./floorTwo'),
    floorThree = require('./floorThree'),
    floorFour = require('./floorFour'),
    floorFive = require('./floorFive'),
    floorSix = require('./floorSix'),
    floorED = require('./floorED'),

var floorOneRestock = false;
var floorTwoRestock = false;
var floorThreeRestock = false;
var floorFourRestock = false;
var floorFiveRestock = false;
var floorSixRestock = false;
var floorEDRestock = false;

var APP_ID = ''; //replace with 'amzn1.echo-sdk-ams.app.[your-unique-value-here]';

// Create array containing all floors
var floors = [floorOne, floorTwo, floorThree, floorFour, floorFive, floorSix, floorED];

// Create temp array that holds the items that need to be restocked
var itemsRestock = [];
// Tracks how far in the itemsRestock array the user is
var itemsRestockIndex = -1;
// Array controls
var next = false
var repeat = false
var previous = false
var done = false
var restock = false
// Store current floor being restocked so you can reference it to update item quantities
var currentFloorBeingRestocked = -1;
// Store current floor name String to reference in case user tries to call restock again while a restock is in process
var currentFloorString = "";

// Temp test of AllFloorRestockIntent & SpecificFloorRestockIntent
floors[0]["mac and cheese"].currQuantity = 5
floors[0]["corn"].currQuantity = 5
floors[2]["pea can"].currQuantity = 5
floors[3]["mixed vegetables"].currQuantity = 5
floors[4]["cream of rice"].currQuantity = 5
floors[4]["pork beans"].currQuantity = 5
floors[5]["beef soup can"].currQuantity = 5
floors[6]["chicken noodle soup can"].currQuantity = 5

// IMPLEMENTED IN FURTURE VER - Properly set floor currQuantity by grabbing from database

var Inventory = function () {
    AlexaSkill.call(this, APP_ID);
};

// Extend AlexaSkill
Inventory.prototype = Object.create(AlexaSkill.prototype);
Inventory.prototype.constructor = Inventory;

Inventory.prototype.eventHandlers.onLaunch = function (launchRequest, session, response) {
    var speechText = "Welcome to the Feed First Food Pantry.";
    // If the user either does not reply to the welcome message or says something that is not
    // understood, they will be prompted again with this text.
    var repromptText = "For instructions on what you can say, please say help me.";
    response.ask(speechText, repromptText);
};

Inventory.prototype.intentHandlers = {
    "AllFloorRestockIntent": function (intent, session, response) {
    // Loop through all floors
    var i = 0;
    while (i < floors.length) {
        // Loop through all items on each floor
        for (var key in floors[i]) {
            // Compare an items current quantity with its full stock quantity
            if (floors[i][key].currQuantity < floors[i][key].fullStockQuantity) {
                // Set that floor need restocking to true
                // These will be set back to false in the add item function, which will check if all items are restocked on a floor after adding an item
                if (i == 0) {
                    floorOneRestock = true;
                }
                else if (i == 1) {
                    floorTwoRestock = true;
                }
                else if (i == 2) {
                    floorThreeRestock = true;
                }
                else if (i == 3) {
                    floorFourRestock = true;
                }
                else if (i == 4) {
                    floorFiveRestock = true;
                }
                else if (i == 5) {
                    floorSixRestock = true;
                }
                else if (i == 6) {
                    floorEDRestock = true;
                }
            }
        }
        // Increment i to the next floor
        i = i + 1;
    }
        
    // Construct response
    var output = "The following floors contain at least one item that needs restocking. ";
    if (floorOneRestock == true) {
        output += "one, ";
    }
    if (floorTwoRestock == true) {
        output += "two, ";
    }
    if (floorThreeRestock == true) {
        output += "three, ";
    }
    if (floorFourRestock == true) {
        output += "four, ";
    }
    if (floorFiveRestock == true) {
        output += "five, ";
    }
    if (floorSixRestock == true) {
        output += "six, ";
    }
    if (floorEDRestock == true) {
        output += "E D, ";
    }
    // Format response    
    var speechOutput = {
        speech: output
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    var repromptOutput = {
        speech: "What else can I help with?"
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    // Send output
    response.ask(speechOutput, repromptOutput);
},
    
    "SpecificFloorRestockIntent": function (intent, session, response) {
        // Get floor number from user
        var floorNumSlot = intent.slots.FloorNumber, floorNumName;
        if (floorNumSlot && floorNumSlot.value){
            floorNumName = floorNumSlot.value.toLowerCase();
        }
        
        // Alexa has some trouble understanding e d lower case, so convert to E D uppercase
        if (floorNumName === "e d" || floorNumName === "ed") {
            floorNumName = "E D"
        }
        
        // Convert to integer
        var i;
        if (floorNumName === "1") {
            i = 0;
        } else if (floorNumName === "2") {
            i = 1;
        } else if (floorNumName === "3") {
            i = 2;
        } else if (floorNumName === "4") {
            i = 3;
        } else if (floorNumName === "5") {
            i = 4;
        } else if (floorNumName === "6") {
            i = 5;
        } else if (floorNumName === "E D" || floorNumName === "emergency department" || floorNumName === "emergency") {
            i = 6;
        }
        
        // Construct response
        var output = "Floor " + floorNumName + " needs ";
        // Because the key names are the several ways that user can say a food item, this is used to skip items that are already accounted for, probably best to change the data structure to handle this more efficiently in the future
        var keyHolder = ""
        // Check if floor actually needs something
        var floorNeedsRestock = false
        // Loop through all items on floor
        for (var key in floors[i]) {
            // Initialize keyHolder to first key value
            if (keyHolder === "") {
                keyHolder = floors[i][key].identifier
            } 
            // If keyHolder has already been initialized, use it to skip keys that have already been accounted for
            else {
                // If keyHolder, which holds the previous key, is equal to the current key, skip this key which is a repeat of th revious item
                if (keyHolder === floors[i][key].identifier) {
                    continue;
                } 
                // If not, set keyHolder to current key
                else {
                    keyHolder = floors[i][key].identifier
                }
            }
            
            // Compare an items current quantity with its full stock quantity
            if (floors[i][key].currQuantity < floors[i][key].fullStockQuantity) {
                floorNeedsRestock = true
                output += key + ", "
                
            }
        }
        
        if (floorNeedsRestock === true) {
            output += ". Say restock floor " + floorNumName + " to begin restocking."
        } else {
            output = "Floor " + floorNumName + " does not need restocking.";
        }
        
        // Format response    
    var speechOutput = {
        speech: output
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    var repromptOutput = {
        speech: "What else can I help with?"
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
        
    // Send output
    response.ask(speechOutput, repromptOutput);
        
    },
    
    "BeginFloorRestockIntent": function (intent, session, response) {
        var output = "";
        if (done === false) {
        // Get floor number from user
        var floorNumSlot = intent.slots.FloorNumber, floorNumName;
        if (floorNumSlot && floorNumSlot.value){
            floorNumName = floorNumSlot.value.toLowerCase();
        }
        
        // Alexa has some trouble understanding e d lower case, so convert to E D uppercase
        if (floorNumName === "e d" || floorNumName === "ed") {
            floorNumName = "E D"
        }
        
        // Convert to integer
        var i;
        if (floorNumName === "1") {
            i = 0;
        } else if (floorNumName === "2") {
            i = 1;
        } else if (floorNumName === "3") {
            i = 2;
        } else if (floorNumName === "4") {
            i = 3;
        } else if (floorNumName === "5") {
            i = 4;
        } else if (floorNumName === "6") {
            i = 5;
        } else if (floorNumName === "E D" || floorNumName === "emergency department" || floorNumName === "emergency") {
            i = 6;
        }
        
        // Set floor being restocked for next, repeat, and previous intents to reference
        currentFloorBeingRestocked = i;
            
        currentFloorString = floorNumName;
        
        // Construct response
        output = "Ok, let's restock Floor " + floorNumName + ".";
        // Because the key names are the several ways that user can say a food item, this is used to skip items that are already accounted for, probably best to change the data structure to handle this more efficiently in the future
        var keyHolder = ""
        // Check if floor actually needs something
        var floorNeedsRestock = false
        // Loop through all items on floor
        for (var key in floors[i]) {
            // Initialize keyHolder to first key value
            if (keyHolder === "") {
                keyHolder = floors[i][key].identifier
            } 
            // If keyHolder has already been initialized, use it to skip keys that have already been accounted for
            else {
                // If keyHolder, which holds the previous key, is equal to the current key, skip this key which is a repeat of the previous item
                if (keyHolder === floors[i][key].identifier) {
                    continue;
                } 
                // If not, set keyHolder to the new key (the new item)
                else {
                    keyHolder = floors[i][key].identifier
                }
            }
            
            // Compare an item's current quantity with its full stock quantity
            if (floors[i][key].currQuantity < floors[i][key].fullStockQuantity) {
                floorNeedsRestock = true
                // Create temp item object that contains the name the quantity of restock needed
                var tempItemHolder = {name: key, restockAmount: floors[i][key].fullStockQuantity - floors[i][key].currQuantity};
                // Add the object to the itemsRestock array
                itemsRestock.push(tempItemHolder);  
            }
        }
        
        if (floorNeedsRestock === true) {
            //output += " First, you need " + itemsRestock[itemsRestockIndex].restockAmount + " " + itemsRestock[itemsRestockIndex].name + ". After you're done restocking this item, say next for the next item.";
            
            // Update restock quantity
            //floors[i][itemsRestock[itemsRestockIndex].name].currQuantity = floors[i][itemsRestock[itemsRestockIndex].name].fullQuantity;
            
            output += " Say next, repeat, or previous to move between items. Say restock to update the database. Say done when you're done restocking.";
            
            // Enable next, previous, repeat, done, restock
            next = true;
            previous = true;
            repeat = true;
            done = true;
            restock = true;
            
        } else {
            output = "Floor " + floorNumName + " does not need restocking.";
        }
        
        } else {
            output = "You cannot begin restocking another floor because you are currently restocking floor " + currentFloorString + ". Say done to end the current restocking process."
        }
        
        // Format response    
    var speechOutput = {
        speech: output
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    var repromptOutput = {
        speech: "What else can I help with?"
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
        
    // Send output
    response.ask(speechOutput, repromptOutput);
    },
    
    "NextIntent": function (intent, session, response) {
        var output = "";
        // If next is enabled
     if (next === true) {
         // Increment itemsRestockIndex to go to next item
            itemsRestockIndex = itemsRestockIndex + 1;
         
         if(itemsRestock.length < itemsRestockIndex) {
                 itemsRestockIndex = itemsRestockIndex - 1;
         }
         
         // Check if at the end of items in need of restocking list
         if (itemsRestock.length <= itemsRestockIndex) {
             
             // Construct output that tells user there are no more items, floor is fully stocked
             if (itemsRestock.length === 0) {
                 output += "You have restocked all items.";
             } else {
             output += "You have reached the end of the restock list. Say previous or done.";
         }
         }
         else {
             // Construct output for item
             output += "You need " + itemsRestock[itemsRestockIndex].restockAmount + " " + itemsRestock[itemsRestockIndex].name + ".";
             
         }
     } else {
         output += "Please specify which floor you would like to restock. For example, say restock floor one.";
     }
        
         // Give response    
         var speechOutput = {
             speech: output
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
         var repromptOutput = {
             speech: "What else can I help with?"
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
         
         // Send output
    response.ask(speechOutput, repromptOutput);
         
    },
    
    "PreviousIntent": function(intent, session, response) {
        var output = "";
        
        if (previous === true) {
            if (itemsRestockIndex >= 1) {
                // Decrement itemRestockIndex
                itemsRestockIndex = itemsRestockIndex - 1;
                
                // Construct output for that item
                output += "You need " + itemsRestock[itemsRestockIndex].restockAmount + " " + itemsRestock[itemsRestockIndex].name + ".";
            } else {
                // Construct output telling user that they are at the first item on the list and can only say next/repeat
                
                if (itemsRestock.length === 0) {
                    output += "You have restocked all items."; 
                } else {
                output += "You are at the first item on the list. You cannot go to a previous item. Say repeat, next, or done.";
                }
            }
        } else {
            output += "Please specify which floor you would like to restock. For example, say restock floor one.";
        }
            // Give response
            var speechOutput = {
             speech: output
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
         var repromptOutput = {
             speech: "What else can I help with?"
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
            
            // Send output
    response.ask(speechOutput, repromptOutput);
    },
    
    "RepeatIntent": function (intent, session, response) {
    var output = "";
        
    if (repeat === true) {
        
        // If user hasnt gone to the first item or is after the last item
        if (itemsRestockIndex === -1 || itemsRestock.length === itemsRestockIndex) {
            // If the length is 0
            if (itemsRestock.length === 0) {
                output += "You have restocked all items.";
            }
            // Else tell user to go forward or back
            else {
                output += "You cannot say repeat if you are the end or beginning of the list. Say next or previous.";
            }
        }
        // Else construct response for that item
        else {
            output += " You need " + itemsRestock[itemsRestockIndex].restockAmount + " " + itemsRestock[itemsRestockIndex].name + ".";
        }
    }
        // Tell user they need to specify floor to say repeat
    else {
        output += "Please specify which floor you would like to restock. For example, say restock floor one.";
    }
        
    // Give response
    var speechOutput = {
        speech: output
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    var repromptOutput = {
        speech: "What else can I help with?"
        , type: AlexaSkill.speechOutputType.PLAIN_TEXT
    };
    // Send output
    response.ask(speechOutput, repromptOutput);
},
    
    "DoneIntent": function(intent, session, response) {
        var output = ""
        
        if (done === true) {
        // Reset itemRestock array
             itemsRestock = [];
             // Reset itemRestockIndex
             itemsRestockIndex = -1;
            // Reset next, repeat, previous
             next = false;
             repeat = false;
             previous = false;
             done = false;
             restock = false;
            output += "Ok, I am ending the floor restock process.";
        } else {
            output += "Please specify which floor you would like to restock. For example, say restock floor one.";
        }
        
        // Give response
        var speechOutput = {
             speech: output
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
         var repromptOutput = {
             speech: "What else can I help with?"
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
        
        // Send output
    response.ask(speechOutput, repromptOutput);
    },
    
    "RestockIntent": function(intent, session, response) {
        
        var output = ""
        
        if (restock === true) {
            
            if (itemsRestockIndex > -1 && itemsRestockIndex < itemsRestock.length) {
            output += "Ok, you restocked " + itemsRestock[itemsRestockIndex].restockAmount + " " + itemsRestock[itemsRestockIndex].name + "."
            
            // Update restock quantity
            floors[currentFloorBeingRestocked][itemsRestock[itemsRestockIndex].name].currQuantity = floors[currentFloorBeingRestocked][itemsRestock[itemsRestockIndex].name].fullQuantity;
            
            // Remove the item from restock array
            itemsRestock.splice(itemsRestockIndex, 1);
            itemsRestockIndex--;
            } else {
                // If the user has restocked everything let the user know
                if (itemsRestock.length === 0) {
                    output += "You have restocked all items.";
                } 
                // Else tell user not to say restock at the edges of the list
                else {
                output += "You cannot say restock at the beginning or end of the list."
                }
            }
            
            
        } else {
            output += "Please specify which floor you would like to restock. For example, say restock floor one.";
        }
        
        // Give response
        var speechOutput = {
             speech: output
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
         var repromptOutput = {
             speech: "What else can I help with?"
             , type: AlexaSkill.speechOutputType.PLAIN_TEXT
         };
        
        // Send output
    response.ask(speechOutput, repromptOutput);
    },

    "AMAZON.StopIntent": function (intent, session, response) {
        var speechOutput = "Good bye.";
        response.tell(speechOutput);
    },

    "AMAZON.CancelIntent": function (intent, session, response) {
        var speechOutput = "Goodbye";
        response.tell(speechOutput);
    },

    "AMAZON.HelpIntent": function (intent, session, response) {
        var speechText = "You can use the Food Pantry to make restocking easier. I can give you what floors need restocking, a list of what a specific floor needs, and guide you through the items one by one as you pick them up. For a list of commands say more.";
        var repromptText = "For instructions, say help me.";
        var speechOutput = {
            speech: speechText,
            type: AlexaSkill.speechOutputType.PLAIN_TEXT
        };
        var repromptOutput = {
            speech: repromptText,
            type: AlexaSkill.speechOutputType.PLAIN_TEXT
        };
        response.ask(speechOutput, repromptOutput);
    },
    
    "MoreIntent": function (intent, session, response) {
        var speechText = "For a list of floors that need restocking, you can say 'what floors need restocking'. For a list of what items a specific floor needs you can say 'what does floor one need'. To restock a floor, you can say 'restock floor one'. You can then say next, previous, and repeat to navigate through items. Say restock on an item and I will update the database. If you don’t talk for a while I will get tired and go to sleep. To wake me up just say “Alexa, open the food pantry” and I’ll say hello! If I fall asleep while you're restocking, don't worry! You can simply wake me up and continue by saying next, previous, and repeat.";
        var repromptText = "For instructions, say help me.";
        var speechOutput = {
            speech: speechText,
            type: AlexaSkill.speechOutputType.PLAIN_TEXT
        };
        var repromptOutput = {
            speech: repromptText,
            type: AlexaSkill.speechOutputType.PLAIN_TEXT
        };
        response.ask(speechOutput, repromptOutput);
    }
    
};


exports.handler = function (event, context) {
    var inventory = new Inventory();
    inventory.execute(event, context);
};
