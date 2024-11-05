package DTO;

public class TextFileDTO {
    private Integer fileid;
    private String fileContent;
    private String fileName;
    private int wordCount;
    private String fileHash;

   
    public TextFileDTO(Integer fileid, String fileContent, String fileName, int wordCount, String fileHash) {
        this.fileid = fileid;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.wordCount = wordCount;
        this.fileHash = fileHash;
    }
    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}