// Basic Bluetooth sketch HC-06_01
// Connect the Hc-06 module and communicate using the serial monitor
//
// The HC-06 defaults to AT mode when first powered on.
// The default baud rate is 9600
// The Hc-06 requires all AT commands to be in uppercase. NL+CR should not be added to the command string
//
 
 
#include <SoftwareSerial.h>
SoftwareSerial BTserial(5, 6); // RX | TX
// Connect the HC-06 TX to the Arduino RX on pin 2. 
// Connect the HC-06 RX to the Arduino TX on pin 3 through a voltage divider.
// 

int LED = 8;
 
void setup() 
{
    pinMode(LED,OUTPUT);

    
    Serial.begin(9600);
    Serial.println("Enter AT commands:");
 
    // HC-06 default serial speed is 9600
    BTserial.begin(9600);  

    BTserial.print("AT+VERSION?"); //Demande le N° de version
    BTserial.print("\r\n"); // sur HC-05, terminer par \r\n
    delay(2000);
    Serial.print( BTserial.read() );  // afficher sur console ce qui est lu sur BT
}
 
void loop()
{

  
    // Keep reading from HC-06 and send to Arduino Serial Monitor
    if (BTserial.available())
    {  
        //Ca commande = BTserial.read();
        //if(commande == "gg")

        int c = BTserial.read();
        Serial.write(c);

        if(c == 'u'){
          digitalWrite(LED,HIGH);
          Serial.println("on");
        }
        if(c == 'd'){
          digitalWrite(LED,LOW);
          Serial.println(c);
          Serial.println("off");
        }
    }
 
    // Keep reading from Arduino Serial Monitor and send to HC-06
    if (Serial.available())
    {
        BTserial.write(Serial.read()+"\r\n");
    }
 
}
