# *NewsReader_clean_mvp*

**NewsReader_clean_mvp** is an android app that read top news from nytimes.

## Technologies are using here:
* Support libraries
* Material design libraries
* RecyclerView
* Data binding
* RxJava 2 and RxAndroid 2
* Retrofit 2
* Gson 2.8
* Butterknife
* Glide
* Functional tests with JUnit
* UI testing with Espresso and Uiautomator
* Mockito

## Architectures, two implementations MVP and MVVM, both of them based on Clean architecture:
[Clean architecture](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029), sperate android project into 3 different layers, which are presenter, domain and data. Dependency Rule: source code dependencies can only point inwards and nothing in an inner circle can know anything at all about something in an outer circle.
<img src='https://github.com/usmeng/NewsReader_clean_mvp/blob/master/material/clean.jpg' />

[MVP](https://github.com/usmeng/android-boilerplate), the architecture of our Android apps is based on the MVP (Model View Presenter) pattern.

* **View (UI layer)**: this is where Activities, Fragments and other standard Android components live. 

* **Presenter**: presenters subscribe to RxJava Observables provided by the DataManager. 

* **Model (Data Layer)**: this is responsible for retrieving, saving, caching and massaging data. 
<img src='https://github.com/usmeng/NewsReader_clean_mvp/blob/master/material/mvp.jpg' />

[MVVM](https://developer.android.com/topic/libraries/architecture/guide.html), M represents Model, V represents View and VM means ViewModel.
* The most important thing you should focus on is the separation of concerns in your app. 
* The second important principle is that you should drive your UI from a model, preferably a persistent model.
<img src='https://github.com/usmeng/NewsReader_clean_mvp/blob/master/material/mvvm.jpg' />

## License

    Copyright [2017] [Meng Zhou]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
