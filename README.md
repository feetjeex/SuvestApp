## Suvest
*An apparel screenshot organizer by Felix Mooij*

**Aiming to increase the usability of screenshots in a more crowded online retail enviroment**


#### Problem setting
As online shopping becomes increasingly popular, the number of different online retailers also increases. Many retailers offer users a posibility of "liking" a certain product, which is then saved in a specific location on the site for easy access at a later time. However, on this location only products from that particular retailer are stored. As such, many users will have a number of lists of "liked" products at different retailers. 

This is inconvenient in the sense that there is no straighforward method to get an overview of all the products the user "liked" across different retailers. These lists of "liked" products are also generally not sorted on type of product, but on the date on which the product was liked.

#### Current workaround
A workaround for this problem is to take a screenshot of the product the user is interested in revisiting at a later time. This method also has drawbacks, however. The screenshot can easiliy get lost between the other media objects in the pictures folder. The screenshot also offers limited information on the product itself. The user has to figure out which retailer sells the product, based on elements of the picture such as the font used, the background color and the brand. Additionally, the specific URL at which the user can actually purchase the product is also not provided.

#### Proposed solution
 The main goal of this App is to increase the usability of these screenshots for the shopping public. The App will process, organize and display the screenshots of products, provided by the user. A user will encounter a product that they want to save for later, on the mobile website of an online retailer. The user will make a screenshot of this product. 
 
 At this point, the user can upload the screenshot into the App. There, they will provide some information on the screenshot, such as the type of product, using a number of quick and convenient multiple choice forms. However, the aim of the App is to automate as much of this process as possible. Using an Optical Character Recognition (OCR) API, the App will strive to extract most of the necessary information from the screenshot. The screenshot will then be stored in the proper section of the App. The user can also take a number of screenshots, launch the app, and subsequently select the screenshots to be uploaded into the App.
 
 Once the user wants to revisit the product, they launch the App. They will be able to select the type of product they are looking for (a sweater, for example). They will be presented with a scrollable list of all the screenshots of this type of product currently stored in the App. They will be able to select the item they are looking for (A sweater by Asos, for example). They will then be able to view the screenshot, along with some other relevant fields (such as the name of the retailer, the price and the date the screenshot was made, for example). A button to redirect the user to the relevant page in the webshop will also be present, should they decide to buy the product.
 
### Main Features 

- Allow users to scroll through all of the screenshots of a particular type of product **(MVP)**

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/Pic1.png" width="600" height="200" />

- Allow users to add a screenshot to the library in the application after selecting that screenshot in their gallery **(MVP)**

- Allow users to make adjustments to which screenshots are shown of a particular type of product (such as a maximum price, for example)

- Allow users to delete screenshots from the library in the application **(MVP)**

- Notify the user if a product in their library is discounted by the retailer

- Allow users to add multiple screenshots at once, from within the App **(MVP)**

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/Pic2.png" width="700" height="300" />


### Prerequisites

#### Data sources:

- The App will use the screenshots made by the user as a main data input

#### External components:

- The App will use an OCR API, such as Tesseract (https://github.com/tesseract-ocr/tesseract

#### Similar mobile apps:

- Perhaps Pinterest is slightly similar, in the sense that users can also collect product by many different retailers, though Pinterest does not employ any OCR to determine characteristics of the image.

#### Hardest parts:

- The optical character recognition will probably be the hardest part of this project.
