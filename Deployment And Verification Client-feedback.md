## Development Environment
This Project built with default latest Android Studio & SDK
Android SDK Version Compiler 27
Testing device: Android 7.0.0
OS Windows 10

Android Application Source Location 	VA-kidney\va-kidney-android\VAKidney

## Video Demo
- `https://drive.google.com/file/d/1wx2x61l9Z5hiK9I54xSGmVL9Qn88JQvA/view?usp=sharing`


Existing files :(This files are created before the challenge and is not deleted)
VA-kidney\va-kidney-android\designs
VA-kidney\va-kidney-android\docs
VA-kidney\va-kidney-android\demovideo.mp4
VA-kidney\va-kidney-android\Deployment and Verfication Guide.docx
VA-kidney\va-kidney-android\README.md

## Building the app
1. Install Android Studio & Android SDK
2. Open Android Studio and Import the project.
3. Install Latest Android SDK Tools and Android SDK Platform API 27 from SDK Manager
4. Create Android Emulator AVD from AVD Manager. Choose Android with Target Android 5.0 Lolipop or newer.
   Make sure you choose Google APIs Target.
5. Run the project.

Or
  directly install app by copying app-debug.apk from VA-kidney\app-debug.apk to your phone.
  make sure your phone has Security->Unknown Sources enabled.
  Click on the app-debug.apk and click install

## Applying the Patch file
	clone the repository https://github.com/topcoderinc/va-kidney-android
	copy the va_client_feedback.diff to the root of your repository open git command window there.
	git apply --reject --whitespace=fix va_client_feedback.diff

#### Open Source Project used in this project:
- com.github.PhilJay:MPAndroidChart:v3.0.3
- Glide
- Retrofit 2
- Sugar ORM

#### Open API used in this project:
- FDA API
- USDA NDB API

## Testing Account
- Email: email@email.com
- Password: demo

These credentials can be changed from UserData.json file in the assets folder.


## Configurations
We can cofigure the FDA API and USDA NDB API endpoint and API key by editing the build.gradle file `app/build.gradle`
- `final FDA_BASE_URL = "https://api.fda.gov/"`
- `final NDB_BASE_URL = "https://api.nal.usda.gov/"`
- `final FDA_API_KEY = "M19F7ValXdbz2jGtnLIEG77VgNyw4TQS4JXOY6hW"`
- `final NDB_API_KEY = "RRsuimKHTOocPYhmzonbv03mIqN3CB7OGzaQtvlE"`

Please make sure to disable Android Studio `Instant Run` feature.


## Video Demo
- `https://drive.google.com/file/d/1wx2x61l9Z5hiK9I54xSGmVL9Qn88JQvA/view?usp=sharing`

The List of fixed issuses
1)Code Quality Issues
	https://github.com/topcoderinc/va-kidney-android/issues/42
	i)All package names are made to lower case.
	ii)All classes are properly formatted.
	iii)Adapters are either converted from BaseAdapter to RecyclerAdapter or View Holder Pattern is implemented.
	iv)Third-Party library(com.makeramen:roundedimageview:2.3.0) was removed instead Glide existing library was used.

2)Profile Pic Disappears On Tapping Else where on the screen
	https://github.com/topcoderinc/va-kidney-android/issues/41

	When ever new Image selection dialog is opened the existing image was set null.
	Fixed this issue.

3)FDA & USDA services should be effectively cached
   https://github.com/topcoderinc/va-kidney-android/issues/40

	Added Caching mechanism in retro fit instance. api/FDARestClient.java & api/NDBRestClient.java
	Now it caches data for 1 day with a maximum cache size of 20MB.

4)Add obligatory measurements based off of comorbid conditions
	https://github.com/topcoderinc/va-kidney-android/issues/37

	Added obligatory measurements and corresponding daily measurements in the home screen with follow up chart.

5)Enlarge Goals Icon
	https://github.com/topcoderinc/va-kidney-android/issues/36

	Goal icon has been enlarged corresponding to the ios app

6)Food Measurement Changes
	https://github.com/topcoderinc/va-kidney-android/issues/35

	Food Measurement such as "oz (mass)", "oz (fluid)", "g", "mg", "L", "mL", "lb", "st", "cups", "pints"
	which was added to ios app has been added to android app.

7)Increase font by 2 pt for Login & History&Metrics screens
	https://github.com/topcoderinc/va-kidney-android/issues/34

	Font size by 2px has been increased.

8)Under Drug Interactions, change Drug Consumption to Medications
	https://github.com/topcoderinc/va-kidney-android/issues/33

	 Changed 'Drug Consumption' to 'Medications'

9)Profile Changes
	https://github.com/topcoderinc/va-kidney-android/issues/32
	discussion on
	https://github.com/topcoderinc/va-kidney-ios/issues/64

	Changed the following

	Under Profile, change Are you In Dialysis to Are you Receiving Dialysis.
	Change Disease Category to Renal Disease Stage.
	Change Do you Want to Set Up Goals to Do You Want to Add/Change Goals.
	For weight, allow the user to type it in rather than scroll.

	Added Goal Setup with two options 'Generate Goals' and 'Rest All Goals'
	'Generate Goals'
		Generates Goal from GoalGenerator class
	fixed duplication of goals by making them regenerated only when they are deleted
	fixed Add Toast as a feedback for user to let them know goals have been generated.
	'Rest All Goals'
		Added Confirmation before deletion
		Added Toast as a feedback that goals have been deleted.
		Once all goals have been reset when user taps this button again it toasts "No goals to reset"

10)Add Meal Changes
	https://github.com/topcoderinc/va-kidney-android/issues/31

	AutoText completion have been added.
	A threshold of three letters is kept and 10 suggestions will be displayed to the user at a time.
	No of suggestions can be changed in NDBServiceAPI.java replace max=10 with your desired value
	Threshold can be changed in AddMealDrugPopup.java in line 63 by changing the value of AUTO_COMPLETION_THRESHOLD variable
	Fixed suggestions food suggestions were displayed for drop popup also

11)Filtering for Food/ Drug Intake
	https://github.com/topcoderinc/va-kidney-android/issues/30

	Dialog boxes for filtering has been added.
	Corresponding query functions has been added . getMealUsingFilter function in the Meal class queries the sqlite database.
	Filter Icon corresponding to the ios app has been added visible only in the Food/Drug Intake page.

12)Goal Changes
	https://github.com/topcoderinc/va-kidney-android/issues/29
	fixed the following:
		Under food goals, Bowls should be Servings.
		Meat should be Oz not Pieces.
		Fluid should be Oz not Glasses
		Alcohol should be removed
		Run should be Workout and Minutes not miles.

13)Change Recommendation Icon
	https://github.com/topcoderinc/va-kidney-android/issues/28

	Added Recommendations Icon similar to ios app and fixed text wrapping issue.

14) Profile Icon resets whenever app is closed.

	Added functionality to save the profile icon.

15) Fixed Issues with text cropping in Welcome page and in Arc Progress Icon.

	Added Scroll View in Welcome Page 1.
	Width of arc progress is increased so that bottom text doesn't get cropped.

16) Use Data Binding
	https://apps.topcoder.com/forums/?module=Thread&threadID=919510&start=3

	Data Binding was added to the entire project
	Removed all View variables which are not required and initView method.

17) Fixed Add New Goal button crashes when all goals where reset.