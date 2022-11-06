#include "AccelStepper.h"
#include "SafeStringReader.h"

// Set up the pins
#define DRIVER_ENABLE 8

// Create the reader and set up the variables needed
createSafeStringReader(reader, 30, "\n");
createSafeString(field, 6);
const char delimiters[] = ":";
int searchFrom = 0;
const bool returnEmptyFields = false;

typedef struct {
   AccelStepper accelStepper;
   int limitPin;
   bool homing;
   int activeMessageId;
} Stepper;

#define NUM_STEPPERS 4
Stepper steppers[NUM_STEPPERS] = { 
  // Back vertical stepper
  { AccelStepper(AccelStepper::DRIVER, 2, 5), 9, false, -1},
  // Back multiplexing stepper (drives drawers in and out, can be positioned on any drawer)
  { AccelStepper(AccelStepper::DRIVER, 3, 6), 10, false, -1},
  // Side vertical stepper
  { AccelStepper(AccelStepper::DRIVER, 4, 7), 11, false, -1},
  // Side multiplexing stepper (drives camera left and right, can be positioned on any drawer)
  { AccelStepper(AccelStepper::DRIVER, 12, 13), 21, false, -1}
};

void setup() {
  Serial.begin(115200);

  SafeString::setOutput(Serial); // enable error messages and SafeString.debug() output to be sent to Serial

  // Set up the reader to listen to Serial
  reader.connect(Serial);

  // Enable the drivers
  pinMode(DRIVER_ENABLE,OUTPUT);
  digitalWrite(DRIVER_ENABLE, LOW);

  // Configure the stepper velocities
  for(int i = 0; i < 4; i++) {
    steppers[i].accelStepper.setMaxSpeed(1000);
    steppers[i].accelStepper.setAcceleration(500);
  }

}

// Invoked when a message terminator is received
void parseMessage() {

 //Serial.println(reader);

  // Read the message number
  searchFrom = reader.stoken(field, 0, delimiters, returnEmptyFields);
  int messageNumber = -1;
  field.toInt(messageNumber);

  // Get the message type
  searchFrom = reader.stoken(field, searchFrom, delimiters, returnEmptyFields);

  switch(field.charAt(0)) {

    // Move 
    case 'm':
      {
        // Get the stepper number
        searchFrom = reader.stoken(field, searchFrom, delimiters, returnEmptyFields);
        int stepper = -1;
        field.toInt(stepper);

        // Get the position to go to
        searchFrom = reader.stoken(field, searchFrom, delimiters, returnEmptyFields);
        long position = -1;
        field.toLong(position);

        // If we have another token available, it's forcing our current position
        long currentPosition = -1;
        bool updateCurrentPosition = false;
        if(searchFrom > -1) {
          updateCurrentPosition = true;
          searchFrom = reader.stoken(field, searchFrom, delimiters, returnEmptyFields);
          field.toLong(currentPosition);
        }

        handleMoveStepper(messageNumber, stepper, position, updateCurrentPosition, currentPosition);
      }
      break;

    // Home
    case 'h':
      {
        // Get the stepper number
        searchFrom = reader.stoken(field, searchFrom, delimiters, returnEmptyFields);
        int stepper = -1;
        field.toInt(stepper);

        handleHomeStepper(messageNumber, stepper);
      }
      break;

    default: 
      Serial.println("Unknown");
  }

}

void handleMoveStepper(int messageId, int stepperNumber, long newPosition, bool updateCurrentPosition, long currentPosition) {
  Serial.print("d: Move stepper "); Serial.print(stepperNumber); Serial.print(" to position "); Serial.println(newPosition);
  //Stepper stepper = steppers[stepperNumber];

  // If we're currently doing anything, cancel it out
  if(steppers[stepperNumber].activeMessageId != -1) {
    Serial.print(steppers[stepperNumber].activeMessageId); Serial.println(":cancel");
  }
  
  // Start tracking the active request
  steppers[stepperNumber].activeMessageId = messageId;

  Serial.print(steppers[stepperNumber].activeMessageId); Serial.println(":ok");

  // Set the current position - this is required when a multiplexed stepper has changed drawers
  // It allows drawers and cameras to be in a different location from eachother without having to reset to home on transitions
  if(updateCurrentPosition) {
    steppers[stepperNumber].accelStepper.setCurrentPosition(currentPosition);
  }

  steppers[stepperNumber].accelStepper.moveTo(newPosition);
}

void handleHomeStepper(int messageId, int stepperNumber) {
  //Serial.print("D: Home stepper "); Serial.println(stepper); 

  // If we're currently doing anything, cancel it out
  if(steppers[stepperNumber].activeMessageId != -1) {
    Serial.print(steppers[stepperNumber].activeMessageId); Serial.println(":cancel");
  }
  
  // Start tracking the active request
  steppers[stepperNumber].activeMessageId = messageId;

  Serial.print(steppers[stepperNumber].activeMessageId); Serial.println(":ok");

  // Go backwards slowly until the limit is hit
  steppers[stepperNumber].homing = true;
  steppers[stepperNumber].accelStepper.setMaxSpeed(500);
  steppers[stepperNumber].accelStepper.setAcceleration(500);
  steppers[stepperNumber].accelStepper.moveTo(-10000);
}


void loop() {

  // Deal with messages as they arrive
  if (reader.read()) {
    parseMessage();
  }

  // Advance all the steppers if needed
  for(int i = 0; i < 4; i++) {

    // if(i == 0) {
    //   if(steppers[i].homing) {
    //     Serial.print(steppers[i].homing);
    //     Serial.print("-");
    //     Serial.println(digitalRead(steppers[i].limitPin));
    //   }
    // }

    // Do this first in case we're already touching the pin when we start homing
    if(steppers[i].homing && digitalRead(steppers[i].limitPin) == 0) {
      
      // Homing is complete
      steppers[i].homing = false;
      steppers[i].accelStepper.stop();

      // Reset position to 0 and set speed
      steppers[i].accelStepper.setCurrentPosition(0);
      steppers[i].accelStepper.setMaxSpeed(1000);
      
      // Close out the home message
      Serial.print(steppers[i].activeMessageId);
      Serial.println(":end");
      steppers[i].activeMessageId = -1;
      continue;
    }

    // Advance the stepper
    bool stepperMoving = steppers[i].accelStepper.run();

    // If we're moving, just keep going
    if(stepperMoving) {
      continue;
    }

    // If we're not moving and we have an active message id, then we're done with our movement and need to reset
    if(steppers[i].activeMessageId != -1) {
      Serial.print(steppers[i].activeMessageId);
      Serial.println(":end");
      steppers[i].activeMessageId = -1;
    }
  }
}