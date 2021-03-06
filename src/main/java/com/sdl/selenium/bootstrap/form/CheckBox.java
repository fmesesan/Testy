package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <label class="checkbox"><input type="checkbox" value="">
 * Stop the process?
 * </label>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * CheckBox checkBox = new CheckBox();
 * checkBox.click();
 * }</pre>
 */
public class CheckBox extends TextField implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox.class);

    public CheckBox() {
        withClassName("CheckBox");
        withType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.CONTAINS);
        withLabelPosition("//");
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        withLabel(boxLabel);
    }

    @Override
    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }

    @Override
    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}