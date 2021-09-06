package testcasefunction;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import utils.BasePage;
import utils.Constants;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomePageFunction extends BasePage {
    public static int tag = -1;
    public static int followed = 0;
    public static int likes = 0;
    public static int comments = 0;
    private final By btnNotNow = By.xpath("(//div//button)[2]");
    private final By btnSecondNotNow = By.xpath("(//div[contains(@role,'dialog')]//button)[2]");
    private final By imgFirstThumbNail = By.xpath("(//div[contains(@class,'9AhH0')])[1]");
    private final By imgThumbNail = By.xpath("//article[contains(@role,'presentation')]");
    private final By txtThumbNailUsername = By.xpath("(//article[contains(@role,'presentation')]//div//span//a)[1]");
    private final By btnFollow = By.xpath("(//div[contains(@class,'GdeD6')]//button)[1]");
    private final By btnLikeThumbNail = By.xpath("(//article[contains(@role,'presentation')]//button//span)[1]");
    private final By btnNextThumbNail = By.xpath("//a[contains(@class,'coreSpriteRightPaginationArrow')]");
    private final By inpComment = By.xpath("//textarea[contains(@placeholder,'Yorum ekle')]");
    //private final By inpCommentBlocked = By.xpath("//div[contains(text(),'Bu gönderideki yorumlar sınırlandırıldı')]");
    private final By inpCommentBlocked = By.xpath("//div[contains(@class,'l4b0S')]");
    //private final By btnCommentSubmit = By.xpath("//button[contains(text(),'Paylaş')]");
    private final By btnCommentSubmit = By.xpath("(//button[contains(@class,'y3zKF')])[2]");
    private final By txtPhotoTagsFilter = By.xpath("//a[contains(@class,'xil3i')]");


    List<String> newFollowed = new ArrayList<String>();
    Random randomGenerator = new Random();

    public HomePageFunction(WebDriver driver) {
        super(driver);
    }

    public void letTheGameBegin() throws IOException {
        //Section 1:1 Login information Popup Passing
        if (element(btnNotNow).isDisplayed()) {
            elementCanClickable(btnNotNow).click();
            logger.info("Clicked not now button");
        }
        //Section 1:1 Login information Popup Passing
        //Section 1:2 Open notification Popup Passing
        if (element(btnSecondNotNow).isDisplayed()) {
            elementCanClickable(btnSecondNotNow).click();
            logger.info("Clicked not now button");
        }
        //Section 1:2 Open notification Popup Passing
        //Section 1:3 Get All Tags
        for (int i = 0; i < Constants.arrayTags.length; i++) {
            //Section 2:1 Open added tags
            tag++;
            driver.get("https://www.instagram.com/explore/tags/" + Constants.arrayTags[i] + "/");
            logger.info("Entering named as " + Constants.arrayTags[i] + "  tags");
            //Section 2:1 Open added tags
            //Section 2:2 Click First Image
            try {
                elementCanClickable(imgFirstThumbNail).click();
            } catch (ElementClickInterceptedException e) {
                logger.info("There is problem while clicking photos on tag details");
                //Section 2:2:1 Set Mouse Location 0,0
                try {
                    Robot bot = new Robot();
                    bot.mouseMove(0, 0);
                    logger.info("Mouse location set to 0,0");
                } catch (AWTException ee) {
                    logger.info(ee.getMessage());
                }
                //Section 2:2:1 Set Mouse Location 0,0
                //Section 2:2:2 Refresh Page Try Again
                driver.navigate().refresh();
                logger.info("Page refreshed for try again");
                elementCanClickable(imgFirstThumbNail).click();
                //Section 2:2:2 Refresh Page Try Again
            }
            logger.info("First photo has been found and clicked");
            //Section 2:2 Click First Image
            //Section 3 How many time loop will work for photos
            int tempJump = 0;
            filter:
            for (int j = tempJump; j < 3; j++) {
                String tempUsername = element(txtThumbNailUsername).getText();
                logger.info(tempUsername + " named user has been saved and will use");
                //Section 3:1 Filter of same publisher
                try {
                    List<WebElement> elements = driver.findElements(txtPhotoTagsFilter);
                    tempJump++;
                    for (int k = 0; k < elements.size(); k++) {
                        if (elements.get(k).getText().contains(Constants.CommentTagFilter)) {
                            elementCanClickable(btnNextThumbNail).click();
                            logger.info(Constants.CommentTagFilter + " filter activated. Clicking for next photo");
                            sleepTime(5000);
                            continue filter;
                        }
                    }
                } catch (TimeoutException e) {
                    logger.info(Constants.CommentTagFilter + " filter clear.");
                }
                //Section 3:1 Filter of same publisher
                //Section 3:1:1 Hover photo publisher name
                hoverElement(element(txtThumbNailUsername));
                logger.info("Hover the username");
                sleepTime(5000);
                //Section 3:1:1 Hover photo publisher name
                //Section 3:1:2 Followed Before Check
                if (driver.findElement(btnFollow).getText().contains("Takip Et") || driver.findElement(btnFollow).getText().contains("Follow")) {
                    elementCanClickable(btnFollow).click();
                    newFollowed.add(tempUsername);
                    logger.info(tempUsername + " will add to Excel");
                    followed++;
                    hoverElement(element(imgThumbNail));
                    elementCanClickable(btnLikeThumbNail).click();
                    logger.info("Photo liked !");
                    likes++;
                    sleepTime(5000);
                    try {
                        if (element(inpCommentBlocked).isDisplayed()) {
                            elementCanClickable(btnNextThumbNail).click();
                            continue filter;
                        }
                    } catch (TimeoutException e) {
                        logger.info("There is no restriction on this photo");
                    }
                    elementCanClickable(inpComment).click();
                    int randomInt = randomGenerator.nextInt(Constants.arrayComments.length);
                    sendKeys(inpComment, Constants.arrayComments[randomInt]);
                    elementCanClickable(btnCommentSubmit).click();
                    comments++;
                    sleepTime(5000);
                    logger.info("'"+Constants.arrayComments[randomInt]+"'" + " has been sent to " +tempUsername);
                    elementCanClickable(btnNextThumbNail).click();
                    logger.info("Clicking for next photo");
                } else {
                    hoverElement(element(imgThumbNail));
                    elementCanClickable(btnNextThumbNail).click();
                    logger.info("Clicking for next photo");
                    sleepTime(5000);
                }
                //Section Followed Before Check
            }
            //Section 3 How many time loop will work for photos
        }
        //Section 1:3 Get All Tags
        writeToExcell();
        logger.info("Total like count => " + likes);
        logger.info("Total follow count => " + followed);
        logger.info("Total comment count => " + comments);
    }

    public void writeToExcell() throws IOException {
        logger.info("Records will be write to '" + Constants.Filepath + "' file path.");
        File file = new File(Constants.Filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                logger.info("There is a problem while creating file");
            }
        }

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sh = wb.createSheet("Datas");
            for (int i = 0; i < newFollowed.size(); i++) {
                XSSFRow row = sh.createRow(i);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(newFollowed.get(i));
            }

            FileOutputStream fos = new FileOutputStream(file, true);
            wb.write(fos);
        } catch (Exception e) {
            logger.info("There is a problem while creating sheet");
        } finally {
            wb.close();
        }
    }


}

