# SuvestApp

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/TypeActivity.png" width="364" height="604" />

An app used to organize, sort and display screenshots of items that the user provided.

## Design

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DesignFinalPaint.png" width="800" height="500" />

### High Level Overview

The flow of the application is as follows: A user first adds a screenshot using the floating action button in the MainActivity. They are transferred, using an intent.ACTION_OPEN_DOCUMENT to their device's gallery. There, they can select a screenshot. At this point, the screenshot will be processed by the OCRHelper class. The OCRHelper will try to extract as much information as possible from the image provided. The user will then be transferred to the AddInformationActivity. There, the OCRHelper class will already have filled in any relevant fields that it succeeded in recognizing in the image. The user adds any information they want, and presses 'Next'. The image is then added to the SQLiteDatabase by the DatabaseHelper class.

If the user wants to view images: They will first start in the MainActivity. They will then choose a category (such as 'Clothing'). They will then be transferred to the CategoryActivity. Here, they will be able to choose a type (dependent on which category they choose in the previous activity). They can choose 'Sweaters', for example. The user will then be transferred to the TypeActivity. The TypeActivity will send a SQLite query to the DatabaseHelper class, with the type of screenshots the user requested in the previous two activites (Such as 'Sweaters' from the category 'Clothing'). The DatabaseHelper class will feed the results of the query into the ProductAdapter class, which in turn populates the gridView in the TypeActivity with screenshots. 

If the user wants to filter or sort the images in the TypeActivity, they can press the relevant buttons. A popupMenu will appear, allowing them to make their selection of criteria. A PreferenceHelper object, which contains preferences about which images the user would like to see (such as the 'color' or 'retailer') is then updated with these new criteria. It sends a query to the DatabaseHelper class, which then feeds this new selection of screenshots to the ProductAdapter class. The gridView is refreshed and the new screenshots appear in the TypeActivity. 

### Activities, Classes and Helpers

#### Activities

**MainActivity**: Is used in making a choice between the different categories of screenshots. The user can also add new images from this Activity using the floating action button.

**CategoryActivity**: Is used in making a choice between the different types of screenshots available for the category chosen in MainActivity.

**TypeActivity**: Shows the screenshots which match the criteria set by the user in the MainActivity and TypeActivity. Within this Activity, users can add additional criteria by using the three popupMenu buttons provided. These additional criteria will be contained in a ProductHelper object. They can add and combine any criteria they like. When they start the activity, or after they have provided additional critera, the TypeActivity sends a query request with a ProductHelper object as parameter, to the DatabaseHelper class. This class will then perform a SQLite rawQuery to the database, and return the results to the ProductAdapter class which will populate the gridView with screenshots, using the grid_product.xml layout. Users can also remove a picture from the database by long pressing an image. The gridView will then be refreshed in the same way as described above.

**DetailActivity**: When the user presses an image in the TypeActivity, they will be transferred to the DetailActivity. If they have provided an URL for the screenshot, they can press the 'Buy' button to be redirected to this webpage. They can remove the image from the database by pressing the 'Remove' button. They will then be transferred back to the TypeActivity.

**AddInformationActivity**: If the user presses the floating action button in the MainActivity, they will be transferred (using intent.ACTION_OPEN_DOCUMENT) to their device's gallery. After selecting an image, the image will first be processed by the OCRHelper class, which will try to extract as much relevant information from the text in the image, as possible. They are then transferred to the AddInformationActivity. Here, all relevant information gathered by the OCRHelper will already be displayed. The user can now add information. Once they are satisfied, they can press the 'Next' button. A Product object is then initialized, and filled with all the data (such as the color, and the Uri of the image) the user and the OCRHelper provided. This Product object is then transferred to the DatabaseHelper class. Here, the information is extracted from the Product object, and inserted into a SQLite database.

#### Helpers

**DatabaseHelper**: Helper class which is used to insert, delete and extract entries to and from a SQLite Database, dependent on the choices of the user. Uses a ContentValues object to extract data from the Product object provided by the AddInformationActivity. On request from TypeActivity, sends a query to the database which returns specific rows of the database. The specific rawQuery is determined by a PreferenceHelper object, which contains information on which screenshots the user would like to view. The rows returned by the rawQuery are stored and returned in the form of a Cursor object. Also contains methods to delete rows from the database on request. 

**OCRHelper**: Class which performs the Optical Character Recognition (OCR) functionality on the image provided by the user. The OCR functionality is provied by the Google Text Recognition API. The image provided selected by the user if first converted to a bitmap. The OCR functionality then extracts all the text from the image which is stored in a SparseArray of TextBlock object. For each TextBlock in the SparseArray, the contents (Strings) are split into one word Strings. These Strings are then examined for a number of keywords, such as colors or prices. These keywords are contained in the Enums described below. If any relevant information is found, a newly initialized Product object is updated. After there are no more unexamined TextBlocks left, the updated Product object is returned to the AddInformationActivity.

**ProductAdapter**: An adapter, which uses a Cursor object containing rows from the SQLite database, provided by the DatabaseHelper. For each row in the Cursor, the Adapter fills an grid_product xml file. These are then used to populate the TypeActivity gridView.

#### Classes

**Product**: A class which represent a screenshot. Has a number of fields (such as the color, and the retailer) which can be filled in by the OCRHelper or the user in AddInformationActivity. 

**PreferenceHelper**: A class which contains the preferences of screenshots to be viewed by the user. When initialized by the TypeActivity class, only the type of screenshot is specified (such as sweaters, or jeans, for example). The PreferenceHelper is used by the DatabaseHelper to determine which rows to request from the SQLite Database. Contains a method to reset all fields except for the type, when the user presses the 'Reset' button in the TypeActivity.

##### Enums

**Category**, **Retailer**, **Color**, **TypeAccessoires**, **TypeClothing**, **TypeGifts**, **TypeInterior**, **TypeShoes**, **TypeSport**, **TypeWellness**: Enums which are used to categorize screenshots, and which are used by the OCRHelper to check for relevant information contained in the text in the screenshot. The OCRHelper will iterate over the constants in these Enums (Such as the retailers contained in the Retailer Enum) in order to look for any matches. 


## Challenges

### Biggest Challenge 

I expected the biggest challenge during development to be the OCR Functionality. However, this was actually fairly easy in the end. In the proposal, I had mentioned using the Tesseract API to perform this functionality. After reading how difficult it was to actually implement this in Android, I decided to look a bit further. After a while, I encountered the Google Text Recognition API. This API was already optimized for use in Android, and was quite easy to implement into my application. 

However, the biggest challenge by far turned out to be implementing the mulit-select from the gallery. After Android update 19 (KitKat 4.4) Android uses a new Storage Acces Framework. The intent.ACTION_GET_CONTENT I was using did allow me to select and copy multiple images (represented by Uri's). However, I discovered that I could only use / acces these Uri's once. If I wanted to display them after adding them to the app, I didn't have the proper permissions. After looking around, I found out this was because of the new framework mentioned earlier. Using a new intent.ACTION_OPEN_DOCUMENT I managed to add a flag to the Uri which would give me permanent acces rights. However, I could only add one image at a time using this new intent. In the end, the multi-select functionality is (as far as I know) just not supported using with this type of intent. Eventually, I chose to continue with this less user-friendly but less error prone method of adding images to the app.

### Other changes

In the end, I didn't implement the **AddTypeFromGallery**. The idea was that a user could add a screenshot from the MainActivity, and also from the TypeActivity (where they would already have made choices regarding the category and type of screenshot they would like to add). The difference for the user would be that in the second case, they did not have to fill in the category and the type in the AddInformationActivity screen. These are both selected from drop down spinners, so in my opinion, the time savings would have been quite small had I implemented this extra Activity. Furthermore, I expect users to usually add screenshots from the MainActivity anyway, since they would usually take a screenshot, then open the SuvestApp and be in the MainActivity. They would then probably just press the floating action button instead of first navigating to the proper category and type of screenshot, to add it from there, since that would take more time than just selecting the right type and category from the spinners.

Similarly, I didn't have to add the **AddFromGalleryHelper** / **AddFromGalleryTypeHelper** because it turned out that the intent.ACTION_GET_CONTENT / intent.ACTION_OPEN_DOCUMENT already redirects the user to a 'file explorer' type of activity, where the users can browse through their images. There was no added benefit in creating one of these type of activities myself.

I also had no need to create an **ArrayList** containg the different products. Using an ArrayList would not have been practical, since the contents would be lost on closing the app. The SQLite database I chose to use persists through reboots and closing the app. SQLite also offers built-in timestamp functionality. Using a DatabaseHelper which can send different rawQuery's to the SQLite database allowed me great flexibility in which screenshots were actually shown on the screen in the TypeActivity. 

I did add a **PreferenceHelper** object which was not present in the original Proposal. I wanted the user to be able to sort and add criteria to the screenshots displayed in the TypeActivity. In line with object oriented style of programming I decided to use a new Object class for this. The PreferenceHelper itself contains getters, setters and a method to reset all fields except for the type. This works very smoothly in combination with the buttons in the TypeActivity. After pressing the back button (from TypeActivity to CategoryActivity) and going back to the TypeActivity, the PreferenceHelper object is automatically re-initialized such that all screenshots of the type chosen by the user are shown (without any additional criteria). 

Another addition was a **DatabaseHelper** class. I chose to implement this helper class, because it provides a level of seperation between the Activities and the SQLite database. It is very convenient to just redirect all query's to the database through one class, such that they will always be consistent. 


