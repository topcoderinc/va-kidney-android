## Development Environment
This Project built with default latest Android Studio & SDK
Android SDK Version Compiler 27
Testing device: Android 8.0.0


## Building the app
1. Install Android Studio & Android SDK
2. Open Android Studio and Import the project.
3. Install Latest Android SDK Tools and Android SDK Platform API 27 from SDK Manager
4. Create Android Emulator AVD from AVD Manager. Choose Android with Target Android 5.0 Lolipop or newer.
   Make sure you choose Google APIs Target. I recommend using Target Android 7 (Google Play).
5. Run the project.

#### Google Fit Integration
This project uses Google Fit API for Android.
You need to follow this instruction to make sure the app works properly.
https://developers.google.com/fit/android/get-started

#### Open Source Project used in this project:
- com.makeramen:roundedimageview:2.3.0
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

## Troubleshooting
If you have trouble with running the app. Please make sure to disable Android Studio `Instant Run` feature.


## Video Demo
- https://www.youtube.com/watch?v=qEo-WWaKojs
Previous Failed Challenge


- https://drive.google.com/open?id=1hWgqu5EWmL-cyAtjAZw9f3_gSW02XU5C
For issues:
https://github.com/topcoderinc/va-kidney-android/issues/14
https://github.com/topcoderinc/va-kidney-android/issues/12


- https://drive.google.com/file/d/1oJqLlLC667YEiRD_LNMi_nuJFVWI-0Qm/view?usp=sharing
For issues:
https://github.com/topcoderinc/va-kidney-android/issues/10
https://github.com/topcoderinc/va-kidney-android/issues/9
https://github.com/topcoderinc/va-kidney-android/issues/4
https://github.com/topcoderinc/va-kidney-android/issues/5


- https://drive.google.com/file/d/1X5ZpQX1f4qJ9kfGX_8gmA6tHEDLiOjN8/view?usp=sharing
For issues:
https://github.com/topcoderinc/va-kidney-android/issues/7
https://github.com/topcoderinc/va-kidney-android/issues/11


- https://drive.google.com/open?id=1gRx9I3V36eD4EeTfXUZuwQpC4-HqxP0f
For issues:
https://github.com/topcoderinc/va-kidney-android/issues/13


- No video for
https://github.com/topcoderinc/va-kidney-android/issues/8
Since the NDB API goes down.
As confirmed in https://apps.topcoder.com/forums/?module=Thread&threadID=915143&start=0
