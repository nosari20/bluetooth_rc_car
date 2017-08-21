int pin = 8;

void setup()
{
  pinMode(pin, OUTPUT);

}

void loop(){
  delay(2000); //1 sg 
  digitalWrite(pin, HIGH);
  delay(2000); //1 sg 
  digitalWrite(pin, LOW);
 
}

