/**
 * Contains a vibration sensor. Shows the vibration level by the LEDs when vibrating.
 * 
 * ========
 * 
 * Qianlang Chen
 * u1172983
 * H 09/27
 * v1.0.0
 */

/** The pin numbers of the LEDs. **/
int leds[] = {2, 3, 4, 5, 6};

/** The pin number of the vibration sensor (MEAS). **/
int meas = A0;

/** Program entry. **/
void setup() {
	Serial.begin(9600);
	
	// Initialize LEDs
	for (int led : leds) {
		pinMode(led, OUTPUT);
	}
}

/** The main loop. **/
void loop() {
	int pressureLimit = 40;
	int pressure = getInputPressure(pressureLimit);
	int percentage = pressure * 100 / pressureLimit;
	
	if (pressure > 0) {
		Serial.println("Pressure: " + percentage + "%"); // output the pressure on screen
	}
	
	int ledLevel = pressure / 2; // 2 levels of pressure per LED
	
	animate(ledLevel);
}

/**
 * Returns the value of the vibration level of the sensor.
 * 
 * Params:
 * 		int limit - The upper limit expected to return.
 * 
 * Returns:
 * 		A value from 0 to limit indicating the vibration level.
 */
int getInputPressure(int limit) {
	int pressure = analogRead(meas);

	return pressure > limit ? limit : pressure;
}

/** Records if the animation already playing. **/
bool isInAnimation = false;

/** The starting time of each LED's animation. **/
long startTime;

/**
 * Plays a little animation of the LEDs (turns a number of LEDs on in order,
 * then turns them all off in order).
 * 
 * Params:
 * 		int ledLevel - The number of LEDs to animate.
 */
void animate(int ledLevel) {
	if (isInAnimation) {
		return; // avoid animations overlapping (multiple animations play at once)
	}
	
	isInAnimation = true;

	// Turn off all LEDs first.
	for (int led : leds) {
		digitalWrite(led, LOW);
	}
	
	// Turn on LEDs in order.
	for (int i = 0; i < ledLevel; i++) {
		while (millis() - startTime < 100)
			delay(10); // avoid too much checking
		
		startTime = millis();
		digitalWrite(leds[i], HIGH);
	}
	
	// Turn off LEDs in order.
	for (int i = 0; i < ledLevel; i++) {
		while (millis() - startTime < 100)
			delay(10); // avoid too much checking
		
		startTime = millis();
		digitalWrite(leds[ledLevel - i - 1], LOW);
	}
	
	isInAnimation = false;
}
