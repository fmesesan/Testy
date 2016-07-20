package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonTest.class);

    public static WebLocator container = new WebLocator("container");
    public static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(), "//a[contains(concat(' ', @class, ' '), ' x-btn ')]"},
                {new Button().withClasses("ButtonClass"), "//a[contains(concat(' ', @class, ' '), ' x-btn ') and contains(concat(' ', @class, ' '), ' ButtonClass ')]"},
                {new Button(container), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ')]"},
                {new Button(container, "ButtonText"), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (.='ButtonText' or count(*//text()[.='ButtonText']) > 0)]"},
                {new Button(container, "ButtonText").withSearchTextType(SearchType.CONTAINS), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (contains(.,'ButtonText') or count(*//text()[contains(.,'ButtonText')]) > 0)]"},
                {new Button(container).withId("ID"), CONTAINER_PATH + "//a[@id='ID' and contains(concat(' ', @class, ' '), ' x-btn ')]"},

                {new Button(container, "Don't Accept"), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (.=\"Don't Accept\" or count(*//text()[.=\"Don't Accept\"]) > 0)]"},
                {new Button(container, "Don'\"t Accept"), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (.=concat(\"Don'\", '\"', \"t Accept\") or count(*//text()[.=concat(\"Don'\", '\"', \"t Accept\")]) > 0)]"},
                {new Button(container, "It was \"good\" ok!"), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (.='It was \"good\" ok!' or count(*//text()[.='It was \"good\" ok!']) > 0)]"},
                {new Button(container, "Don't do it \"now\" ok!"), CONTAINER_PATH + "//a[contains(concat(' ', @class, ' '), ' x-btn ') and (.=concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\") or count(*//text()[.=concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\")]) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        assertThat(button.getXPath(), equalTo(expectedXpath));
    }
}