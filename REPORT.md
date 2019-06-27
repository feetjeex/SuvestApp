## SuvestApp

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/TypeActivity.png" width="364" height="604" />

An app used to organize, sort and display screenshots of items that the user provided.

### Design

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DesignFinalPaint.png" width="800" height="500" />

The flow of the application is as follows: A user first adds a screenshot using the floating action button in the MainActivity. They are transferred, using an intent.ACTION_GET_CONTENT to their device's gallery. There, they can select a screenshot. At this point, the screenshot will be processed by the OCRHelper class. The OCRHelper will try to extract as much information as possible from the image provided. The user will then be transferred to the AddInformationActivity. There, the OCRHelper class will already have filled in any relevant fields that it succeeded in recognizing in the image. The user adds any information they want, and presses 'Next'. The image is then added to the SQLiteDatabase by the DatabaseHelper class.

If the user wants to view images: They will first start in the MainActivity. They will then choose a category (such as 'Clothing'). They will then be transferred to the CategoryActivity. Here, they will be able to choose a type (dependent on which category they choose in the previous activity). They can choose 'Sweaters', for example. The user will then be transferred to the TypeActivity. The TypeActivity will send a SQLite query to the DatabaseHelper class, with the type of screenshots the user requested in the previous two activites (Such as 'Sweaters' from the category 'Clothing'. The DatabaseHelper class will feed the results of the query into the ProductAdapter class, which in turn populates the gridView in the TypeActivity with screenshots. 

If the user wants to filter or sort the images in the TypeActivity, they can press the relevant buttons. A popupMenu will appear, allowing them to make their selection of criteria. A PreferenceHelper object, which contains preferences about which images the user would like to see (such as the 'color' or 'retailer') is then updated with these new criteria. It sends a query to the DatabaseHelper class, which then feeds this new selection of screenshots to the ProductAdapter class. The gridView is refreshed and the new screenshots appear in the TypeActivity. 
