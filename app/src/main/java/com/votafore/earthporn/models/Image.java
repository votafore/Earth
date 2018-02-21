package com.votafore.earthporn.models;

import java.util.List;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class Image {

    private Source source;
    private List<Resolution> resolutions = null;
    private Variants variants;
    private String id;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    public Variants getVariants() {
        return variants;
    }

    public void setVariants(Variants variants) {
        this.variants = variants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
