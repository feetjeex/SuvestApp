## Suvest
*An apparel screenshot organizer by Felix Mooij*

<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DesignDocument.png" width="750" height="600" />


<img src="https://github.com/feetjeex/SuvestApp/blob/master/doc/DesignUML.png" width="750" height="600" />

### Classes to be used (so far):

| Product                  |
|--------------------------|
| Category: String         |
| Type: String             |
| Price: Float             |
| Retailer: String         |
| Url: String              |
| DateAdded: String        |
| Image: Bitmap            |
| ________________________ |
| AddToProductArrayList()  |
| Getters()                |
| Setters()                |



| ProductArraylist         |
|--------------------------|
| Products: ArrayList<>    |
| ________________________ |
| GetSize()                |

### API:

This App will use the Tesseract API in order to use Optical Character Recognition (OCR) on the screenshots provided by the user. 
https://github.com/tesseract-ocr/tesseract
