package com.epolsoft.brest.model;

import java.util.Date;

public class CSVFile {
    private String filename;
    private Date lastModified;

    public CSVFile(String filename, Date lastModified) {
        this.filename = filename;
        this.lastModified = lastModified;
    }

    public CSVFile() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
