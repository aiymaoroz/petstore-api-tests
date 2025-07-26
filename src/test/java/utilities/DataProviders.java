package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//UserData.xlsx";
        XLUtility xl = new XLUtility(path);
        int rowNum = xl.getRowCount("Sheet1");
        int colCount = xl.getCellCount("Sheet1", 1);
        String[][] apiData = new String[rowNum][colCount];
        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colCount; j++) {
                apiData[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }
        return apiData;
    }

    @DataProvider(name = "Usernames")
    public String[] getUsernames() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/UserData.xlsx";
        XLUtility xl = new XLUtility(path);
        int rowNum = xl.getRowCount("Sheet1");
        String[] apidata = new String[rowNum];
        for (int i = 1; i <= rowNum; i++) {
            apidata[i - 1] = xl.getCellData("Sheet1", i, 1);
        }
        return apidata;
    }

    @DataProvider(name = "PetData")
    public Object[][] getPetData() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/PetData.xlsx";
        XLUtility xl = new XLUtility(path);
        int rowNum = xl.getRowCount("Sheet1");
        int colCount = xl.getCellCount("Sheet1", 1);
        Object[][] petData = new Object[rowNum][colCount];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colCount; j++) {
                petData[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }
        return petData;
    }
}