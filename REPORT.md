## SuvestApp

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/TypeActivity.png" width="364" height="604" />

An app used to organize, sort and display screenshots of items that the user provided.

### Design

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DesignFinalPaint.png" width="800" height="500" />

The flow of the application is as follows: A user first adds a screenshot using the floating action button in the MainActivity. They are transferred, using an intent.ACTION_OPEN_DOCUMENT to their device's gallery. There, they can select a screenshot. At this point, the screenshot will be processed by the OCRHelper class. The OCRHelper will try to extract as much information as possible from the image provided. The user will then be transferred to the AddInformationActivity. There, the OCRHelper class will already have filled in any relevant fields that it succeeded in recognizing in the image. The user adds any information they want, and presses 'Next'. The image is then added to the SQLiteDatabase by the DatabaseHelper class.

If the user wants to view images: They will first start in the MainActivity. They will then choose a category (such as 'Clothing'). They will then be transferred to the CategoryActivity. Here, they will be able to choose a type (dependent on which category they choose in the previous activity). They can choose 'Sweaters', for example. The user will then be transferred to the TypeActivity. The TypeActivity will send a SQLite query to the DatabaseHelper class, with the type of screenshots the user requested in the previous two activites (Such as 'Sweaters' from the category 'Clothing'). The DatabaseHelper class will feed the results of the query into the ProductAdapter class, which in turn populates the gridView in the TypeActivity with screenshots. 

If the user wants to filter or sort the images in the TypeActivity, they can press the relevant buttons. A popupMenu will appear, allowing them to make their selection of criteria. A PreferenceHelper object, which contains preferences about which images the user would like to see (such as the 'color' or 'retailer') is then updated with these new criteria. It sends a query to the DatabaseHelper class, which then feeds this new selection of screenshots to the ProductAdapter class. The gridView is refreshed and the new screenshots appear in the TypeActivity. 

### Activities, Classes and Helpers

#### Activities

MainActivity: Is used in making a choice between the different categories of screenshots. The user can also add new images from this Activity using the floating action button.

CategoryActivity: Is used in making a choice between the different types of screenshots available for the category chosen in MainActivity.

TypeActivity: Shows the screenshots which match the criteria set by the user in the MainActivity and TypeActivity. Within this Activity, users can add additional criteria by using the three popupMenu buttons provided. These additional criteria will be contained in a ProductHelper object. They can add and combine any criteria they like. When they start the activity, or after they have provided additional critera, the TypeActivity sends a query request with a ProductHelper object as parameter, to the DatabaseHelper class. This class will then perform a SQLite rawQuery to the database, and return the results to the ProductAdapter class which will populate the gridView with screenshots, using the grid_product.xml layout. Users can also remove a picture from the database by long pressing an image. The gridView will then be refreshed in the same way as described above.

DetailActivity: When the user presses an image in the TypeActivity, they will be transferred to the DetailActivity. If they have provided an URL for the screenshot, they can press the 'Buy' button to be redirected to this webpage. They can remove the image from the database by pressing the 'Remove' button. They will then be transferred back to the TypeActivity.

AddInformationActivity: If the user presses the floating action button in the MainActivity, they will be transferred (using intent.ACTION_OPEN_DOCUMENT) to their device's gallery. After selecting an image, the image will first be processed by the OCRHelper class, which will try to extract as much relevant information from the text in the image, as possible. They are then transferred to the AddInformationActivity. Here, all relevant information gathered by the OCRHelper will already be displayed. The user can now add information. Once they are satisfied, they can press the 'Next' button. A Product object is then initialized, and filled with all the data (such as the color, and the Uri of the image) the user and the OCRHelper provided. This Product object is then transferred to the DatabaseHelper class. Here, the information is extracted from the Product object, and inserted into a SQLite database.

#### Helpers

DatabaseHelper:

OCRHelper:

ProductAdapter:

#### Classes

Product:

PreferenceHelper

