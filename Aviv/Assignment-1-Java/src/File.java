public class File {

    String fileName;
    String fileType;


    public File(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public void setFileName(String fileName)throws IllegalArgumentException {

        if (fileName == null) {
            throw new IllegalArgumentException("file name cannot be null");

        }
        this.fileName = fileName;

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileType(String fileType)throws IllegalArgumentException {
        if (fileType == null) {
            throw new IllegalArgumentException("file type cannot be null");
        }
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public String toString() {
        return " file name: "+ getFileName() + "file type: "+ getFileType();
    }

}
