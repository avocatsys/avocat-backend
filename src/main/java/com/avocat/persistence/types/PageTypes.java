package com.avocat.persistence.types;

import lombok.Getter;

@Getter
public enum PageTypes {

    DEFAULT_PAGE("10");

    public String defaultPage;

    PageTypes(String defaultPage) {
        this.defaultPage = defaultPage;
    }
}
