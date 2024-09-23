package com.spring.boot.util.constant;

import java.util.regex.Pattern;

/**
 * @author Yu Zhengmin
 */
public class ApplicationConstant {

    public static final Pattern BIOSAMPLE_NAME_REGEX = Pattern.compile("\\s+");

    public static final String ALL_DATA_SYMBOL = "All";
    public static final Integer ALL_DATA_INTEGER_SYMBOL = -1;

    public static final String MYSQL_TABLE_PREFIX = "t_";

    public static final String MYSQL_FIELD_PREFIX = "f_";

}
