#include <Servo.h>

Servo direction;


void turn_right(int level){
  int angle = 35;
  if(level == 1) angle = 20;  
  direction.write(angle);
}

void turn_left(int level){
  int angle = 35;
  if(level == 1) angle = 20;  
  direction.write(90 + angle);
}

void turn_middle(){
  direction.write(90);
}


void setup() {
  Serial.begin(9600);
  direction.attach(9);
  direction.write(90);
}

void loop() {


  turn_right(1);

  delay(2000);

  turn_left(1);

  delay(4000);

  turn_middle();

  delay(4000);

   turn_right(2);

  delay(2000);

  turn_left(2);

  delay(4000);

/*
  // Fait bouger le bras de 0° à 180°
  for (int position = 0; position <= 180; position++) {
    monServomoteur.write(position);
    delay(15);
  }
  
  // Fait bouger le bras de 180° à 10°
  for (int position = 180; position >= 0; position--) {
    monServomoteur.write(position);
    delay(15);
  }
  */
}





