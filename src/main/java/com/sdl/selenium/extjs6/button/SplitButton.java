package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
 */
public class SplitButton extends Button {
    private static final Logger LOGGER = LoggerFactory.getLogger(SplitButton.class);

    /**
     * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
     */
    public SplitButton() {
        withClassName("SplitButton");
    }

    public SplitButton(WebLocator container) {
        this();
        withContainer(container);
    }

    public SplitButton(WebLocator container, String text) {
        this(container);
        withText(text);
    }

    public boolean clickOnMenu(String option) {
        return clickOnMenu(new String[]{option});
    }

    public boolean clickOnMenu(String[] menuOptions) {
        int n = menuOptions.length;
        LOGGER.debug("clickOnMenu : " + menuOptions[n - 1]);
        assertReady();
        boolean selected = true;
        if (showMenu()) {
            for (String val : menuOptions) {
                WebLocator option = getComboEl(val, false, 300);
                selected = selected && option.click();
            }
        } else {
            LOGGER.debug("(" + toString() + ") The element arrow could not be located.");
            selected = false;
        }
        return selected;
    }

    public List<String> getAllMenuValues() {
        click();
        WebLocator menuContainer = new WebLocator("x-menu").withAttribute("aria-hidden", "false");
        WebLocator menuList = new WebLocator(menuContainer).withClasses("x-menu-body").withInfoMessage(this + " -> x-menu-body");
        menuList.assertReady();
        String[] menuValues = menuList.getText().split("\\n");
        click();
        return Arrays.asList(menuValues);
    }

    private WebLocator getComboEl(String value, boolean startWith, long optionRenderMillis) {
        WebLocator comboListElement = new WebLocator("x-menu").withAttribute("aria-hidden", "false").withInfoMessage(this + " -> x-menu");
        return new WebLocator(comboListElement).withText(value, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS).withRenderMillis(optionRenderMillis).withInfoMessage(value);
    }
}
