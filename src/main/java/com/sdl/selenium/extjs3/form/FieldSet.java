package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldSet extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldSet.class);

    public FieldSet() {
        withClassName("FieldSet");
        withBaseCls("x-fieldset");
        withTag("fieldset");
        withExcludeClasses("x-hide-display", "x-masked");
        withTemplate("text", "count(.//*[normalize-space(text())='%s']) > 0");
    }

    public FieldSet(WebLocator container) {
        this();
        withContainer(container);
    }

    public FieldSet(WebLocator container, String text) {
        this(container);
        withText(text);
    }

    public FieldSet(WebLocator container, String text, boolean isInternationalized) {
        this(container);
        withText(text, isInternationalized);
    }

    public FieldSet(WebLocator container, String cls, String text) {
        this(container, text);
        withClasses(cls);
    }

    public FieldSet(WebLocator container, String cls, String text, boolean isInternationalized) {
        this(container, text, isInternationalized);
        withClasses(cls);
    }

    // methods
    public boolean isCollapsed() {
        String cls = getAttribute("class");
        return cls != null && cls.contains("x-panel-collapsed");
    }

    public boolean expand() {
        WebLocator legendElement = new WebLocator(this).withText(getPathBuilder().getText());
        boolean expanded = !isCollapsed() || legendElement.click();
        if (expanded) {
            Utils.sleep(500);
        }
        return expanded;
    }
}
