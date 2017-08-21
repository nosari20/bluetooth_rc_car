const int B = 8;
const int G = 12;
const int R = 13;

const int DELAY = 100;

void setup() {
    pinMode(B,OUTPUT);
    pinMode(G,OUTPUT); 
    pinMode(R,OUTPUT); 
}

void loop() {

  /*
  digitalWrite(B,HIGH);
  digitalWrite(G,HIGH);
  digitalWrite(R,HIGH);

  delay(500);
  */

  digitalWrite(B,HIGH);
  digitalWrite(G,LOW);
  digitalWrite(R,LOW);

  delay(DELAY);

  digitalWrite(B,LOW);
  digitalWrite(G,HIGH);
  digitalWrite(R,LOW);

  delay(DELAY);

  digitalWrite(B,LOW);
  digitalWrite(G,LOW);
  digitalWrite(R,HIGH);

  delay(DELAY);

  /*
  digitalWrite(B,LOW);
  digitalWrite(G,LOW);
  digitalWrite(R,LOW);
  

  delay(DELAY);
  */

  

}
