#include <SoftwareSerial.h> 
SoftwareSerial BTserial(10, 11); // RX (TXD) | TX (RXD) 


void AT(const String cmd){
  BTserial.write("AT+");
  BTserial.print(cmd);
  BTserial.write("\r\n"); // sur HC-05, terminer par \r\n
  /*
  int i = 0;
  while(!BTserial.available() && i < 5){
    delay(1000);
    i++;
  }
  if(i == 5){
    Serial.write(BTserial.read());
  }else{
    Serial.print( BTserial.readString() );  // afficher sur console ce qui est lu sur BT
  }
  */
  
}
void setup() 
{
    pinMode(10, INPUT);
    pinMode(11, OUTPUT);
    Serial.begin(9600);  
    Serial.println("Starting ...");          
    BTserial.begin(9600);
    
    
    BTserial.write("AT+BAUD4");
      BTserial.write("\r\n");

    BTserial.write("AT");
      BTserial.write("\r\n");
     BTserial.write("AT+VERSION");
      BTserial.write("\r\n"); // sur HC-05, terminer par \r\n
    /*
    AT("");
    AT("VERSION");
    AT("NAMEArduino");
    AT("BAUD8");
    */
}
void loop()
{

 
    // Keep reading from HC-06 and send to Arduino Serial Monitor
    if (BTserial.available())
    {  
        //String command = BTserial.readString();
        Serial.write(BTserial.read());
    }
 
    // Keep reading from Arduino Serial Monitor and send to HC-06
    if (Serial.available())
    {
        BTserial.write(Serial.read());
        
    }

    
 
}
