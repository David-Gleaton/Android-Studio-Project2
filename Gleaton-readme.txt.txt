H. David Gleaton
C88379585

How to load and run the program:
	1. Have a Pixel 2 with Oreo level API AVD created
	2. Extract .zip
	3. Import to Android Studio project from extracted file
	4. Run on AVD in Android Studio

Justifications for Strategies
	1. Random - A random decision model that rolls a random int and banks or rolls
	2. Normal - A slightly worse version of Cut Throat, where the cut off is 5% of the target value for banking.
			Since it is still a good strategy to not roll forever and bank often, it is an average difficulty.
	3. Cut Throat - Taken from durangobill.com, this write up statisitcally attempts to prove the best way to play.
			They found that, in a game to 100, rolling to 15 and then banking is the most statistically sound way to play.
			As a result, I implemented that the banker will attempt to roll to 15% of the target value and then bank.
			Its made for some tight games, as I have tested!

Additional Credits
	1. Zybooks
		-I used and referenced the Zybooks dice roller app in order to have the dice appear and disappear
		-I used and referenced the Spinner section in the Zybooks Wigets section
		-I used and referenced the fragments sectino in the Zybooks Wigets section
	2. Dialogs - Android Studio
		-I repeatedly made use of the Dialogs section when building my Dialogs sections
	3. Title Image
		-Taken from clipart-library.com, 'Free Pig Clipart #1294757' is free for personal use
	4. Dice Image
		-Taken from wikimedia.org for personal use, I further edited the colors to fit the scheme of the app.