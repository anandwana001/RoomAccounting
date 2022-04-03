# RoomAccounting

**RoomAccounting** app is a simple invoice accounting application. Based on the local database with the CRUD operation.

## Architecture 📐
Project is a single module consist of multiple packages in order to achieve MVVM architecture.
* data
* di
* repository
* ui
  * base
  * binding
  * home
  * recyclerview
* utility

## Features 🦿
* Pre-populating Data from local asset.
* User can create a complete invoice with a list of items.
* User can edit saved invoice.
* User can create and save a draft invoice.
* User can delete the invoice.

## Android/Kotlin Beauty 🫀 🫁 🧠
* BindingAdapters
* Custom Moshi Adapters
* Retrieving and loading local JSON asset
* Date Formatting
* Generic RecyclerView based on ViewModel
* Hilt separate module for testing and production
* Room Type Converters
* Drawable states for button clicks
* Preloaded fonts
* SharedTest support
* Unit Testing
* Instrumentation Testing with Hilt for Fragments in isolation
* End to End Testing
* Custom Matchers and ViewActions for adequate testing

## Tech-stack 🛠
This project use multiple libraries to bring easy way of implementation
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection Framework
- [Room](https://developer.android.com/training/data-storage/room) - Used for Local Database
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - to perform asynchronous work
- [Moshi](https://github.com/square/moshi) - use to serialize and deserialize Java objects to JSON
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - observable data holder
- [AndroidX](https://developer.android.com/jetpack/androidx) - android library for core functionalities
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - manage data in lifecycle aware fashion
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) - helps to bind UI with data source
- [Espresso](https://developer.android.com/training/testing/espresso) - Instrumentation Testing
- [Robolectric](http://robolectric.org/) - Unit Testing

## Screenshot 📱
<p>
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181634.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181640.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181645.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181648.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181652.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181657.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181702.png" height=450 width=230 />
<img src="https://github.com/anandwana001/RoomAccounting/blob/main/screenshots/AndroidAppDarkMode/Screenshot_20220310-181708.png" height=450 width=230 />
</p>

## Contact 🔗
Let's connect here -> [webprofile](https://anandwana001.github.io)

## License 📝
```
MIT License

Copyright (c) 2022 Akshay Nandwana

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
