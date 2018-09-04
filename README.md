# Vimeo Client
For fun/learning I decided to try and replicate Vimeo's Android app. I decided not to use their Android SDK since I wanted to gain expereince using Retrofit/RxJava, as well as handle Oauth2 login myself.

### Usage
Clone the repository and import the root folder into your IDE (tested on Android Studio). In order for the network calls to work you will need to register as a [Vimeo Developer](https://developer.vimeo.com/) and get your Client ID and Client Secret keys.

From there you will also need to create a new app on the Vimeo developer site, and generate an App Callback Url (required for Oauth2 login).

Once you have these three items, in Android Studio you should add them to your **gradle.properties** file (project's root folder).
* vimeoClientId = < YOUR-CLIENT-ID >
* vimeoClientSecret = < YOUR-CLIENT-SECRET >
* vimeoOauthRedirect = < YOUR-REDIRECT-URL >

The app was created with a **Min SDK Version of 19** and **Target SDK Version of 27**.

### Built With
The app is built using an MVP architecture, and uses:
* [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client for Android and Java
* [RxJava](https://github.com/ReactiveX/RxJava): Reactive Extensions for the JVM
* [Dagger2](https://github.com/google/dagger): A fast dependency injector for Android and Java
* [Butter Knife](https://github.com/JakeWharton/butterknife): Field and method binding for Android views
* [Gson](https://github.com/google/gson): A Java serialization/deserialization library to convert Java Objects into JSON and back
* [Glide](https://github.com/bumptech/glide): An image loading and caching library for Android


### Future Features

* Ability to reply and comment on videos
*  <strike>Ability to follow users/channels</strike>
* User logout
* A View for Categories (currently in the Explore tab they are unclickable)

### Screenshots

<img src="../assets/home.png?raw=true">  <img src="../assets/explore.png?raw=true">  <img src="../assets/search_results_tab3.png?raw=true">
</br>
<img src="../assets/video_tab2.png?raw=true">  <img src="../assets/oauth_login.png?raw=true">  <img src="../assets/user_profile.png?raw=true">

### License
    Copyright (C) 2018 Armon Khosravi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License. 
