package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Pattern;

public class PathBuilder {

    private static final Logger LOGGER = Logger.getLogger(PathBuilder.class);

    public PathBuilder() {

    }

    public static void main(String[] args) {
        PathBuilder pathBuilder = new PathBuilder(By.id("ID"));
//        pathBuilder.setElPathSuffix("iconCls", "count(.//*[contains(@class, '%s')]) > 0", "save");
        pathBuilder.setElPathSuffix("iconCls", "count(.//*[contains(@class, 'true')]) > 0");
        LOGGER.debug(pathBuilder.getPath());
    }

    /*public PathBuilder(By... init) {
        for (By by : init) {
            by.init(this);
        }
    }*/

    public PathBuilder(By... bys) {
        init(bys);
    }

    public void init(By ...bys) {
        for (By by : bys) {
            by.init(this);
        }
    }

    public void defaults(By ...bys) {
        for (By by : bys) {
            by.initDefault(this);
        }
    }

    private String className = "WebLocator";
    private String tag = "*";
    private String id;
    private String elPath;
    private String baseCls;
    private String cls;
    private List<String> classes;
    private List<String> excludeClasses;
    private String name;
    private String text;
    protected List<SearchType> defaultSearchTextType = new ArrayList<SearchType>();
    private List<SearchType> searchTextType = WebLocatorConfig.getSearchTextType();
    private List<SearchType> searchLabelType = new ArrayList<SearchType>();
    private String style;
    private String elCssSelector;
    private String title;
    private Map<String, String> pathSuffixs = new HashMap<String, String>();

    private String infoMessage;

    private String label;
    private String labelTag = "label";
    private String labelPosition = "//following-sibling::*//";

    private int position = -1;

    //private int elIndex; // TODO try to find how can be used

    private boolean visibility;
    private long renderMillis = WebLocatorConfig.getDefaultRenderMillis();
    private int activateSeconds = 60;

    private WebLocator container;

    // =========================================
    // ========== setters & getters ============
    // =========================================

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setTag(String)}
     *         <p>tag (type of DOM element)</p>
     *         <pre>default to "*"</pre>
     */
    public String getTag() {
        return tag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param tag (type of DOM element) eg. input or h2
     * @return this element
     */
    public <T extends PathBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setId(String)}
     */
    public String getId() {
        return id;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param id eg. id="buttonSubmit"
     * @return this element
     */
    public <T extends PathBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElPath(String)}
     *         <p>returned value does not include containers path</p>
     */
    public String getElPath() {
        return elPath;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elPath absolute way (xpath) to identify element
     * @return this element
     */
    public <T extends PathBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setBaseCls(String)}
     */
    public String getBaseCls() {
        return baseCls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param baseCls base class
     * @return this element
     */
    public <T extends PathBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setCls(String)}
     */
    public String getCls() {
        return cls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Find element with <b>exact math</b> of specified class (equals)</p>
     *
     * @param cls class of element
     * @return this element
     */
    public <T extends PathBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     * <p>Example:</p>
     * <pre>
     *     WebLocator element = new WebLocator().setClasses("bg-btn", "new-btn");
     * </pre>
     *
     * @return value that has been set in {@link #setClasses(String...)}
     */
    public List<String> getClasses() {
        return classes;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Use it when element must have all specified css classes (order is not important).</p>
     * <ul>
     * <li>Provided classes must be conform css rules.</li>
     * </ul>
     *
     * @param classes list of classes
     * @return this element
     */
    public <T extends PathBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = Arrays.asList(classes);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setExcludeClasses(String...)}
     */
    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param excludeClasses list of class to be excluded
     * @return this element
     */
    public <T extends PathBuilder> T setExcludeClasses(final String... excludeClasses) {
        if (excludeClasses != null) {
            this.excludeClasses = Arrays.asList(excludeClasses);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setName(String)}
     */
    public String getName() {
        return name;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param name eg. name="buttonSubmit"
     * @return this element
     */
    public <T extends PathBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setText(String, SearchType...)}
     */
    public String getText() {
        return text;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param text       with which to identify the item
     * @param searchType type search text element: see more details see {@link SearchType}
     * @return this element
     */
    public <T extends PathBuilder> T setText(final String text, final SearchType... searchType) {
        this.text = text;
        if (searchType != null) {
            setSearchTextType(searchType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setSearchTextType(SearchType...)}
     */
    public List<SearchType> getSearchTextType() {
        return searchTextType;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param searchTextType accepted values are: {@link SearchType#EQUALS}
     * @return this element
     */
    public <T extends PathBuilder> T setSearchTextType(SearchType... searchTextType) {
        if(searchTextType == null) {
            this.searchTextType = WebLocatorConfig.getSearchTextType();
        } else {
            this.searchTextType = new ArrayList<SearchType>();
            Collections.addAll(this.searchTextType, searchTextType);
        }
        this.searchTextType.addAll(defaultSearchTextType);
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param searchLabelType accepted values are: {@link SearchType}
     * @return this element
     */
    public <T extends PathBuilder> T setSearchLabelType(SearchType... searchLabelType) {
        this.searchLabelType = new ArrayList<SearchType>();
        if (searchLabelType != null) {
            Collections.addAll(this.searchLabelType, searchLabelType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setStyle(String)}
     */
    public String getStyle() {
        return style;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param style of element
     * @return this element
     */
    public <T extends PathBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    /**
     * <p><b>not implemented yet</b></p>
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElCssSelector(String)}
     */
    public String getElCssSelector() {
        return elCssSelector;
    }

    /**
     * <p><b>not implemented yet</b></p>
     * <p><b>Used for finding element process (to generate css address)<b></p>
     *
     * @param elCssSelector cssSelector
     * @return this element
     */
    public <T extends PathBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setTitle(String)}
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param title of element
     * @return this element
     */
    public <T extends PathBuilder> T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElPathSuffix(String)}
     */
    public String getElPathSuffix() {
        return pathSuffixs.get("elPathSuffix");
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Example:</p>
     * <pre>
     *     TODO
     * </pre>
     *
     * @param elPathSuffix additional identification xpath element that will be added at the end
     * @return this element
     */
    public <T extends PathBuilder> T setElPathSuffix(String elPathSuffix) {
        setElPathSuffix("elPathSuffix", elPathSuffix);
        return (T) this;
    }

    public <T extends PathBuilder> T setElPathSuffix(String key, String value, Object ...arguments) {
        if(value == null) {
            pathSuffixs.remove(key);
        } else {
            //value = MessageFormat.format(value, arguments);
            value = String.format(value, arguments);
            pathSuffixs.put(key, value);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used in logging process</i><b></p>
     *
     * @return value that has been set in {@link #setInfoMessage(String)}
     */
    public String getInfoMessage() {
        return infoMessage;
    }

    /**
     * <p><b><i>Used in logging process</i><b></p>
     *
     * @param infoMessage info Message
     * @return this element
     */
    public <T extends PathBuilder> T setInfoMessage(final String infoMessage) {
        this.infoMessage = infoMessage;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setVisibility(boolean)}
     */
    public boolean isVisibility() {
        return visibility;
    }

    public <T extends PathBuilder> T setVisibility(final boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    public long getRenderMillis() {
        return renderMillis;
    }

    public <T extends PathBuilder> T setRenderMillis(final long renderMillis) {
        this.renderMillis = renderMillis;
        return (T) this;
    }

    public <T extends PathBuilder> T setRenderSeconds(final int renderSeconds) {
        setRenderMillis(renderSeconds * 1000);
        return (T) this;
    }

    public int getActivateSeconds() {
        return activateSeconds;
    }

    public <T extends PathBuilder> T setActivateSeconds(final int activateSeconds) {
        this.activateSeconds = activateSeconds;
        return (T) this;
    }

    // TODO verify what type must return
    public WebLocator getContainer() {
        return container;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param container parent containing element.
     * @return this element
     */
    public <T extends PathBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabel(String, SearchType...)}
     */
    public String getLabel() {
        return label;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param label text label element
     * @return this element
     */
    public <T extends PathBuilder> T setLabel(String label, final SearchType... searchType) {
        this.label = label;
        if (searchType != null) {
            setSearchLabelType(searchType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabel(String, SearchType...)}
     */
    public String getLabelTag() {
        return labelTag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param labelTag label tag element
     * @return this element
     */
    public <T extends PathBuilder> T setLabelTag(String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabelPosition(String)}
     */
    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param labelPosition position of this element reported to label
     * @return this element
     * @see <a href="http://www.w3schools.com/xpath/xpath_axes.asp">http://www.w3schools.com/xpath/xpath_axes.asp"</a>
     */
    public <T extends PathBuilder> T setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setPosition(int)}
     */
    public int getPosition() {
        return position;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[contains(@class, 'x-grid-panel')][position() = 1]
     * </pre>
     *
     * @param position starting index = 1
     * @return this element
     */
    public <T extends PathBuilder> T setPosition(int position) {
        this.position = position;
        return (T) this;
    }

    // =========================================
    // =============== Methods =================
    // =========================================

    /**
     * <p>Used only to identify class type of current object</p>
     * <p> Not used for css class!</p>
     *
     * @return string
     */
    public String getClassName() {
        return className;
    }

    protected void setClassName(final String className) {
        this.className = className;
    }

    protected boolean hasId() {
        return id != null && !id.equals("");
    }

    protected boolean hasCls() {
        return cls != null && !cls.equals("");
    }

    protected boolean hasClasses() {
        return classes != null && classes.size() > 0;
    }

    protected boolean hasExcludeClasses() {
        return excludeClasses != null && excludeClasses.size() > 0;
    }

    protected boolean hasBaseCls() {
        return baseCls != null && !baseCls.equals("");
    }

    protected boolean hasName() {
        return name != null && !name.equals("");
    }

    protected boolean hasText() {
        return text != null && !text.equals("");
    }

    protected boolean hasStyle() {
        return style != null && !style.equals("");
    }

    protected boolean hasElPath() {
        return elPath != null && !elPath.equals("");
    }

    protected boolean hasTag() {
        return tag != null && !tag.equals("*");
    }

    protected boolean hasElCssSelector() {
        return elCssSelector != null && !elCssSelector.equals("");
    }

    protected boolean hasLabel() {
        return label != null && !label.equals("");
    }

    protected boolean hasTitle() {
        return title != null && !title.equals("");
    }

    protected boolean hasPosition() {
        return position > 0;
    }

    // =========================================
    // ============ XPath Methods ==============
    // =========================================

    /**
     * Containing baseCls, class, name and style
     *
     * @return baseSelector
     */
    protected String getBasePathSelector() {
        // TODO use disabled
        // TODO verify what need to be equal OR contains
        List<String> selector = new ArrayList<String>();
        CollectionUtils.addIgnoreNull(selector, getBasePath());
        CollectionUtils.addIgnoreNull(selector, getItemPathText());

        if (!WebDriverConfig.isIE()) {
            if (hasStyle()) {
                selector.add("contains(@style ,'" + getStyle() + "')");
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
//               TODO selector.append(" and count(ancestor-or-self::*[contains(replace(@style, '\s*:\s*', ':'), 'display:none')]) = 0");
                selector.add("count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0");
            }
        }

        return selector.isEmpty() ? "" : StringUtils.join(selector, " and ");
    }

    protected String getBasePath() {
        List<String> selector = new ArrayList<String>();
        if (hasId()) {
            selector.add("@id='" + getId() + "'");
        }
        if (hasName()) {
            selector.add("@name='" + getName() + "'");
        }
        if (hasBaseCls()) {
            selector.add("contains(concat(' ', @class, ' '), ' " + getBaseCls() + " ')");
        }
        if (hasCls()) {
            selector.add("@class='" + getCls() + "'");
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
//                selector.append(" and contains(@class, '").append(cls).append("')");
                selector.add("contains(concat(' ', @class, ' '), ' " + cls + " ')");
            }
        }
        if (hasExcludeClasses()) {
            for (String excludeClasses : getExcludeClasses()) {
                selector.add("not(contains(@class, '" + excludeClasses + "'))");
            }
        }

        for (String suffix : pathSuffixs.values()) {
            selector.add(suffix);
        }
        return selector.isEmpty() ? null : StringUtils.join(selector, " and ");
    }

    /**
     * this method is meant to be overridden by each component
     *
     * @param disabled disabled
     * @return String
     */
    protected String getItemPath(boolean disabled) {
        String selector = getBaseItemPath();
        selector = "//" + getTag() + (selector != null && (selector.length() > 0) ? ("[" + selector + "]") : "");
        return selector;
    }

    /**
     * Construct selector if WebLocator has text
     *
     * @return String
     */
    protected String getItemPathText() {
        String selector = null;
        if (hasText()) {
            selector = "";
            String text = getText();
            boolean hasContainsAll = searchTextType.contains(SearchType.CONTAINS_ALL);
            if (!(hasContainsAll || searchTextType.contains(SearchType.CONTAINS_ANY))) {
                text = Utils.getEscapeQuotesText(text);
            }
            String pathText = "text()";

            boolean useChildNodesSearch = searchTextType.contains(SearchType.CHILD_NODE) || searchTextType.contains(SearchType.DEEP_CHILD_NODE);
            if (useChildNodesSearch) {
                boolean isDeepSearch = searchTextType.contains(SearchType.DEEP_CHILD_NODE);
                selector += "count(" + (isDeepSearch ? "*//" : "") + "text()[";
                pathText = ".";
            }

            if (searchTextType.contains(SearchType.TRIM)) {
                pathText = "normalize-space(" + pathText + ")";
            }

            if (searchTextType.contains(SearchType.EQUALS)) {
                selector += pathText + "=" + text;
            } else if (searchTextType.contains(SearchType.STARTS_WITH)) {
                selector += "starts-with(" + pathText + "," + text + ")";
            } else if (hasContainsAll || searchTextType.contains(SearchType.CONTAINS_ANY)) {
                String splitChar = String.valueOf(text.charAt(0));
                Pattern pattern = Pattern.compile(Pattern.quote(splitChar));
                String[] strings = pattern.split(text.substring(1));
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = "contains(" + pathText + ",'" + strings[i] + "')";
                }
                String operator = hasContainsAll ? " and " : " or ";
                selector += hasContainsAll ? StringUtils.join(strings, operator) : "(" + StringUtils.join(strings, operator) + ")";
            } else {
                selector += "contains(" + pathText + "," + text + ")";
            }
            if (useChildNodesSearch) {
                selector += "]) > 0";
            }

            if (searchTextType.contains(SearchType.HTML_NODE)) {
                String a = "normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))=" + text;
                String b = "normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))=" + text;

                selector = "(" + a + " or " + b + ")";
            }
        }
        return selector;
    }

    private String getBaseItemPath() {
        return getBasePathSelector();
    }

    /**
     * @return final xpath (including containers xpath), used for interacting with browser
     */
    public final String getPath() {
        return getPath(false);
    }

    /**
     * @param disabled disabled
     * @return String
     */
    public String getPath(boolean disabled) {
        String returnPath;
        if (hasElPath()) {
            returnPath = getElPath();

            String baseItemPath = getBaseItemPath();
            if (baseItemPath != null && !baseItemPath.equals("")) {
                // TODO "inject" baseItemPath to elPath
//                logger.warn("TODO must inject: \"" + baseItemPath + "\" in \"" + returnPath + "\"");
            }
        } else {
            returnPath = getItemPath(disabled);
        }

        returnPath = afterItemPathCreated(returnPath);

        // add container path
        if (getContainer() != null) {
            returnPath = getContainer().getPath() + returnPath;
        }

//        logger.debug(returnPath);
        return returnPath;
    }

    @Override
    public String toString() {
        String info = getInfoMessage();
        if (info == null || "".equals(info)) {
            info = itemToString();
        }
        if (WebLocatorConfig.isLogUseClassName() && !getClassName().equals(info)) {
            info += " - " + getClassName();
        }
        // add container path
        if (WebLocatorConfig.isLogContainers() && getContainer() != null) {
            info = getContainer().toString() + " -> " + info;
        }
        return info;
    }

    public String itemToString() {
        String info;
        if (hasText()) {
            info = getText();
        } else if (hasId()) {
            info = getId();
        } else if (hasName()) {
            info = getName();
        } else if (hasClasses()) {
            info = classes.size() == 1 ? classes.get(0) : classes.toString();
        } else if (hasCls()) {
            info = getCls();
        } else if (hasLabel()) {
            info = getLabel();
        } else if (hasTitle()) {
            info = getTitle();
        } else if (hasBaseCls()) {
            info = getBaseCls();
        } else if (hasElPath()) {
            info = getElPath();
        } else if (hasTag()) {
            info = getTag();
        } else {
            info = getClassName();
        }
        return info;
    }


    protected String afterItemPathCreated(String itemPath) {
        if (hasLabel()) {
            // remove '//' because labelPath already has and include
            if (itemPath.indexOf("//") == 0) {
                itemPath = itemPath.substring(2);
            }
            itemPath = getLabelPath() + getLabelPosition() + itemPath;
        }
        itemPath = addPositionToPath(itemPath);
        return itemPath;
    }

    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath += "[position() = " + getPosition() + "]";
        }
        return itemPath;
    }

    protected String getLabelPath() {
        if (searchLabelType.size() == 0) {
            searchLabelType.add(SearchType.EQUALS);
        }
        SearchType[] st = new SearchType[searchLabelType.size()];
        for (int i = 0; i < searchLabelType.size(); i++) {
            st[i] = searchLabelType.get(i);
        }
        return new WebLocator().setText(getLabel(), st).setTag(getLabelTag()).getPath();
    }
}
