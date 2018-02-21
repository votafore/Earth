package com.votafore.earthporn.models;

import java.util.List;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class Preview {

    private List<Image> images = null;
    private Boolean enabled;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
