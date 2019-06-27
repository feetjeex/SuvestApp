## SuvestApp

*An apparel screenshot organizer by Felix Mooij*

**Aiming to increase the usability of screenshots in a more crowded online retail enviroment**

Demonstration video: https://youtu.be/9NKXDjXnGEo

#### Purpose of the App

The main goal of this App is to increase the usability of screenshots of items taken by the shopping public. The App will process, organize and display screenshots of products, provided by the user.  


<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/MainActivity1.png" width="364" height="604" /> <img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/AddInformationActivity.png" width="364" height="604" />

On launching the app, the user is presented with the Home menu (left image). Here they can choose to select the type of product they are looking for (a sweater, for example). On this screen, the user can also press the floating action button to add a screenshot to the app (right image). Some of the fields may already have been filled in by the app itself, using the Optical Character Recognition functionality. Once the user presses the 'Next' button, the screenshot, along with the information provided, is added to the database.

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/CategoryActivity.png" width="364" height="604" /> <img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/TypeActivity.png" width="364" height="604" />

Once the user has added one or more screenshots, they can select the category of screenshot they want to view. They will then encounter the Type selection screen (left image). Here they can make a choice of the type of screenshot they want to view, dependent on what they chose in the previous screen. They are then transferred to the List screen (right image). Here they will find all screenshots matching the type they selected in the previous screen. They can also filter the results, on color or retailer. They can also change the order in which the screenshots appear, for example from the highest price to the lowest. If the user performs a longer click one of the results, it will be removed from the app.

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DetailActivity.png" width="364" height="604" />

If the user presses one of the images, they will be taken to the Detail page. Here they can view the screenshot, as well as some information about it, such as the date when it was added to the app. Should they decide to buy the product, and if they provided an URL, they can also press the 'Buy' button to be taken to the relevant website. They can also remove the screenshot by pressing the 'Remove' button.

#### Acknowledgements

This application uses the Text Recognition API, created by Google (https://developers.google.com/vision/android/text-overview).
Used under the Creative Commons Attribution License 4.0. The API was not modified by the Author.

Copyright 2019 Felix Mooij

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
