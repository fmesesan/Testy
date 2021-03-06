package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends TextField implements ICombo {
    private static final Logger LOGGER = Logger.getLogger(ComboBox.class);

    public ComboBox() {
        withClassName("ComboBox");
    }

    public ComboBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public ComboBox(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @param value              value
     * @param searchType         true or false
     * @param optionRenderMillis 300ms
     * @return true if value was selected
     * @deprecated use {@link #doSelect(String, SearchType, long)}
     */
    public boolean select(String value, SearchType searchType, long optionRenderMillis) {
        return doSelect(value, searchType, optionRenderMillis);
    }

    /**
     * @param value              value
     * @param searchType         true or false
     * @param optionRenderMillis 300ms
     * @return true if value was selected
     */
    public boolean doSelect(String value, SearchType searchType, long optionRenderMillis) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, searchType, optionRenderMillis);

        if (clickIcon("arrow")) {
            selected = option.click();
            if (selected) {
                LOGGER.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            } else {
                clickIcon("arrow"); // to close combo
            }
            LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            LOGGER.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private WebLocator getComboEl(String value, SearchType searchType, long optionRenderMillis) {
        String classList = "x-boundlist";
        WebLocator comboListElement = new WebLocator(classList).withInfoMessage(this + " -> " + classList);
        return new WebLocator(comboListElement).withText(value, searchType).withRenderMillis(optionRenderMillis).withInfoMessage(value);
    }

    public boolean select(String value, SearchType searchType) {
        boolean selected = doSelect(value, searchType, 500);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    @Override
    public String getValue() {
        String value = null;
        if (clickIcon("arrow")) {
            WebLocator option = getComboEl(null, SearchType.CONTAINS, 300).withClasses("x-boundlist-selected");
            value = option.getText();
            clickIcon("arrow"); // to close combo
        } else {
            LOGGER.debug("(" + this + ") The combo or arrow could not be located.");
        }
        return value;
    }

    @Deprecated
    public boolean assertSelect(String value) {
        return select(value);
    }
}
